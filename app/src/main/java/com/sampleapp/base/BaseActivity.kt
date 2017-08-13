package com.sampleapp.base

import android.app.AlertDialog
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity

import com.sampleapp.R
import com.sampleapp.mvpbase.BaseTarget
import com.sampleapp.error.SampleException

import dmax.dialog.SpotsDialog

/**
 * Created by saveen_dhiman on 13-Aug-17.
 * To be extended by all Activities
 */
abstract class BaseActivity : AppCompatActivity(), BaseTarget {

    internal var progressDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
    }

    /**
     * get layout to inflate
     */
    @get:LayoutRes
    abstract val layout: Int

    override fun onStart() {
        super.onStart()
        //Kahuna.getInstance().start();
    }

    override fun onStop() {
        super.onStop()
        //Kahuna.getInstance().stop();
    }

    override fun showProgressDialog(message: String?) {
        if (this@BaseActivity == null)
            return

        if (progressDialog == null)
            progressDialog = SpotsDialog(this@BaseActivity, message ?: "Please wait")
        progressDialog!!.setCancelable(false)

        progressDialog!!.show()
    }

    override fun closeProgressDialog() {
        if (progressDialog != null)
            progressDialog!!.dismiss()
    }

    fun showGeneralAlert(positiveButtonText: String?, negativeButtonText: String?, message: String) {
        val builder = AlertDialog.Builder(this@BaseActivity)
        if (positiveButtonText != null)
            builder.setPositiveButton(positiveButtonText) { dialog, which -> dialog.dismiss() }
        if (negativeButtonText != null)
            builder.setNegativeButton(negativeButtonText) { dialog, which -> dialog.dismiss() }
        builder.setTitle(resources.getString(R.string.app_name))
        builder.setMessage(message)
        builder.setCancelable(false)
        builder.create().show()
    }

    override fun showErrorMessage(exception: SampleException) {
        showGeneralAlert("OK", null, exception.getLocalisedString())
    }

    val component: AppComponent
        get() = (this.application as SampleApplication).appComponent!!

}