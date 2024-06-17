package com.example.listhotels.data.network

sealed interface Request {

    data object RestaurantRequest : Request

    data class RestaurantDetailsRequest(val id: Int) : Request

}