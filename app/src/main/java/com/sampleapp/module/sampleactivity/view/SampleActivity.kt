package com.sampleapp.module.sampleactivity.view

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.support.annotation.RequiresApi
import com.sampleapp.R
import com.sampleapp.interfaces.OnDialogButtonClickListener
import com.sampleapp.base.BaseActivity
import com.sampleapp.constants.ValueConstants
import com.sampleapp.module.sampleactivity.presenter.SamplePresenter
import com.sampleapp.module.sampleactivity.target.SampleTarget
import com.sampleapp.notification.loaction.SendLocationFCMToken
import com.sampleapp.utils.AppUtils
import com.sampleapp.utils.DateTimeUtils
import com.sampleapp.utils.DialogsUtil
import com.sampleapp.utils.ImageUtility
import kotlinx.android.synthetic.main.activity_sample.*
import org.jetbrains.annotations.NotNull
import javax.inject.Inject

/**
 * Created by saveen_dhiman on 13-Aug-17.
 */

class SampleActivity : BaseActivity(), SampleTarget, OnDialogButtonClickListener {


    lateinit @Inject var presenter: SamplePresenter

    lateinit @Inject var mImageUtility: ImageUtility
    lateinit @Inject var mDialogsUtil: DialogsUtil
    lateinit @Inject var mAppUtils: AppUtils
    lateinit @Inject var mDateTimeUtils: DateTimeUtils
    lateinit var sendLcoationFCM: SendLocationFCMToken

    @ValueConstants.DialogType
    private var mDialogType: Int = 0
    private var uriCamera: Uri? = null

    var job_id: String = ""
    var uploadedImagePath: String = ""
    var photourl = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component.inject(this)
        presenter.takeTarget(this)

        // TODO temp solution for now will check it later android N
        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        handleAllClicks();

    }


    fun handleAllClicks() {


        btnlogin_signin.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                presenter.checkMultiplePermissions(ValueConstants.REQUEST_CODE_ASK_CAMERA_PERMISSIONS, this@SampleActivity)
            } else {
                cameraPermissionsGranted()
            }
        }

    }

    companion object {

        @JvmStatic
        fun createIntent(@NotNull context: Context): Intent {

            val intent = Intent(context, SampleActivity::class.java)
            return intent
        }

    }

    override val layout: Int
        get() = R.layout.activity_sample

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropTarget()
    }

    //user clicked positive button of opened dialog
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onPositiveButtonClicked() {
        when (mDialogType) {
            ValueConstants.DialogType.DIALOG_DENY -> presenter.checkMultiplePermissions(ValueConstants.REQUEST_CODE_ASK_CAMERA_PERMISSIONS, this@SampleActivity)
            ValueConstants.DialogType.DIALOG_NEVER_ASK -> mAppUtils.redirectToAppSettings(this@SampleActivity)
        }
    }

    override fun onNegativeButtonClicked() {

    }

    /**
     * Check permission status for camera permission

     * @param requestCode  request code for permission
     * *
     * @param permissions  requested permissions (camera access in this case)
     * *
     * @param grantResults result of approval or denial
     */

    @TargetApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            ValueConstants.REQUEST_CODE_ASK_CAMERA_PERMISSIONS -> presenter.takeActionOnPermissionChanges(this@SampleActivity, grantResults, this@SampleActivity, getString(R.string.explanation_permission_request_camera), getString(R.string.explanation_permission_settings_camera),
                    getString(R.string.action_grant_permission), getString(R.string.action_cancel), getString(R.string.action_goto_settings), mDialogsUtil)
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun cameraPermissionsGranted() {

        uriCamera = presenter.CameraGalleryIntent(this@SampleActivity, ValueConstants.REQUEST_CAMERA, ValueConstants.REQUEST_GALLERY)
    }

    override fun getDialogType(dialogType: Int) {
        mDialogType = dialogType
    }

    override fun onCloseJobSuccess() {

        sendLcoationFCM.execute(this).subscribe({ }) { }


    }

    override fun onUploadImageSuccess(path: String) {
        this.photourl = path
    }

    //on activity result
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                ValueConstants.REQUEST_CAMERA -> try {
                    uploadedImagePath = mImageUtility.compressImage(uriCamera!!.getPath())
//                    ivreport.setImageURI(Uri.parse(uploadedImagePath))
                    presenter.uploadPicture(uploadedImagePath)

                } catch (e: Exception) {
                    e.printStackTrace()
                }

                ValueConstants.REQUEST_GALLERY -> try {
                    if (data != null) {
                        uploadedImagePath = mImageUtility.compressImage(mImageUtility.getRealPathFromURI(data.data, this@SampleActivity))
                    }
//                    ivreport.setImageURI(Uri.parse(uploadedImagePath))

                    presenter.uploadPicture(uploadedImagePath)

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }

        }
    }

    override fun onLoginSuccess() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}