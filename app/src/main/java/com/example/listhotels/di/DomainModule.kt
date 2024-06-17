package com.example.listhotels.di


import com.example.listhotels.domain.api.RestaurantInteractor
import com.example.listhotels.domain.impl.RestaurantInteractorImpl
import org.koin.dsl.module

val domainModule = module {

    factory<RestaurantInteractor> {
        RestaurantInteractorImpl(get())
    }

}