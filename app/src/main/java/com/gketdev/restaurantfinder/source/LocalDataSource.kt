package com.gketdev.restaurantfinder.source

import com.gketdev.restaurantfinder.data.DataResultState
import com.gketdev.restaurantfinder.data.Restaurant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor() {

    private val data = mutableSetOf<Restaurant>()

    fun getRestaurants(
        northEast: Pair<Double, Double>,
        southWest: Pair<Double, Double>,
    ): Flow<DataResultState<List<Restaurant>>> = flow {
        val restaurants = data.filter {
            it.geocodes.main.latitude in (southWest.first..northEast.first)
                    && it.geocodes.main.longitude in (southWest.second..northEast.second)
        }
        emit(DataResultState.Success(restaurants))
    }

    fun addRestaurant(restaurant: List<Restaurant>) {
        data += restaurant
    }

}