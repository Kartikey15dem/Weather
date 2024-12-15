package com.example.weather.core.data.location

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun getCoordinates(context: Context): Pair<Double, Double>? {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    return suspendCoroutine { continuation ->
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request location permission if not granted
            val LOCATION_PERMISSION_REQUEST_CODE = 0
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            continuation.resume(null)
            return@suspendCoroutine
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val lat = location.latitude
                val long = location.longitude
                continuation.resume(Pair(lat, long))
            } else {
                continuation.resume(null)
            }
        }.addOnFailureListener {
            continuation.resume(null)
        }
    }
}



