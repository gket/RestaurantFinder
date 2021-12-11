package com.gketdev.restaurantfinder.repository

import com.gketdev.restaurantfinder.data.DataResultState
import com.gketdev.restaurantfinder.source.LocalDataSource
import com.gketdev.restaurantfinder.source.RemoteDataSource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {

    fun getRestaurants(
        northEast: Pair<Double, Double>,
        southWest: Pair<Double, Double>,
    ) = flow {
        localDataSource.getRestaurants(northEast, southWest).collect {
            emit(it)
        }
        remoteDataSource.getRestaurants(northEast, southWest).collect {
            if (it is DataResultState.Success) {
                localDataSource.addRestaurant(it.data)
            }
            emit(it)
        }
    }

}