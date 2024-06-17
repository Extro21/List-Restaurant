package com.example.listhotels.domain.impl

import com.example.listhotels.domain.api.RestaurantInteractor
import com.example.listhotels.domain.api.RestaurantRepository
import com.example.listhotels.domain.models.RestaurantDetailsItem
import com.example.listhotels.domain.models.RestaurantItem
import com.example.listhotels.util.Resource
import kotlinx.coroutines.flow.Flow

class RestaurantInteractorImpl(private val repository: RestaurantRepository) :
    RestaurantInteractor {

    override fun getRestaurant(): Flow<Resource<List<RestaurantItem>>> {
        return repository.getRestaurant()
    }

    override fun getDetailsRestaurant(id: Int): Flow<Resource<RestaurantDetailsItem>> {
        return repository.getDetailsRestaurant(id)
    }

}