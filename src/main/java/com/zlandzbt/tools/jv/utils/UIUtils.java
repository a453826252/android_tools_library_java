package com.zlandzbt.tools.jv.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

public class UIUtils {

    public static void showToast(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context,String msg){
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
    
}
