package com.sampleapp.module

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sampleapp.R
import com.sampleapp.base.SampleApplication
import com.sampleapp.module.sampleactivity.view.SampleActivity
import com.sampleapp.utils.PreferenceManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import io.reactivex.Observable


/**
 * Created by saveen_dhiman on 13-Aug-17.
 * initial screen for application to present app logo to users
 */

class SplashActivity : AppCompatActivity() {

    lateinit @Inject var mPrefs: PreferenceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        (applicationContext as SampleApplication).appComponent!!.inject(this)
        //start next activity after delay of 2 seconds
        Observable.timer(2, TimeUnit.SECONDS).subscribe { aLong ->

            if (mPrefs!!.isUserLoggedIn) {
                startActivity(SampleActivity.createIntent(this@SplashActivity))
            } else {
                startActivity(SampleActivity.createIntent(this@SplashActivity))
            }
            finish()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    override fun onPause() {
        super.onPause()
        finish()
    }
}