package com.sampleapp.utils.common

import com.google.gson.annotations.SerializedName
import com.sampleapp.utils.PreferenceManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */
class GetSessionToken @Inject constructor(var tokenService: TokenService, var preferenceManager: PreferenceManager) {

    data class TokenRequestBody(@SerializedName("id") var id: String,
                                @SerializedName("refresh_token") var token: String)

    fun execute(): Observable<SessionToken> {
        return tokenService.getSessionTokenAsync(TokenRequestBody(preferenceManager.userID, preferenceManager.refreshToken))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(ErrorResponseTransformer<SessionToken>())
                .doOnNext {
                    preferenceManager.sessionToken = it.sessionToken ?: ""
                }

    }
}