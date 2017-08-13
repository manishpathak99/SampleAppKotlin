package com.sampleapp.extenstions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sampleapp.utils.CropCircleTransformation
import java.io.File

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */
fun ImageView.load(url: String?) {
    if (url.isNullOrBlank()) {
        //setIMa
    } else {
        if (url!!.startsWith("http://") || url.startsWith("https://")) {
            Glide.with(this.context).load(url).crossFade(300).into(this)
        } else {
            Glide.with(this.context).load(File(url)).crossFade(300).into(this)
        }
    }
}

fun ImageView.loadAvatar(url: String?) {
    if (url == null)
        return

    if (url.startsWith("http://") || url.startsWith("https://")) {
        Glide.with(this.context).load(url).crossFade(300).bitmapTransform(CropCircleTransformation(context)).into(this)
    } else {
        Glide.with(this.context).load(File(url)).crossFade(300).bitmapTransform(CropCircleTransformation(context)).into(this)
    }


}