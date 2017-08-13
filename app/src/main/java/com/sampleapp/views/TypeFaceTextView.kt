package com.sampleapp.views

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet

import com.sampleapp.utils.Utility

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */

class TypeFaceTextView : AppCompatTextView {

    constructor(context: Context) : super(context) {
        initTypeFace(null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initTypeFace(attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initTypeFace(attrs)
    }

    private fun initTypeFace(attrs: AttributeSet?) {
        if (!isInEditMode) {
            Utility.setTypeFace(this, attrs)
        }
    }

}
