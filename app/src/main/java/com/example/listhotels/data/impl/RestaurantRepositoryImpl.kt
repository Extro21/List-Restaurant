package com.example.listhotels.data.impl

import com.example.listhotels.data.network.NetworkClient
import com.example.listhotels.domain.api.RestaurantRepository
import com.example.listhotels.domain.models.RestaurantItem
import com.example.listhotels.util.Mapper
import com.example.listhotels.data.dto.RestaurantDetailDto
import com.example.listhotels.data.dto.RestaurantResp
import com.example.listhotels.data.network.Request
import com.example.listhotels.domain.models.RestaurantDetailsItem
import com.example.listhotels.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RestaurantRepositoryImpl(
    private val networkClient: NetworkClient,
    private val map: Mapper,
) : RestaurantRepository {

    override fun getRestaurant(): Flow<Resource<List<RestaurantItem>>> = flow {
        val request = Request.RestaurantRequest
        when (val response = networkClient.doRequest(request)) {
            is Resource.Error -> {
                emit(Resource.Error(response.errorType!!))
            }

            is Resource.Success -> {
                val data = map.mapDtoToModels(response.data as RestaurantResp)
                emit(Resource.Success(data = data))
            }
        }
    }

    override fun getDetailsRestaurant(id: Int): Flow<Resource<RestaurantDetailsItem>> = flow {
        val request = Request.RestaurantDetailsRequest(id)
        when (val response = networkClient.doRequest(request)) {
            is Resource.Error -> {
                emit(Resource.Error(response.errorType!!))
            }

            is Resource.Success -> {
                val data = map.mapDtoDetailsToModelDetails(response.data as RestaurantDetailDto)
                emit(Resource.Success(data = data))
            }
        }
    }


}