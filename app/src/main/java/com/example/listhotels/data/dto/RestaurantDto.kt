package com.example.listhotels.data.dto

import com.example.listhotels.data.Response
import com.google.gson.annotations.SerializedName


data class RestaurantDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("distance")
    val distance: Double,
    @SerializedName("free_tables")
    val freeTables: String,
)


class RestaurantResp : ArrayList<RestaurantDto>(), Response

