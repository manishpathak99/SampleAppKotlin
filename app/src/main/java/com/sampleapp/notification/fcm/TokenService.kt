package com.sampleapp.notification.fcm

import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.firebase.jobdispatcher.RetryStrategy
import com.firebase.jobdispatcher.Trigger
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.sampleapp.base.SampleApplication
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */

class TokenService : FirebaseInstanceIdService() {

    @Inject lateinit var updateToken: UpdateLocationFCMToken


    override fun onTokenRefresh() {
        super.onTokenRefresh()
        (applicationContext as SampleApplication).appComponent!!.inject(this)
        Timber.d("FCM Token: " + FirebaseInstanceId.getInstance().token)

        updateToken.execute(UpdateLocationFCMToken.Body(tokenFCM = FirebaseInstanceId.getInstance().token))
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Timber.v("Token sent successfully!")
                }, {
                    //Timber.e(it)
                    Timber.e("Scheduling job to upload")
                    val dispatcher = FirebaseJobDispatcher(GooglePlayDriver(applicationContext))
                    val updateJob = dispatcher.newJobBuilder()
                            .setService(UpdateTokenJob::class.java)
                            .setTag("update-token")
                            .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                            .setTrigger(Trigger.executionWindow(0, 10))
                            .build()
                    dispatcher.mustSchedule(updateJob)
                })
    }
}