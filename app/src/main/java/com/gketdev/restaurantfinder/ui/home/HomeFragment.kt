package com.gketdev.restaurantfinder.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.gketdev.restaurantfinder.R
import com.gketdev.restaurantfinder.base.BaseFragment
import com.gketdev.restaurantfinder.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : BaseFragment<FragmentHomeBinding>(), OnMapReadyCallback {

    private val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    private lateinit var googleMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var userLocation: LatLng = LatLng(0.0, 0.0)


    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToParent: Boolean
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, attachToParent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
        binding?.mapView?.getMapAsync(this)
        binding?.mapView?.onCreate(savedInstanceState)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
    }

    override fun onStart() {
        super.onStart()
        binding?.mapView?.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding?.mapView?.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding?.mapView?.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        binding?.mapView?.onStop()
    }

    private fun checkPermission() {
        val activityResultLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireContext())
                locationListener()
            } else {
                //TODO Dialog or toast message
            }
        }

        if (allPermissionsGranted()) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
            locationListener()
        } else {
            activityResultLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    private fun allPermissionsGranted() = requiredPermissions.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun locationListener() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                userLocation = LatLng(location?.latitude ?: 0.0, location?.longitude ?: 0.0)
                showLocationInMap()
            }
    }

    private fun showLocationInMap() {

        val mapStyleOption = MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.map_style)

        googleMap.setMapStyle(mapStyleOption)

        val markerOptions = MarkerOptions().apply {
            position(userLocation)
            anchor(0.5f, 0.5f)
        }

        googleMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(
                userLocation, 20f
            )
        )

        googleMap.addMarker(markerOptions)
    }


}