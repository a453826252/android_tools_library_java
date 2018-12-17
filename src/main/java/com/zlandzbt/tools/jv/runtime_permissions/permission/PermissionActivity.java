package com.zlandzbt.tools.jv.runtime_permissions.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.zlandzbt.tools.jv.R;

import java.util.ArrayList;
import java.util.Arrays;

public class PermissionActivity extends Activity {

    private static PermissionCallBack mCallBack;

    private static String[] mPermissions;

    private static int mRequestCode;

    private static boolean mRepeat;
    public static void request(String[] permissions,PermissionCallBack callBack,int requestCode,boolean repeat){
        mCallBack = callBack;
        mPermissions = permissions;
        mRequestCode = requestCode;
        mRepeat = repeat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Translucent);
        if(mCallBack == null || mPermissions == null || mPermissions.length<=0){
            throw new IllegalArgumentException("参数异常");
        }
        if(Build.VERSION.SDK_INT >= 23){
            requestPermissions(mPermissions,mRequestCode);
        }else {
            mCallBack.permissionGranted(mPermissions[0], mRequestCode);
            mCallBack.finish(PermissionCallBack.ErrorCode.SUCCESS, "当前设备的Api低于23，无需动态获取权限", mRequestCode);
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode,  @NonNull String[] permissions,  @NonNull int[] grantResults) {
        if(requestCode != mRequestCode){
            return;
        }
        ArrayList<String> permissionDeny = new ArrayList<>();
        boolean granted = true;
        for (int i = 0; i < permissions.length; ++i) {
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                boolean shouldShow = shouldShowRequestPermissionRationale(permissions[i]);
                if(shouldShow && mRepeat){
                    permissionDeny.add(permissions[i]);
                    continue;
                }
                mCallBack.permissonReject(shouldShow,permissions[i],requestCode);
                granted = false;
            }else{
                mCallBack.permissionGranted(permissions[i],requestCode);
            }
        }

        if(mRepeat && !permissionDeny.isEmpty()){
            requestPermissions(permissionDeny.toArray(new String[permissionDeny.size()]),requestCode);
            return;
        }

        if(granted){
            mCallBack.finish(PermissionCallBack.ErrorCode.SUCCESS,"所有权限获取完毕",requestCode);
        }else{
            mCallBack.finish(PermissionCallBack.ErrorCode.FAIL,"至少一个权限获取失败",requestCode);
        }
        finish();
    }

}
