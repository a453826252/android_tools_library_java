package com.zlandzbt.tools.jv.runtime_permissions.permission;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * @author zhao.botao
 * 2018.12.15
 * android 6.0及以上动态权限获取
 */
public class Permissions {

    private static Permissions permissionsInstance;

    private String mPermissions[];

    private PermissionCallBack mCallBack;

    private Activity mActivity;

    private int mRequestCode;

    private boolean mIsRepeat;


    public static Permissions with(Activity activity) {
        permissionsInstance = new Permissions(activity);
        return permissionsInstance;
    }

    private Permissions(Activity activity) {
        mActivity = activity;
    }

    public Permissions permissions(String[] permissions, int requestCode) {
        mPermissions = permissions;
        mRequestCode = requestCode;
        return Permissions.permissionsInstance;
    }

    public Permissions repeat(boolean repeat) {
        mIsRepeat = repeat;
        return Permissions.permissionsInstance;
    }

    public Permissions callback(PermissionCallBack callBack) {
        mCallBack = callBack;
        return Permissions.permissionsInstance;
    }

    public void request() {
        PermissionActivity.request(mPermissions, mCallBack, mRequestCode, mIsRepeat);
        mActivity.startActivity(new Intent(mActivity, PermissionActivity.class));
        //释放资源，防止内存泄露
        permissionsInstance = null;
    }

    public boolean checkPermission(String permission) {
        return ContextCompat.checkSelfPermission(mActivity,
                permission) == PackageManager.PERMISSION_GRANTED;

    }

    public static String[] getAllUsesPermissionsNameInManifest(Context context) {
        String packageName = context.getPackageName();
        String permissions[] = null;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            permissions = packageInfo.requestedPermissions;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return permissions;
    }
}


