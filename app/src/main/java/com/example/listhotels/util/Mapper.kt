package com.example.listhotels.util


import com.example.listhotels.data.dto.RestaurantDetailDto
import com.example.listhotels.data.dto.RestaurantDto
import com.example.listhotels.domain.models.RestaurantDetailsItem
import com.example.listhotels.domain.models.RestaurantItem
import com.example.listhotels.domain.models.MenuItem
import java.math.BigDecimal
import java.math.RoundingMode

class Mapper {

    fun mapDtoToModels(restaurantDto: List<RestaurantDto>): List<RestaurantItem> {
        return restaurantDto.map { hotel ->
            mapDtoToModel(hotel)
        }
    }

    private fun mapDtoToModel(hotelDto: RestaurantDto): RestaurantItem {
        return RestaurantItem(
            id = hotelDto.id,
            address = hotelDto.address,
            distance = hotelDto.distance,
            name = hotelDto.name,
            rating = hotelDto.rating,
            freeTables = hotelDto.freeTables.toInt(),
        )
    }

    fun mapDtoDetailsToModelDetails(hotelDto: RestaurantDetailDto): RestaurantDetailsItem {
        return RestaurantDetailsItem(
            id = hotelDto.id,
            address = hotelDto.address,
            distance = hotelDto.distance,
            name = hotelDto.name,
            rating = hotelDto.rating,
            image = hotelDto.image,
            lat = fixCoordinates(hotelDto.lat),
            lon = fixCoordinates(hotelDto.lon),
            freeTables = hotelDto.freeTables,
            cuisine = hotelDto.cuisine,
            menu = mapMenuToMenuItem(hotelDto.menu),
            imageCuisine = hotelDto.imageCuisine
        )
    }

    private fun mapMenuToMenuItem(menuDto: List<String>): List<MenuItem> {
        return menuDto.map { menu ->
            mapDtoToMenuItem(menu)
        }
    }

    private fun mapDtoToMenuItem(menuDto: String): MenuItem {
        return MenuItem(
            name = menuDto
        )
    }

    private fun fixCoordinates(coordinate: Double?): String {
        val value = coordinate ?: 0.0
        val bigDecimal = BigDecimal(value.toString())
        val roundedValue = bigDecimal.setScale(6, RoundingMode.DOWN)
        val seventhDigit = bigDecimal.movePointRight(7).remainder(BigDecimal.TEN).toInt()

        val finalValue = if (seventhDigit >= 5) {
            roundedValue.add(BigDecimal("0.000001"))
        } else {
            roundedValue
        }

        return finalValue.setScale(6, RoundingMode.HALF_UP).toString().replace(",", ".")
    }

}


