package com.example.listhotels.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.listhotels.util.ErrorType
import com.example.listhotels.util.Resource
import com.example.listhotels.data.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class RetrofitNetworkClient(private val context: Context) : NetworkClient {

    private val binInfoUrl = "https://raw.githubusercontent.com"

    private val retrofitRestaurant =
        Retrofit.Builder().baseUrl(binInfoUrl).addConverterFactory(GsonConverterFactory.create())
            .build()
    private val restaurantService = retrofitRestaurant.create(RestaurantApiService::class.java)

    override suspend fun doRequest(dto: Request): Resource<Response> {
        if (!isConnected()) {
            return Resource.Error(ErrorType.NO_INTERNET)
        }

        return withContext(Dispatchers.IO) {
            try {
                val resp = getResponse(dto)
                Resource.Success(resp)
            } catch (e1: IOException) {
                Resource.Error(ErrorType.SERVER_THROWABLE)
            } catch (e2: HttpException) {
                Resource.Error(ErrorType.NON_200_RESPONSE)
            }
        }
    }

    private suspend fun getResponse(dto: Request): Response {
        return when (dto) {
            is Request.RestaurantRequest -> restaurantService.getRestaurant()
            is Request.RestaurantDetailsRequest -> restaurantService.getDetailRestaurant(dto.id.toString())
        }
    }

    private fun isConnected(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}