package com.sampleapp.utils.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import com.sampleapp.BuildConfig
import com.sampleapp.utils.RealPathUtils
import java.io.File
import javax.inject.Singleton

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */
@Singleton
open class PhotoHelper  constructor(var packageManager: PackageManager, val context: Context) {

    companion object {
        val PHOTO_GALLERY_REQUEST_CODE: Int = 2
        val CAMERA_REQUEST_CODE: Int = 3
    }

    var tempFile: File? = null

    open fun getCameraIntent(): Intent? {
        val photoPickerIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val infos: List<ResolveInfo> = packageManager.queryIntentActivities(photoPickerIntent, 0)
        if (infos.size > 0) {
            photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(context,
                    BuildConfig.APPLICATION_ID + ".provider",
                    getOutputFilePathForCamera()))

            return photoPickerIntent

        } else {
            return null
        }

    }

    open fun getGalleryIntent(): Intent {
        val photoPickerIntent: Intent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        return photoPickerIntent
    }

    open fun getFile(requestCode: Int, resultCode: Int, data: Intent?): File? {
        if (requestCode == PHOTO_GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            return RealPathUtils().getFileFromUri(context,data.data)
        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            return tempFile
        }

        return null
    }

    private fun getOutputFilePathForCamera(): File? {
        val outputDir = context.cacheDir // context being the Activity pointer
        tempFile = File.createTempFile("IMG_", "jgp", outputDir)
        return tempFile // let this be one file

    }


}