package com.sampleapp.utils.common

import com.sampleapp.base.SampleApplication
import com.sampleapp.utils.PreferenceManager
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */
class TokenAuthenticator constructor(var preferenceManager: PreferenceManager) : Authenticator {

    @Inject lateinit var tokenService: TokenService

    override fun authenticate(route: Route?, response: Response?): Request? {
        if (preferenceManager.refreshToken.isNotEmpty()) {
            (PreferenceManager.context!!.applicationContext as SampleApplication).appComponent!!.inject(this)

            val sessionTokenService = tokenService.getSessionToken(GetSessionToken.TokenRequestBody(preferenceManager.userID, preferenceManager.refreshToken)).execute()

            if (sessionTokenService.isSuccessful) {
                val sessionToken = sessionTokenService.body().sessionToken
                preferenceManager.sessionToken = sessionToken!!
                return response?.request()?.newBuilder()?.header("Authorization", "Bearer " + sessionToken)?.build()
            } else {
                return null
            }
        } else {
            return null // I don't even care for those who does not even have minimum respect for resfresh Token :P
        }
    }
}