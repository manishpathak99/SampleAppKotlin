package com.sampleapp.module.samplefragment.presenter

import com.sampleapp.error.SampleException
import com.sampleapp.mvpbase.BasePresenter
import com.sampleapp.module.samplefragment.interactor.GetSampleData
import com.sampleapp.module.samplefragment.target.SampleTarget
import com.sampleapp.utils.PreferenceManager
import javax.inject.Inject

/**
* Created by saveen_dhiman on 13-Aug-17.
*/
class SamplePresenter @Inject constructor(val getLoginData: GetSampleData, val mPrefs: PreferenceManager) : BasePresenter<SampleTarget>() {

    fun tryUserLogin(email: String, password: String) {
        getLoginData.executeLogin(email, password)
                .doOnSubscribe { target?.showProgressDialog() }
                .doFinally { target?.closeProgressDialog() }
                .subscribe({

                    mPrefs.isUserLoggedIn = true
                    mPrefs.refreshToken = it.refresh_token
                    mPrefs.userID = it.id

                    target?.onLoginSuccess()
                }, {
                    target?.showErrorMessage(SampleException(1, "Something went wrong!"))
                })
    }
}