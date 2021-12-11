package com.gketdev.restaurantfinder.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gketdev.restaurantfinder.data.DataResultState
import com.gketdev.restaurantfinder.repository.HomeRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    private val _viewState = MutableStateFlow<HomeViewState>(HomeViewState.InitialLoading)
    val viewState: StateFlow<HomeViewState> = _viewState
    private var southWest = Pair(0.0, 0.0)
    private var northEast = Pair(0.0, 0.0)


    fun setBound(latLngBounds: LatLngBounds) {
        southWest = (latLngBounds.southwest.latitude to latLngBounds.southwest.longitude)
        northEast = (latLngBounds.northeast.latitude to latLngBounds.northeast.longitude)
        getRestaurants()
    }

    private fun getRestaurants() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getRestaurants(northEast, southWest).collect {
                when (it) {
                    is DataResultState.Error -> _viewState.value = HomeViewState.Error(it.message)
                    is DataResultState.Success -> _viewState.value =
                        HomeViewState.Restaurants(it.data)
                }
            }
        }
    }

}