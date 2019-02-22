package com.zlandzbt.tools.jv.runtime_permissions.permission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.zlandzbt.tools.jv.R;

import java.util.ArrayList;

/**
 * @author zhao.botao
 * 2018.12.15
 * 透明主题，协助动态权限获取
 */
public class PermissionActivity extends Activity {

    private static PermissionCallBack mCallBack;

    private static String[] mPermissions;

    private static int mRequestCode;

    private static boolean mRepeat;

    public static void request(String[] permissions, PermissionCallBack callBack, int requestCode, boolean repeat) {
        mCallBack = callBack;
        mPermissions = permissions;
        mRequestCode = requestCode;
        mRepeat = repeat;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Translucent);
        if (mPermissions == null || mPermissions.length <= 0) {
            throw new IllegalArgumentException("参数异常");
        }
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(mPermissions, mRequestCode);
        } else {
            permissionGranted(mPermissions[0], mRequestCode);
            permissionFinish(PermissionCallBack.ErrorCode.SUCCESS, "当前设备的Api低于23，无需动态获取权限", mRequestCode);
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != mRequestCode) {
            return;
        }
        ArrayList<String> permissionDeny = new ArrayList<>();
        boolean granted = true;
        for (int i = 0; i < permissions.length; ++i) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                boolean shouldShow = shouldShowRequestPermissionRationale(permissions[i]);
                if (shouldShow && mRepeat) {
                    permissionDeny.add(permissions[i]);
                    continue;
                }
                permissionReject(shouldShow, permissions[i], requestCode);
                granted = false;
            } else {
                permissionGranted(permissions[i], requestCode);
            }
        }

        if (mRepeat && !permissionDeny.isEmpty()) {
            requestPermissions(permissionDeny.toArray(new String[permissionDeny.size()]), requestCode);
            return;
        }

        if (granted) {
            permissionFinish(PermissionCallBack.ErrorCode.SUCCESS, "所有权限获取完毕", requestCode);
        } else {
            permissionFinish(PermissionCallBack.ErrorCode.FAIL, "至少一个权限获取失败", requestCode);
        }
        finish();
    }

    private void permissionGranted(String permission, int code) {
        if (mCallBack != null) {
            mCallBack.permissionGranted(permission, code);
        }
    }

    private void permissionReject(boolean s, String m, int c) {
        if (mCallBack != null) {
            mCallBack.permissionReject(s, m, c);
        }
    }

    private void permissionFinish(int e, String m, int c) {
        if (mCallBack != null) {
            mCallBack.finish(e, m, c);
        }
    }
}
