package com.sampleapp.module.samplefragment.model

import com.google.gson.annotations.SerializedName

/**
* Created by saveen_dhiman on 13-Aug-17.
*/

data class SampleResponse(@SerializedName("id") val id: String,
                          @SerializedName("refresh_token") val refresh_token: String) {


}