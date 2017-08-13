package com.sampleapp.module.sampleactivity.interactor

import android.util.Base64
import com.sampleapp.api.RestService
import com.sampleapp.utils.common.ErrorResponseTransformer
import com.sampleapp.constants.ApiConstants
import com.sampleapp.model.request.LoginRequest
import com.sampleapp.model.response.Photo
import com.sampleapp.utils.AppUtils
import com.sampleapp.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */

class SampleData @Inject constructor(val restService: RestService,
                                     val preferenceManager: PreferenceManager, val mAppUtils: AppUtils) {

    fun executeLogin(email: String, password: String): Observable<com.sampleapp.module.samplefragment.model.SampleResponse> {

        val request = LoginRequest(email, password)
        val basicAuth = "Basic " + String(Base64.encode((email + ":" + password).toByteArray(), Base64.NO_WRAP))

        return restService.login(basicAuth, request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ErrorResponseTransformer<com.sampleapp.module.samplefragment.model.SampleResponse>())

    }

    fun executeUploadImage(filepath: String): Observable<Photo> {

        val file = File(filepath)
        // create RequestBody instance from file
        val requestFile: RequestBody =
                RequestBody.create(
                        MediaType.parse("multipart/form-data"),
                        file)

        val uploadImage: MultipartBody.Part =
                MultipartBody.Part.createFormData("upload[datafile]", file.name, requestFile)

        return restService.uploadFile(mAppUtils.getRequestString(preferenceManager.userID), mAppUtils.getRequestString(ApiConstants.type), uploadImage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ErrorResponseTransformer<Photo>())
    }

}