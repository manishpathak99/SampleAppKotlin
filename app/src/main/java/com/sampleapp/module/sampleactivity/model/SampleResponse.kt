package com.sampleapp.module.sampleactivity.model

import com.google.gson.annotations.SerializedName

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */

data class SampleResponse(@SerializedName("status") val status: String,
                          @SerializedName("message") val message: String
) {
}