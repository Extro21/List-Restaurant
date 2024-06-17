package com.example.listhotels.ui.states

import com.example.listhotels.domain.models.RestaurantItem

sealed interface SortState {

    data class MinDistance(
        val filterList: List<RestaurantItem> = emptyList(),
    ) : SortState

    data class MaxDistance(
        val filterList: List<RestaurantItem> = emptyList(),
    ) : SortState

    data class MaxTables(
        val filterList: List<RestaurantItem> = emptyList(),
    ) : SortState

    data class MinTables(
        val filterList: List<RestaurantItem> = emptyList(),
    ) : SortState

    data class Default(
        val filterList: List<RestaurantItem> = emptyList(),
    ) : SortState

}