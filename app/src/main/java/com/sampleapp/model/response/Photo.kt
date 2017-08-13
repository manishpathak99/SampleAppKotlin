package com.sampleapp.model.response

import com.google.gson.annotations.SerializedName

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */
data class Photo(@SerializedName("photo_url") var photoUrl: String)