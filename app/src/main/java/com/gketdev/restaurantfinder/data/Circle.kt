package com.gketdev.restaurantfinder.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Circle(
    @Json(name = "center")
    val center: Center,
    @Json(name = "radius")
    val radius: Int
)