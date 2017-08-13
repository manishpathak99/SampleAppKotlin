package com.sampleapp.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.sampleapp.constants.PrefernceConstants
import com.sampleapp.module.sampleactivity.model.SampleResponse

/**
 * Created by saveen_dhiman on 13-Aug-17.
 * Contains method to store and retrieve SharedPreferences data
 */
class PreferenceManager(context: Context) {

    init {
        Companion.context = context
    }

    //get shared pref
    private val preferences: SharedPreferences
        get() = context!!.getSharedPreferences(PrefernceConstants.PREFERENCE_NAME, Context.MODE_PRIVATE)

    //save data of current logged in user
    var loginResponse: SampleResponse?
        get() {
            val userProfile = preferences.getString(PrefernceConstants.USER_DATA, null)
            if (userProfile == null) {
                return null
            } else {
                return Gson().fromJson(userProfile, SampleResponse::class.java)
            }
        }
        set(loginResponse) = preferences.edit().putString(PrefernceConstants.USER_DATA, Gson().toJson(loginResponse)).apply()

    //set true when user is logegd in else false
    //returns true when user is logged in
    var isUserLoggedIn: Boolean
        get() = preferences.getBoolean(PrefernceConstants.IS_LOGIN, false)
        set(isLogin) {
            preferences.edit().putBoolean(PrefernceConstants.IS_LOGIN, isLogin).apply()
        }

    //set user password while login
    //returns user password while login
    var sessionToken: String
        get() = preferences.getString(PrefernceConstants.SESSION_TOKEN, "")
        set(sessionToken) {
            preferences.edit().putString(PrefernceConstants.SESSION_TOKEN, sessionToken).apply()
        }

    //set user password while login
    //returns user password while login
    var refreshToken: String
        get() = preferences.getString(PrefernceConstants.REFRESH_TOKEN, "")
        set(refreshToken) {
            preferences.edit().putString(PrefernceConstants.REFRESH_TOKEN, refreshToken).apply()
        }

    // returns user id
    //Set user id
    var userID: String
        get() = preferences.getString(PrefernceConstants.USER_ID, "")
        set(userid) {
            preferences.edit().putString(PrefernceConstants.USER_ID, userid).apply()
        }


    //clear user shared preferences
    fun clearPrefrences() {
        preferences.edit().clear().apply()
    }

    companion object {

        var context: Context? = null
            private set
    }

}