package com.example.listhotels.ui.listRestaurant

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listhotels.R
import com.example.listhotels.databinding.RestaurantFragmentBinding
import com.example.listhotels.domain.models.RestaurantItem
import com.example.listhotels.ui.listRestaurant.adapters.RestaurantAdapter
import com.example.listhotels.ui.details.DetailsFragment
import com.example.listhotels.ui.states.RestaurantState
import com.example.listhotels.ui.states.SortState
import com.example.listhotels.ui.listRestaurant.viewModel.RestaurantViewModel
import com.example.listhotels.util.debounce
import com.google.android.material.bottomsheet.BottomSheetBehavior
import org.koin.androidx.viewmodel.ext.android.viewModel

class RestaurantFragment : Fragment() {

    private var _binding: RestaurantFragmentBinding? = null
    private val binding get() = _binding!!

    private var isClickAllowed = true

    private val adapter = RestaurantAdapter {
        openDetailsFragment(it)
    }

    private val viewModel by viewModel<RestaurantViewModel>()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    private fun openDetailsFragment(id: Int) {
        if (clickDebounce()) {
            openRestaurantDetails(id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = RestaurantFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listRestaurant.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.listRestaurant.adapter = adapter

        viewModel.state.observe(viewLifecycleOwner) { state ->
            render(state)
        }

        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheetBehavior).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        binding.dimView.visibility = View.GONE
                    }

                    else -> {}
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })



        binding.btSort.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            binding.dimView.visibility = View.VISIBLE
        }

        viewModel.stateSort.observe(viewLifecycleOwner) { state ->
            renderStateFilter(state)
            binding.listRestaurant.scrollToPosition(0)
        }


        binding.btApply.setOnClickListener {
            if (binding.radioGroupSort.checkedRadioButtonId != -1) {
                viewModel.filter(changeFilerButton())
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.please_select_an_option), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun render(state: RestaurantState) {
        when (state) {
            is RestaurantState.Content -> getContent(state.restaurant)
            is RestaurantState.Error -> showError()
            is RestaurantState.Loading -> showLoading()
        }
    }

    private fun changeFilerButton(): SortState {
        return when (binding.radioGroupSort.checkedRadioButtonId) {
            R.id.closerRB -> SortState.MinDistance()
            R.id.furtherRB -> SortState.MaxDistance()
            R.id.lotRoomsRB -> SortState.MinTables()
            R.id.fewRoomsRB -> SortState.MaxTables()
            else -> SortState.Default()
        }
    }

    private fun openRestaurantDetails(id: Int) {
        findNavController().navigate(
            R.id.action_restaurantFragment_to_detailsFragment,
            DetailsFragment.createArgs(id)
        )
    }

    private fun renderStateFilter(state: SortState) {
        when (state) {
            is SortState.MinDistance -> {
                adapter.updateData(state.filterList)
                binding.btSort.text = requireContext().getString(R.string.closer_center)
            }

            is SortState.MaxDistance -> {
                adapter.updateData(state.filterList)
                binding.btSort.text = requireContext().getString(R.string.further_center)
            }

            is SortState.MinTables -> {
                adapter.updateData(state.filterList)
                binding.btSort.text = requireContext().getString(R.string.few_free_tables)
            }

            is SortState.MaxTables -> {
                adapter.updateData(state.filterList)
                binding.btSort.text = requireContext().getString(R.string.many_free_tables)
            }

            is SortState.Default -> {
                adapter.updateData(state.filterList)
                binding.btSort.text = requireContext().getString(R.string.default_sort)
            }
        }
    }

    private fun showError() = with(binding) {
        btSort.isVisible = false
        progressBar.isVisible = false
        listRestaurant.isVisible = false
        errorMassage.isVisible = true
        refreshBt.setOnClickListener {
            viewModel.getRestaurant()
            progressBar.isVisible = true
            errorMassage.isVisible = false
        }
    }

    private fun showLoading() = with(binding) {
        progressBar.isVisible = true
        listRestaurant.isVisible = false
        errorMassage.isVisible = false
    }

    private fun getContent(restaurant: List<RestaurantItem>) = with(binding) {
        btSort.isVisible = true
        adapter.updateData(restaurant)
        progressBar.isVisible = false
        listRestaurant.isVisible = true
        errorMassage.isVisible = false
    }

    private val onItemClickDebounce = debounce<Boolean>(
        CLICK_DEBOUNCE_DELAY,
        lifecycleScope,
        false
    ) { param ->
        isClickAllowed = param
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            onItemClickDebounce(true)
        }
        return current
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 500L
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

