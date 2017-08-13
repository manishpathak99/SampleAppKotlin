package com.sampleapp.notification.loaction

import android.content.Context
import android.location.Location
import com.sampleapp.utils.LocationTracker
import io.reactivex.Observable
import javax.inject.Inject


/**
 * Created by saveen_dhiman on 13-Aug-17.
 */
class GetLocation @Inject constructor() {


    fun execute(context: Context): Observable<Location> {
        return Observable.create<Location> {
            var locationTracker: LocationTracker = LocationTracker(context)
            locationTracker.setLocationListener { location ->
                it.onNext(location)
                locationTracker.stopLocationUpdates()
            }
        }
    }


}