package com.example.listhotels.domain.models


data class RestaurantDetailsItem(
    val id: Int,
    val address: String,
    val cuisine: String,
    val distance: Double,
    val freeTables: Int,
    val image: String,
    val menu: List<MenuItem>,
    val name: String,
    val rating: Double,
    val lat : String,
    val lon : String,
    val imageCuisine : String,
)