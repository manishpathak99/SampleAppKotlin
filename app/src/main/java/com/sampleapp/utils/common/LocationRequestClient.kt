package com.sampleapp.utils.common

import android.content.Context
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.sampleapp.utils.common.GoogleAPIClient
import io.reactivex.Observable
import javax.inject.Inject


/**
 * Created by saveen_dhiman on 13-Aug-17.
 */


class LocationRequestClient @Inject constructor(val plowzGoogleClient: GoogleAPIClient) {

    fun execute(context: Context): Observable<Status> {
        return plowzGoogleClient.execute(context)
                .flatMap {
                    getStatusCodeOfLocation(it)
                }


    }

    fun getStatusCodeOfLocation(googleApiClient: GoogleApiClient): Observable<Status> {
        return Observable.create<Status> {
            var request = LocationRequest()
            request.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

            val builder = LocationSettingsRequest.Builder()
                    .addLocationRequest(request)

            val result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
            result.setResultCallback { locationResult ->
                var status = locationResult.status
                it.onNext(status)
                it.onComplete()
            }
        }
    }
}