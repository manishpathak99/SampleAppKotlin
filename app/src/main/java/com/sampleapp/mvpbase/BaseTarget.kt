package com.sampleapp.mvpbase

import com.sampleapp.error.SampleException

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */
interface BaseTarget {
    fun showErrorMessage(exception: SampleException) {}
    fun showProgressDialog(message: String? = null) {}
    fun closeProgressDialog() {}

    fun sendErrorToTarget(it: Throwable) {
        if (it is SampleException)
            showErrorMessage(exception = it)
        else
            showErrorMessage(SampleException(-1)) // unknown
    }
}