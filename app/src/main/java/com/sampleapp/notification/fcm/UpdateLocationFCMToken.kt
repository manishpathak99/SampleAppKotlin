package com.sampleapp.notification.fcm

import android.location.Location
import com.google.gson.annotations.SerializedName
import com.sampleapp.api.RestService
import com.sampleapp.utils.PreferenceManager
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */
class UpdateLocationFCMToken @Inject constructor(val restService : RestService,
                                                 val preferenceManager: PreferenceManager) {

    data class Body(@SerializedName("google_token") var tokenFCM: String? = null,
                    var location: Location? = null,
                    @SerializedName("coordinates") private var commaSeparatedLatLng: String? = null) {
        init {
            if (location != null) {
                commaSeparatedLatLng = location?.latitude.toString() + "," + location?.longitude.toString()
                location = null
            }
        }
    }

    fun execute(body: Body): Completable {
        if (preferenceManager.userID.isNullOrEmpty())
            return Completable.complete()

        return restService.updateLocationAndFCMToken(preferenceManager.userID, body)
                .subscribeOn(Schedulers.io())

    }
}