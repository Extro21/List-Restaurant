package com.example.listhotels.ui.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.listhotels.R
import com.example.listhotels.databinding.DetailsFragmentBinding
import com.example.listhotels.domain.models.RestaurantDetailsItem
import com.example.listhotels.ui.details.adapter.MenuAdapter
import com.example.listhotels.ui.details.viewModel.DetailsViewModel
import com.example.listhotels.ui.states.DetailsState
import org.koin.androidx.viewmodel.ext.android.viewModel

const val EXTRA_ID = "restaurant_id"

class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<DetailsViewModel>()

    private val adapter = MenuAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listMenu.layoutManager = GridLayoutManager(requireContext(), 3)
        binding.listMenu.adapter = adapter

        val restaurantId = requireArguments().getInt(EXTRA_ID)

        viewModel.getRestaurantDetails(restaurantId)

        viewModel.state().observe(viewLifecycleOwner) { state ->
            render(state)
        }

        binding.titleBar.setOnClickListener { findNavController().popBackStack() }

    }

    private fun render(state: DetailsState) {
        when (state) {
            is DetailsState.Content -> showContent(state.restaurant)
            is DetailsState.Error -> showError()
            is DetailsState.Loading -> showLoading()
        }
    }

    private fun showError() {
        updateScreenViews(
            isMainProgressVisible = false,
            isErrorMessageVisible = true,
            isRestaurantImageVisible = false,
            isImgStarsRestaurantVisible = false,
            isStarRestaurantVisible = false,
            isImgAddressRestaurantVisible = false,
            isAddressRestaurantVisible = false,
            isImgDistanceRestaurantVisible = false,
            isDistanceRestaurantVisible = false,
            isImgCoordinatesVisible = false,
            isCoordinatesVisible = false,
            isTvSuitesAvailabilityRestaurantVisible = false,
            isSuiteAvailabilityRestaurantVisible = false,
        )
    }

    private fun showLoading() {
        updateScreenViews(
            isMainProgressVisible = true,
            isErrorMessageVisible = false,
            isRestaurantImageVisible = false,
            isImgStarsRestaurantVisible = false,
            isStarRestaurantVisible = false,
            isImgAddressRestaurantVisible = false,
            isAddressRestaurantVisible = false,
            isImgDistanceRestaurantVisible = false,
            isDistanceRestaurantVisible = false,
            isImgCoordinatesVisible = false,
            isCoordinatesVisible = false,
            isTvSuitesAvailabilityRestaurantVisible = false,
            isSuiteAvailabilityRestaurantVisible = false,
        )
    }

    private fun setImage(restaurant: RestaurantDetailsItem?) {
        val imageUri = viewModel.getImageUri(restaurant)
        Glide.with(this@DetailsFragment)
            .load(imageUri)
            .centerCrop()
            .placeholder(R.drawable.default_img)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.restaurantImage)

    }

    private fun showContent(restaurant: RestaurantDetailsItem?) = with(binding) {
        setImage(restaurant)
        imageCuisine.text = restaurant?.imageCuisine
        distanceRestaurant.text = requireContext().getString(
            R.string.from_center_format,
            restaurant?.distance.toString(), requireContext().getString(R.string.from_center)
        )
        collapsingToolbarLayout.title = restaurant?.name
        addressRestaurant.text = restaurant?.address
        starsRestaurant.text = restaurant?.rating.toString()
        coordinates.text =
            requireContext().getString(
                R.string.coordinates_format,
                restaurant?.lat,
                restaurant?.lon
            )
        val count = restaurant?.freeTables
        val ending = count?.let { getEnding(it) }
        suitesAvailabilityRestaurant.text =
            requireContext().getString(R.string.rooms_available, count, ending)
        coordinates.setOnClickListener {
            openMap(restaurant?.lat?.toDouble(), restaurant?.lon?.toDouble())
        }
        cuisine.text = restaurant?.cuisine
        adapter.submitList(restaurant?.menu)
        contentVisibility()
    }

    private fun contentVisibility() {
        updateScreenViews(
            isMainProgressVisible = false,
            isErrorMessageVisible = false,
            isRestaurantImageVisible = true,
            isImgStarsRestaurantVisible = true,
            isStarRestaurantVisible = true,
            isImgAddressRestaurantVisible = true,
            isAddressRestaurantVisible = true,
            isImgDistanceRestaurantVisible = true,
            isDistanceRestaurantVisible = true,
            isImgCoordinatesVisible = true,
            isCoordinatesVisible = true,
            isTvSuitesAvailabilityRestaurantVisible = true,
            isSuiteAvailabilityRestaurantVisible = true,
        )
    }

    private fun getEnding(number: Int): String {
        return if (number <= 1) {
            requireContext().getString(R.string.table)
        } else {
            requireContext().getString(R.string.tables)
        }
    }

    private fun updateScreenViews(
        isMainProgressVisible: Boolean,
        isRestaurantImageVisible: Boolean,
        isImgStarsRestaurantVisible: Boolean,
        isStarRestaurantVisible: Boolean,
        isImgAddressRestaurantVisible: Boolean,
        isAddressRestaurantVisible: Boolean,
        isImgDistanceRestaurantVisible: Boolean,
        isDistanceRestaurantVisible: Boolean,
        isImgCoordinatesVisible: Boolean,
        isCoordinatesVisible: Boolean,
        isTvSuitesAvailabilityRestaurantVisible: Boolean,
        isSuiteAvailabilityRestaurantVisible: Boolean,
        isErrorMessageVisible: Boolean,
    ) {
        with(binding) {
            progressBar.isVisible = isMainProgressVisible
            restaurantImage.isVisible = isRestaurantImageVisible
            imgStarsRestaurant.isVisible = isImgStarsRestaurantVisible
            starsRestaurant.isVisible = isStarRestaurantVisible
            imgAddressRestaurant.isVisible = isImgAddressRestaurantVisible
            addressRestaurant.isVisible = isAddressRestaurantVisible
            imgDistanceRestaurant.isVisible = isImgDistanceRestaurantVisible
            distanceRestaurant.isVisible = isDistanceRestaurantVisible
            imgCoordinates.isVisible = isImgCoordinatesVisible
            coordinates.isVisible = isCoordinatesVisible
            tvSuitesAvailabilityRestaurant.isVisible = isTvSuitesAvailabilityRestaurantVisible
            suitesAvailabilityRestaurant.isVisible = isSuiteAvailabilityRestaurantVisible
            errorMassage.isVisible = isErrorMessageVisible
        }
    }

    private fun openMap(latitude: Double?, longitude: Double?) {
        val uri = Uri.parse("geo:$latitude,$longitude")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

    companion object {
        fun createArgs(
            restaurantId: Int,
        ): Bundle =
            bundleOf(
                EXTRA_ID to restaurantId,
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}