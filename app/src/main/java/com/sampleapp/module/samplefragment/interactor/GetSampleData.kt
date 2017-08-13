package com.sampleapp.module.samplefragment.interactor

import android.util.Base64
import com.sampleapp.utils.common.ErrorResponseTransformer
import com.sampleapp.model.request.LoginRequest
import com.sampleapp.module.samplefragment.model.SampleResponse
import com.sampleapp.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import com.sampleapp.api.RestService

/**
* Created by saveen_dhiman on 13-Aug-17.
*/
class GetSampleData @Inject constructor(val restService: RestService,
                                        val preferenceManager: PreferenceManager) {
    fun executeLogin(email: String, password: String): Observable<SampleResponse> {

        val request = LoginRequest(email, password)
        val basicAuth = "Basic " + String(Base64.encode((email + ":" + password).toByteArray(), Base64.NO_WRAP))

        return restService.login(basicAuth, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ErrorResponseTransformer<SampleResponse>())

    }


}