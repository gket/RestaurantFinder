package com.gketdev.restaurantfinder.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GeoBounds(
    @Json(name = "circle")
    val circle: Circle
)