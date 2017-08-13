package com.sampleapp.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.provider.Settings
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.text.Html
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast

import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.sampleapp.R
import com.sampleapp.constants.ApiConstants

import org.json.JSONObject

import java.io.File
import java.net.ConnectException
import java.net.UnknownHostException

import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * Created by saveen_dhiman on 13-Aug-17.
 *
 *
 * Contains commonly used methods in an Android App
 */
class AppUtils(private val mContext: Context) {

    /**
     * Description : Check if user is online or not

     * @return true if online else false
     */
    fun isOnline(v: View): Boolean {
        val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        if (netInfo != null && netInfo.isConnectedOrConnecting) {
            return true
        }
        showSnackBar(v, mContext.getString(R.string.toast_network_not_available))
        return false
    }


    /**
     * Description : Hide the soft keyboard

     * @param view : Pass the current view
     */
    fun hideSoftKeyboard(view: View) {
        val inputMethodManager = mContext.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    /**
     * Show snackbar

     * @param view view clicked
     * *
     * @param text text to be displayed on snackbar
     */
    fun showSnackBar(view: View, text: String) {
        Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
    }

    /**
     * Show snackbar

     * @param text text to be displayed on Toast
     */
    fun showToast(text: String) {
        Toast.makeText(mContext, text, Toast.LENGTH_LONG).show()
    }


    /**
     * show related error message to user on api failure
     */
    fun showErrorMessage(view: View, t: Throwable) {
        showSnackBar(view, getErrorMessage(t))
    }

    //return error message from webservice error code
    private fun getErrorMessage(throwable: Throwable): String {
        val errorMessage: String
        if (throwable is UnknownHostException || throwable is ConnectException) {
            errorMessage = mContext.resources.getString(R.string.warning_network_error)
        } else {
            errorMessage = "Unfortunately an error has occurred!"
        }
        return errorMessage
    }

    /**
     * send email via. intent

     * @param email to whom you want to send email
     */
    fun sendEmail(context: Context, email: String) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, email)
        try {
            context.startActivity(Intent.createChooser(i, context.resources.getString(R.string.send_mail)))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(context, context.resources.getString(R.string.no_email_client), Toast.LENGTH_SHORT).show()
        }

    }


    fun sendEmailPropertyIssue(context: Context, job_id: String) {

        val emailIntent = Intent(Intent.ACTION_SENDTO)
        emailIntent.data = Uri.parse("mailto:" + "")
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, job_id)
        emailIntent.putExtra(Intent.EXTRA_TEXT, "")

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send email using..."))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(context, "No email clients installed.", Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * To open a website in phone browser

     * @param address valid email link
     */
    fun openBrowser(address: String, activity: Activity) {
        var address = address
        try {
            if (!address.startsWith("http://") && !address.startsWith("https://")) {
                address = "http://" + address
            }
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(address))
            activity.startActivity(browserIntent)
        } catch (e: Exception) {
            showToast(mContext.resources.getString(R.string.warning_invalid_link))
        }

    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    fun checkPlayServices(): Boolean {
        val apiAvailability = GoogleApiAvailability.getInstance()
        val resultCode = apiAvailability.isGooglePlayServicesAvailable(mContext)
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(mContext as Activity, resultCode, 9000)
                        .show()
            } else {
                showToast(mContext.resources.getString(R.string.warning_play_services))
            }
            return false
        }
        return true
    }

    /**
     * redirect user to your application settings in device
     */
    fun redirectToAppSettings(context: Context) {
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", mContext.packageName, null)
        intent.data = uri
        context.startActivity(intent)
    }

    /**
     * check if user has enabled Gps of device

     * @return true or false depending upon device Gps status
     */
    val isGpsEnabled: Boolean
        get() {
            val manager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

    /**
     * Redirect user to enable GPS
     */
    fun goToGpsSettings() {
        val callGPSSettingIntent = Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        mContext.startActivity(callGPSSettingIntent)
    }


    fun callByIntent(number: String, activity: Activity) {

        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number))
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        activity.startActivity(intent)
    }


    /**
     * Get error message from api's if status code is 400, 401

     * @return error message from response
     */
    fun getErrorMessage(errormsg: String): String {

        var errormessage = ""
        try {
            val jObjError = JSONObject(errormsg)
            errormessage = jObjError.getString(ApiConstants.geterrorMessage)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return errormessage
    }

    //return multipart string
    fun getRequestString(text: String): RequestBody {
        return RequestBody.create(MediaType.parse("text/plain"), text)
    }

    /**
     * get request body image
     */
    fun getRequestBodyImage(path: String): RequestBody {
        val file = File(path)
        return RequestBody.create(MediaType.parse("image/png"), file)
    }

    /**
     * Text to html form

     * @param html
     * *
     * @return
     */

    fun fromHtml(html: String): Spanned {
        val result: Spanned
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY)
        } else {
            result = Html.fromHtml(html)
        }
        return result
    }


    /**
     * Capitalized the first letter in any string

     * @param passString
     * *
     * @return
     */
    fun capitalizedFirstLetter(passString: String): String {

        val sb = StringBuilder(passString)
        sb.setCharAt(0, Character.toUpperCase(sb[0]))
        return sb.toString()
    }

    /**
     * Open play store
     * @param context
     * *
     * @param appPackageName
     */
    fun openAppInGooglePlay(context: Context, appPackageName: String) {
        //        final String appPackageName = context.getPackageName();
        try {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)))
        } catch (e: android.content.ActivityNotFoundException) { // if there is no Google Play on device
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)))
        }

    }
}