package com.gketdev.restaurantfinder.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Restaurant(
    @Json(name = "distance")
    val distance: Int,
    @Json(name = "fsq_id")
    val fsqId: String,
    @Json(name = "geocodes")
    val geocodes: Geocodes,
    @Json(name = "location")
    val location: Location,
    @Json(name = "name")
    val name: String,
    @Json(name = "timezone")
    val timezone: String
) : Parcelable