package com.sampleapp.module.samplefragment.view

import android.os.Bundle
import android.view.View
import com.sampleapp.R
import com.sampleapp.base.BaseFragment
import com.sampleapp.module.sampleactivity.view.SampleActivity
import com.sampleapp.module.samplefragment.presenter.SamplePresenter
import com.sampleapp.module.samplefragment.target.SampleTarget
import com.sampleapp.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_sample.*
import javax.inject.Inject

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */

class SampleFragment : BaseFragment(), SampleTarget {

    lateinit @Inject var presenter: SamplePresenter
    lateinit @Inject var mAppUtils: AppUtils

    internal var phonenumber = ""

    override val layoutId: Int
        get() = R.layout.fragment_sample

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.inject(this)
        presenter.takeTarget(this)


        handleOnClick()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropTarget()
    }

    override fun onLoginSuccess() {

        startActivity(SampleActivity.createIntent(activity))
        activity.finish()
    }

    fun handleOnClick() {
//        mAppUtils.hideSoftKeyboard(edtUserEmail)
//
//        btnlogin_signin.setOnClickListener {
//            if (mAppUtils.isOnline(edtUserEmail))
//                if (edtUserEmail.text.isNotEmpty() && edtUserPassword.text.isNotEmpty()) {
//                    mAppUtils.hideSoftKeyboard(edtUserEmail)
//                    presenter.tryUserLogin(edtUserEmail.getText().toString(), edtUserPassword.getText().toString())
//
//                } else {
//                    mAppUtils.showSnackBar(edtUserEmail, getString(R.string.warning_field_empty))
//                }
//        }
    }

}