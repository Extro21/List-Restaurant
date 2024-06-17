package com.example.listhotels.di

import com.example.listhotels.util.Mapper
import com.example.listhotels.data.network.NetworkClient
import com.example.listhotels.data.network.RetrofitNetworkClient
import com.example.listhotels.domain.api.RestaurantRepository
import com.example.listhotels.data.impl.RestaurantRepositoryImpl
import org.koin.dsl.module

val dataModule = module {

    single {
        Mapper()
    }

    single<RestaurantRepository> {
        RestaurantRepositoryImpl(get(), get())
    }

    single<NetworkClient> {
        RetrofitNetworkClient(get())
    }

}
