package com.gketdev.restaurantfinder.api

import com.gketdev.restaurantfinder.data.ResultResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FoursquareApiService {

    @GET("v3/places/search")
    suspend fun getRestaurants(
        @Query("ne") northEast: String,
        @Query("sw") southWest: String,
        @Query("categories") category: Int = 13065
    ): ResultResponse

}