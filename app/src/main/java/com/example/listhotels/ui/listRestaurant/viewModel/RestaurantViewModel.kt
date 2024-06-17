package com.example.listhotels.ui.listRestaurant.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listhotels.domain.api.RestaurantInteractor
import com.example.listhotels.domain.models.RestaurantItem
import com.example.listhotels.ui.states.RestaurantState
import com.example.listhotels.ui.states.SortState
import com.example.listhotels.util.Resource
import kotlinx.coroutines.launch

class RestaurantViewModel(private val interactor: RestaurantInteractor) : ViewModel() {

    private val listRestaurant = ArrayList<RestaurantItem>()
    private val sortListRestaurant = ArrayList<RestaurantItem>()

    private val _stateSort = MutableLiveData<SortState>()
    val stateSort: LiveData<SortState> = _stateSort

    private val _state = MutableLiveData<RestaurantState>(RestaurantState.Loading)
    val state: LiveData<RestaurantState> = _state

    init {
        getRestaurant()
    }

    fun getRestaurant() {
        viewModelScope.launch {
            interactor.getRestaurant().collect {
                processResult(it)
            }
        }
    }


    private fun processResult(result: Resource<List<RestaurantItem>>) {
        when (result) {
            is Resource.Success -> {
                listRestaurant.clear()
                result.data?.let { listRestaurant.addAll(it) }
                _state.postValue(RestaurantState.Content(listRestaurant))
            }

            is Resource.Error -> {
                _state.postValue(RestaurantState.Error(result.errorType!!))
            }
        }
    }

    fun filter(state: SortState) {
        sortListRestaurant.clear()
        when (state) {
            is SortState.MaxTables -> {
                sortListRestaurant.addAll(listRestaurant.sortedBy { it.freeTables })
                _stateSort.postValue(SortState.MinTables(sortListRestaurant))
            }

            is SortState.MinTables -> {
                sortListRestaurant.addAll(listRestaurant.sortedBy { it.freeTables }
                    .reversed())
                _stateSort.postValue(SortState.MaxTables(sortListRestaurant))
            }

            is SortState.MinDistance -> {
                sortListRestaurant.addAll(listRestaurant.sortedBy { it.distance })
                _stateSort.postValue(SortState.MinDistance(sortListRestaurant))
            }

            is SortState.MaxDistance -> {
                sortListRestaurant.addAll(listRestaurant.sortedBy { it.distance }.reversed())
                _stateSort.postValue(SortState.MaxDistance(sortListRestaurant))
            }

            is SortState.Default -> {
                _stateSort.postValue(SortState.Default(listRestaurant))
            }
        }
    }

}