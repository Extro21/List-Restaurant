package com.example.listhotels.ui.details.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listhotels.domain.api.RestaurantInteractor
import com.example.listhotels.domain.models.RestaurantDetailsItem
import com.example.listhotels.ui.states.DetailsState
import com.example.listhotels.util.Resource
import kotlinx.coroutines.launch

class DetailsViewModel(private val interactor: RestaurantInteractor) : ViewModel() {

    private val _state = MutableLiveData<DetailsState>(DetailsState.Loading)
    fun state(): LiveData<DetailsState> = _state

    fun getRestaurantDetails(id: Int) {
        viewModelScope.launch {
            interactor.getDetailsRestaurant(id).collect {
                processResult(it)
            }
        }
    }

    private fun processResult(result: Resource<RestaurantDetailsItem>) {
        when (result) {
            is Resource.Success -> {
                _state.postValue(DetailsState.Content(result.data))
            }

            is Resource.Error -> {
                _state.postValue(DetailsState.Error)
            }
        }
    }

    fun getImageUri(restaurant: RestaurantDetailsItem?): String {
        return "https://github.com/Extro21/restaurant_repository/raw/main/photo/${restaurant?.image}"
    }

}