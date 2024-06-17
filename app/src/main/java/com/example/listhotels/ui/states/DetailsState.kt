package com.example.listhotels.ui.states

import com.example.listhotels.domain.models.RestaurantDetailsItem

sealed interface DetailsState {

    data class Content(
        val restaurant: RestaurantDetailsItem?,
    ) : DetailsState

    data object Error : DetailsState
    data object Loading : DetailsState
}