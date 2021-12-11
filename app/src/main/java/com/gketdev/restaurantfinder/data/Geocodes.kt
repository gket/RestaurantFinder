package com.gketdev.restaurantfinder.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Geocodes(
    @Json(name = "main")
    val main: Main
) : Parcelable