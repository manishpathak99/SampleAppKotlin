package com.sampleapp.constants

/**
 * Created by saveen_dhiman on 13-Aug-17.
 * Contains all the keys used in SharedPrefernce of application
 */
interface PrefernceConstants {
    companion object {
        val PREFERENCE_NAME = "SAMPLE_PREFERENCES"
        val USER_DATA = "user_data"
        val IS_LOGIN = "isLogin"
        val USER_EMAIL = "user_email"
        val USER_PASSWORD = "user_password"
        val SESSION_TOKEN = "session_token"
        val REFRESH_TOKEN = "refresh_token"
        val USER_ID = "user_id"
    }
}