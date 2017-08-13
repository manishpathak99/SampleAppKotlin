 package com.sampleapp.constants;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by saveen_dhiman on 13-Aug-17.
 * contains constant values used in application
 */
public interface ValueConstants {

    //requests for runtime time permissions
    String CAMERA_PERMISSION = android.Manifest.permission.CAMERA;
    String READ_EXTERNAL_STORAGE_PERMISSION = android.Manifest.permission.READ_EXTERNAL_STORAGE;
    String WRITE_EXTERNAL_STORAGE_PERMISSION = android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

    String CALL_PHONE_PERMISSION = android.Manifest.permission.CALL_PHONE;

    String ACCESS_FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    String ACCESS_COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    String ACCESS_NETWORK_STATE = android.Manifest.permission.ACCESS_NETWORK_STATE;

    int REQUEST_CAMERA = 1001;
    int REQUEST_GALLERY = 1002;
    int REQUEST_ALBUMS = 1003;
    int REQUEST_CODE_ASK_PHONE_PERMISSIONS = 1004;
    int REQUEST_CODE_ASK_CAMERA_PERMISSIONS= 1005;
    int REQUEST_CODE_ASK_LOCATION_PERMISSIONS= 1006;
    int REQUEST_CODE_SEND_BLANK_INTENT = 1007;

    String DEVICE_TYPE = "android";
    String NEW_THREAD = "newThread";
    String MAIN_THREAD = "mainThread";
    String DATE_INPUT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    String DATE_OUTPUT_FORMAT = "EEEE, h:mm a";

    // type check for error during login
    @IntDef({ValidationType.VALID, ValidationType.USER_NAME_INVALID, ValidationType.PASSWORD_INVALID})
    @Retention(RetentionPolicy.SOURCE)
    @interface ValidationType {
        int VALID = 0, USER_NAME_INVALID = 1, PASSWORD_INVALID = 2;
    }

    // type of dialog opened in MainActivity
    @IntDef({DialogType.DIALOG_DENY, DialogType.DIALOG_NEVER_ASK})
    @Retention(RetentionPolicy.SOURCE)
    @interface DialogType {
        int DIALOG_DENY = 0, DIALOG_NEVER_ASK = 1;
    }

    // type of dialog opened in MainActivity
//    @IntDef({DialogTypeLocation.DIALOG_DENY, DialogTypeLocation.DIALOG_NEVER_ASK, DialogTypeLocation.DIALOG_LOCATION})
//    @Retention(RetentionPolicy.SOURCE)
//    @interface DialogTypeLocation {
//        int DIALOG_DENY = 0, DIALOG_NEVER_ASK = 1, DIALOG_NOTI_SETTINGS = 2, DIALOG_LOCATION = 3;
//    }


}
