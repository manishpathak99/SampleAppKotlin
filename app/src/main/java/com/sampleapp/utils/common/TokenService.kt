package com.sampleapp.utils.common

import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */
interface TokenService {

    @POST("/api/v2/get_session_token")
    fun getSessionToken(@Body body: GetSessionToken.TokenRequestBody): Call<SessionToken> // using same model as of refresh resfreshToken as response is similar

    @POST("/api/v2/get_session_token")
    fun getSessionTokenAsync(@Body body: GetSessionToken.TokenRequestBody): Observable<Response<SessionToken>>
}