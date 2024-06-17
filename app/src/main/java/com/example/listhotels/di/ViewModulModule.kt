package com.example.listhotels.di

import com.example.listhotels.ui.details.viewModel.DetailsViewModel
import com.example.listhotels.ui.listRestaurant.viewModel.RestaurantViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModule = module {

    viewModel {
        RestaurantViewModel(get())
    }

    viewModel {
        DetailsViewModel(get())
    }

}