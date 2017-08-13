package com.sampleapp.views

import android.content.Context
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet


/**
 * Created by saveen_dhiman on 13-Aug-17.
 * Custom editText to remove error on editText field when onTextChange is called
 */
class RemoveErrorEditText : AppCompatEditText {

    constructor(context: Context) : super(context) {
        removeError()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        removeError()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        removeError()
    }

    private fun removeError() {

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun afterTextChanged(editable: Editable) {
                error = null
            }
        })
    }
}
