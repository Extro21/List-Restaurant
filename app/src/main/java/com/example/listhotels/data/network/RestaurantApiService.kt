package com.example.listhotels.data.network

import com.example.listhotels.data.dto.RestaurantResp
import com.example.listhotels.data.dto.RestaurantDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantApiService {

    @GET("/Extro21/restaurant_repository/main/list_restaurant.json")
    suspend fun getRestaurant(): RestaurantResp

    @GET("/Extro21/restaurant_repository/main/{id}.json")
    suspend fun getDetailRestaurant(@Path("id") id: String): RestaurantDetailDto

}