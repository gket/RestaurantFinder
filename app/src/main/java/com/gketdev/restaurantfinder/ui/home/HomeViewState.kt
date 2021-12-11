package com.gketdev.restaurantfinder.ui.home

import com.gketdev.restaurantfinder.data.Restaurant

sealed class HomeViewState {
    object InitialLoading : HomeViewState()
    object RequestLoading : HomeViewState()
    data class Restaurants(val list: List<Restaurant>) : HomeViewState()
    data class Error(val error: String?) : HomeViewState()
}
