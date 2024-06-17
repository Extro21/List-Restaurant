package com.example.listhotels.ui.states

import com.example.listhotels.util.ErrorType
import com.example.listhotels.domain.models.RestaurantItem


sealed interface RestaurantState {

    data class Content(
        val restaurant: List<RestaurantItem>,
    ) : RestaurantState

    data object Loading : RestaurantState

    data class Error(val error: ErrorType) : RestaurantState

}