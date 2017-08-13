package com.sampleapp.module.sampleactivity.target

import com.sampleapp.mvpbase.BaseTarget

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */

interface SampleTarget : BaseTarget {

    fun cameraPermissionsGranted()

    fun getDialogType(dialogType: Int)

    fun onCloseJobSuccess()

    fun onUploadImageSuccess(path: String)

    fun onLoginSuccess()
}