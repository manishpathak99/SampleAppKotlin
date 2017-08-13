package com.sampleapp.error

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */
class SampleException(var code: Int, var serverError: String = "Unknown Error") : RuntimeException() {

    fun getLocalisedString(): String {

        return when (code) {
            SampleErrorTypes.EMAIL_ERROR.code -> "EMAIL not valid"
            SampleErrorTypes.MOBILE_ERROR.code -> "Mobile number not valid"
            SampleErrorTypes.NAME_ERROR.code -> "Name can not be blank"
            SampleErrorTypes.PASSWORD_ERROR.code -> "Password can not be blank"
            else -> {
                return serverError
            }
        }
    }
}
