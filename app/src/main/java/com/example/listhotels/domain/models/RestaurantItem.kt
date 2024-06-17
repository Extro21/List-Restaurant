package com.example.listhotels.domain.models


data class RestaurantItem(
    val id: Int,
    val name: String,
    val address: String,
    val rating: Double,
    val distance: Double,
    val freeTables: Int,
)
