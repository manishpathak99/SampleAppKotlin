package com.sampleapp.module.sampleactivity.presenter

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import com.sampleapp.R
import com.sampleapp.constants.ValueConstants
import com.sampleapp.error.SampleException
import com.sampleapp.interfaces.OnDialogButtonClickListener
import com.sampleapp.module.sampleactivity.interactor.SampleData
import com.sampleapp.module.sampleactivity.target.SampleTarget
import com.sampleapp.mvpbase.BasePresenter
import com.sampleapp.utils.DialogsUtil
import com.sampleapp.utils.ImageUtility
import com.sampleapp.utils.PreferenceManager
import java.io.File
import javax.inject.Inject

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */


class SamplePresenter @Inject constructor(val sampleData: SampleData, val mImgUtils : ImageUtility,val mPrefs: PreferenceManager) : BasePresenter<SampleTarget>() {


    fun tryUserLogin(email: String, password: String) {
        sampleData.executeLogin(email, password)
                .doOnSubscribe { target?.showProgressDialog() }
                .doFinally { target?.closeProgressDialog() }
                .subscribe({

                    mPrefs.isUserLoggedIn = true
                    mPrefs.refreshToken = it.refresh_token
                    mPrefs.userID = it.id

                    target?.onLoginSuccess()
                }, {
                    target?.showErrorMessage(SampleException(1, "Something went wrong!"))
                })
    }


    fun uploadPicture(filepath : String) {
        sampleData.executeUploadImage(filepath)
                .doOnSubscribe { target?.showProgressDialog() }
                .doFinally { target?.closeProgressDialog() }
                .subscribe({
                    target?.onUploadImageSuccess(it.photoUrl)
                }, {
                    target?.showErrorMessage(SampleException(1, "Upload error! "))
                })

    }

    //check for camera and storage access permissions
    @TargetApi(Build.VERSION_CODES.M)
    fun checkMultiplePermissions(permissionCode: Int, context: Context) {

        val PERMISSIONS = arrayOf(ValueConstants.CAMERA_PERMISSION, ValueConstants.READ_EXTERNAL_STORAGE_PERMISSION, ValueConstants.WRITE_EXTERNAL_STORAGE_PERMISSION)
        if (!hasPermissions(context, *PERMISSIONS)) {
            ActivityCompat.requestPermissions(context as Activity, PERMISSIONS, permissionCode)
        } else {
            target?.cameraPermissionsGranted()
        }
    }

    private fun hasPermissions(context: Context?, vararg permissions: String): Boolean {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    //check whether user accepted or rejected the permissions
    @TargetApi(Build.VERSION_CODES.M)
    fun takeActionOnPermissionChanges(mActivity : Activity?, grantResults: IntArray, onDialogButtonClickListener: OnDialogButtonClickListener, mRequestPermissions: String, mRequsetSettings: String, mGrantPermissions: String, mCancel: String, mGoToSettings: String
                                      , mDialogUtils : DialogsUtil) {
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
            target?.cameraPermissionsGranted()
        } else {
            val showRationale1 = mActivity!!.shouldShowRequestPermissionRationale(ValueConstants.CAMERA_PERMISSION)
            val showRationale2 = mActivity.shouldShowRequestPermissionRationale(ValueConstants.READ_EXTERNAL_STORAGE_PERMISSION)
            val showRationale3 = mActivity.shouldShowRequestPermissionRationale(ValueConstants.WRITE_EXTERNAL_STORAGE_PERMISSION)
            if (showRationale1 && showRationale2 && showRationale3) {
                //explain to user why we need the permissions
                target?.getDialogType(ValueConstants.DialogType.DIALOG_DENY)
                mDialogUtils.openAlertDialog(mActivity, mRequestPermissions, mGrantPermissions, mCancel, onDialogButtonClickListener)
            } else {
                //explain to user why we need the permissions and ask him to go to settings to enable it
                target?.getDialogType(ValueConstants.DialogType.DIALOG_NEVER_ASK)
                mDialogUtils.openAlertDialog(mActivity, mRequsetSettings, mGoToSettings, mCancel, onDialogButtonClickListener)
            }
        }
    }

    fun CameraGalleryIntent( mActivity : Activity? ,cameraRequestCode: Int, galleryRequestCode: Int): Uri {

        val root = mImgUtils.file
        root.mkdirs()
        val filename = mImgUtils.uniqueImageFilename
        val sdImageMainDirectory = File(root, filename)
        val outputFileUri = Uri.fromFile(sdImageMainDirectory)
        //        final Uri outputFileUri = FileProvider.getUriForFile(mActivity, mActivity.getApplicationContext().getPackageName() + ".provider", sdImageMainDirectory);
        val dialog = AlertDialog.Builder(mActivity!!, R.style.MyDialogTheme)
        val items = arrayOf<CharSequence>(mActivity.getResources().getString(R.string.camera), mActivity.getResources().getString(R.string.gallery))
        dialog.setItems(items) { d, n ->
            when (n) {
                0 -> {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri)
                    mActivity.startActivityForResult(intent, cameraRequestCode)
                }
                1 -> {
                    val pickIntent = Intent(Intent.ACTION_PICK)
                    pickIntent.type = "image/*"
                    mActivity.startActivityForResult(pickIntent, galleryRequestCode)
                }
                else -> {
                }
            }
        }
        dialog.setTitle(mActivity.getResources().getString(R.string.selection))
        if (outputFileUri != null) {
            dialog.show()
        }
        return outputFileUri
    }
}