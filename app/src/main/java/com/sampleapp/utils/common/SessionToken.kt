package com.sampleapp.utils.common

import com.google.gson.annotations.SerializedName

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */
data class SessionToken(@SerializedName("id") var id: String?,
                        @SerializedName("session_token") var sessionToken: String?)