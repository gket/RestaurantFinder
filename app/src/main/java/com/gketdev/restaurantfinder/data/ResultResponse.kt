package com.gketdev.restaurantfinder.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResultResponse(
    @Json(name = "context")
    val locationContext: LocationContext,
    @Json(name = "results")
    val restaurants: List<Restaurant>
)