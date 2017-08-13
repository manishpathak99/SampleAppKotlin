package com.sampleapp.utils

import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.sampleapp.R

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */

object Utility {


    fun getIntentForEmail(emailTo: String, subject: String, body: String?): Intent {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/html"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailTo))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        if (body != null) {
            intent.putExtra(Intent.EXTRA_TEXT, body)
        }
        return intent

    }

    fun getIntentForSms(number: String, smsBody: String): Intent {
        val intentsms = Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + number))
        intentsms.putExtra("sms_body", smsBody)
        return intentsms

    }


    fun hideKeyBoard(view: EditText?) {
        if (view != null) {
            val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        if (target == null) {
            return false
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    fun setTypeFace(view: View, attrs: AttributeSet?) {
        if (view.isInEditMode)
            return
        var fontname: String? = "latoregular"
        if (attrs != null) {
            val a = view.context.obtainStyledAttributes(attrs, R.styleable.TypeFaceTextView)
            val fontType = if (a == null || a.getString(R.styleable.TypeFaceTextView_font_type) == null) "0" else a.getString(R.styleable.TypeFaceTextView_font_type)

            when (Integer.valueOf(fontType)) {
                1 -> fontname = "roboto_black"
                2 -> fontname = "roboto_blackitalic"

                3 -> fontname = "roboto_bold"

                4 -> fontname = "roboto_bolditalic"

                5 -> fontname = "roboto_italic"

                6 -> fontname = "roboto_light"

                7 -> fontname = "roboto_lightitalic"

                8 -> fontname = "roboto_medium"

                9 -> fontname = "roboto_mediumitalic"

                10 -> fontname = "roboto_regular"

                11 -> fontname = "roboto_thin"

                12 -> fontname = "roboto_thinitalic"

                13 -> fontname = "robotoslab_bold"

                14 -> fontname = "robotoslab_light"

                15 -> fontname = "robotoslab_regular"

                16 -> fontname = "robotoslab_thin.ttf"

                else -> fontname = "roboto_regular"
            }// ?? set the default font here
            a!!.recycle()

        }

        if (fontname != null) {
            val myTypeface = Typeface.createFromAsset(view.context.assets, "fonts/$fontname.ttf")
            if (view is TextView)
                view.typeface = myTypeface
        }
    }

    val osVersion: String
        get() = Integer.toString(Build.VERSION.SDK_INT)

}
