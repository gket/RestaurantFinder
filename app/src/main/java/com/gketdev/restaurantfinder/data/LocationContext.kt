package com.gketdev.restaurantfinder.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LocationContext(
    @Json(name = "geo_bounds")
    val geoBounds: GeoBounds
)