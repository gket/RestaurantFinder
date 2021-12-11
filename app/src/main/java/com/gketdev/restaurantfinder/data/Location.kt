package com.gketdev.restaurantfinder.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Location(
    @Json(name = "address")
    val address: String?,
    @Json(name = "country")
    val country: String?,
    @Json(name = "cross_street")
    val street: String?,
    @Json(name = "locality")
    val locality: String?,
    @Json(name = "neighborhood")
    val neighborhood: List<String>?,
    @Json(name = "postcode")
    val postcode: String?,
    @Json(name = "region")
    val region: String?
) : Parcelable