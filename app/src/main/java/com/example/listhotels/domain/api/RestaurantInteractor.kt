package com.example.listhotels.domain.api

import com.example.listhotels.domain.models.RestaurantDetailsItem
import com.example.listhotels.domain.models.RestaurantItem
import com.example.listhotels.util.Resource
import kotlinx.coroutines.flow.Flow

interface RestaurantInteractor {

    fun getRestaurant(): Flow<Resource<List<RestaurantItem>>>
    fun getDetailsRestaurant(id: Int): Flow<Resource<RestaurantDetailsItem>>

}