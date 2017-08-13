package com.sampleapp.notification.fcm

import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.google.firebase.iid.FirebaseInstanceId
import com.sampleapp.base.SampleApplication
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */

class UpdateTokenJob : JobService() {
    @Inject lateinit var updateToken: UpdateLocationFCMToken

    var disposable: Disposable? = null


    //Returns "is there still work to do"
    override fun onStartJob(job: JobParameters): Boolean {
        (applicationContext as SampleApplication).appComponent!!.inject(this)
        disposable = updateToken.execute(UpdateLocationFCMToken.Body(tokenFCM = FirebaseInstanceId.getInstance().token))
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Timber.v("Token sent successfully!")
                    jobFinished(job, false)
                }, {
                    jobFinished(job, true)
                })
        return false
    }


    //Returns "Should this job be retired"
    override fun onStopJob(job: JobParameters): Boolean {
        disposable?.dispose()
        return true
    }


}