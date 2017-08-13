package com.sampleapp.interfaces

import android.location.Location


/**
 * Created by saveen_dhiman on 13-Aug-17.

 * An interface to receive high-level updates on where your location is.
 * @author aalcock
 */
interface LocationUpdateListener {

    /**
     * Called immediately the service starts if the service can obtain location
     */
    fun canReceiveLocationUpdates()

    /**
     * Called immediately the service tries to start if it cannot obtain location - eg the user has disabled wireless and
     */
    fun cannotReceiveLocationUpdates()

    /**
     * Called whenever the location has changed (at least non-trivially)
     * @param location
     */
    fun updateLocation(location: Location)

    /**
     * Called when GoogleLocationServices detects that the device has moved to a new location.
     * @param localityName The name of the locality (somewhere below street but above area).
     */
    fun updateLocationName(localityName: String, location: Location)
}
