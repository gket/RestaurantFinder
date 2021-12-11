package com.gketdev.restaurantfinder.source

import com.gketdev.restaurantfinder.api.FoursquareApiService
import com.gketdev.restaurantfinder.base.BaseCallMapper
import com.gketdev.restaurantfinder.data.DataResultState
import com.gketdev.restaurantfinder.data.Restaurant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val fsqService: FoursquareApiService) :
    BaseCallMapper() {

    fun getRestaurants(
        northEast: Pair<Double, Double>,
        southWest: Pair<Double, Double>,
    ): Flow<DataResultState<List<Restaurant>>> = flow {
        emit(apiCallResponse {
            fsqService.getRestaurants(
                northEast = "${northEast.first},${northEast.second}",
                southWest = "${southWest.first},${southWest.second}"
            ).restaurants
        })
    }

}