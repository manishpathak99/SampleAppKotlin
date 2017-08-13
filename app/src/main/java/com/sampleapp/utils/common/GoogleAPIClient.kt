package com.sampleapp.utils.common

import android.content.Context
import android.os.Bundle
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.sampleapp.error.SampleException
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */
class GoogleAPIClient @Inject constructor() {
    var googleApiClient: GoogleApiClient? = null

    fun execute(context: Context): Observable<GoogleApiClient> {
        return Observable.create<GoogleApiClient> {
            googleApiClient = GoogleApiClient.Builder(context)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(object : GoogleApiClient.ConnectionCallbacks {
                        override fun onConnected(p0: Bundle?) {
                            it.onNext(googleApiClient)
                            it.onComplete()
                        }

                        override fun onConnectionSuspended(p0: Int) {
                            it.onError(SampleException(0))
                        }

                    })
                    .addOnConnectionFailedListener { i ->
                        it.onError(SampleException(0))
                    }
                    .build()
            googleApiClient?.connect()

        }
    }
}