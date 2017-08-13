package com.sampleapp.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location

import com.google.android.gms.maps.model.LatLng

import java.io.IOException
import java.util.Locale

/**
 * Created by saveen_dhiman on 13-Aug-17.
 * contains helper methods to manipulate user location
 */
class LocationHelper(private val mContext: Context) {

    /**
     * @param latitude  latitude of address
     * *
     * @param longitude longitude of address
     * *
     * @return simplified address of location
     */

    fun getSimplifiedAddress(latitude: Double, longitude: Double): String {
        var location = ""
        try {
            val geocoder = Geocoder(mContext, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses.size > 0) {
                val address = addresses[0]
                var admin = address.adminArea
                val subLocality = address.subLocality
                val locality = address.locality
                if (admin.length > 10) {
                    admin = admin.substring(0, 10) + ".."
                }
                if (locality != null && subLocality != null) {
                    location = subLocality + "," + locality
                } else if (subLocality != null) {
                    location = subLocality + "," + admin
                } else {
                    location = locality + "," + admin
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return location
    }

    /**
     * @param latitude  latitude of address
     * *
     * @param longitude longitude of address
     * *
     * @return complete address of location
     */

    fun getCompleteAddress(latitude: Double, longitude: Double): String {
        var location = ""
        try {
            val geocoder = Geocoder(mContext, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses.size > 0) {
                val address = addresses[0]
                val state: String
                val city: String
                val zip: String
                val street: String
                if (address.adminArea != null) {
                    state = address.adminArea
                } else {
                    state = ""
                }
                if (address.locality != null) {
                    city = address.locality
                } else {
                    city = ""
                }
                if (address.postalCode != null) {
                    zip = address.postalCode
                } else {
                    zip = ""
                }

                if (address.thoroughfare != null) {
                    street = address.subLocality + "," + address.thoroughfare
                } else {
                    street = address.subLocality + "," + address.featureName
                }
                location = "$street,$city,$zip,$state"
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return location
    }

    /**
     * @param latitude  latitude of address
     * *
     * @param longitude longitude of address
     * *
     * @return zipcode from location
     */

    fun getCurrentLocationZipCode(latitude: Double, longitude: Double): String {
        var location = ""
        try {
            val geocoder = Geocoder(mContext, Locale.getDefault())
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses.size > 0) {
                val address = addresses[0]
                val zip: String

                if (address.postalCode != null) {
                    zip = address.postalCode
                } else {
                    zip = ""
                }
                location = zip
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return location
    }

    /**
     * Calculate distance between two

     * @param src lat/long of source
     * *
     * @param des lat/lng of destination
     * *
     * @return distance between two location in miles
     */
    fun calculateDistance(src: LatLng, des: LatLng): Double {
        val distance: Double
        var finalValue = 0.0
        try {
            val locationA = Location("")
            locationA.latitude = src.latitude
            locationA.longitude = src.longitude
            val locationB = Location("")
            locationB.latitude = des.latitude
            locationB.longitude = des.longitude
            distance = (locationA.distanceTo(locationB) / 1000).toDouble()
            finalValue = Math.round(distance * 100.0) / 100.0
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return finalValue
    }

    /**
     * to get latitude and longitude of an address

     * @param strAddress address string
     * *
     * @return lat and lng in comma separated string
     */
    fun getLocationFromAddress(strAddress: String): String? {

        val coder = Geocoder(mContext)
        val address: List<Address>?

        try {
            address = coder.getFromLocationName(strAddress, 1)
            if (address == null) {
                return null
            }
            val location = address[0]
            val lat = location.latitude
            val lng = location.longitude

            return lat.toString() + "," + lng
        } catch (e: Exception) {
            return null
        }

    }
}