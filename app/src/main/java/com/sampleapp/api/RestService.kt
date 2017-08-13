package com.sampleapp.api

import com.sampleapp.model.request.LoginRequest
import com.sampleapp.model.response.Photo
import com.sampleapp.notification.fcm.UpdateLocationFCMToken
import com.sampleapp.module.samplefragment.model.SampleResponse
import io.reactivex.Completable
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */


interface RestService {

       //LOGIN API
    @POST("/api/v2/abc/login")
    fun login(@Header("Authorization") authorization: String, @Body loginRequest: LoginRequest): Observable<Response<SampleResponse>>


    @POST("/api/v2/abc/{id}/update_location")
    fun updateLocationAndFCMToken(@Path("id") id: String, @Body body: UpdateLocationFCMToken.Body): Completable

    @Multipart
    @POST("/api/v2/upload_image")
    fun uploadFile(@Part("type") type: RequestBody, @Part("id") description: RequestBody, @Part file: MultipartBody.Part): Observable<Response<Photo>>

}
