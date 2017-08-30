package com.sampleapp.notification.loaction

import android.content.Context
import com.google.firebase.iid.FirebaseInstanceId
import com.sampleapp.notification.fcm.UpdateLocationFCMToken
import io.reactivex.Completable
import javax.inject.Inject

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */
class SendLocationFCMToken @Inject constructor(val getLocation: GetLocation,
                                               val updateLocationFCMToken: UpdateLocationFCMToken) {


    fun execute(context: Context): Completable {
        return getLocation.execute(context)
                .flatMapCompletable {
                    updateLocationFCMToken.execute(UpdateLocationFCMToken.Body(tokenFCM = FirebaseInstanceId.getInstance().token, location = it))

                }
    }


}