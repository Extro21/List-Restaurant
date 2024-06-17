package com.example.listhotels.data.dto

import com.example.listhotels.data.Response
import com.google.gson.annotations.SerializedName


data class RestaurantDetailDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("address")
    val address: String,
    @SerializedName("cuisine")
    val cuisine: String,
    @SerializedName("distance")
    val distance: Double,
    @SerializedName("free_tables")
    val freeTables: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("menu")
    val menu: List<String>,
    @SerializedName("name")
    val name: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("image_cuisine")
    val imageCuisine: String,
) : Response


