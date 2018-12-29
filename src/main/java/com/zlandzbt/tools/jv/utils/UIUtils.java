package com.zlandzbt.tools.jv.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Toast;

public class UIUtils {

    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static AlertDialog alertDialog(Activity activity, String title, String msg, final IAlertDialogCallBack callBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog dialog = builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.yes(dialog, which);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.no(dialog, which);
                    }
                }).create();
        dialog.show();
        return dialog;
    }

    public static AlertDialog alertDialogWithView(Activity activity, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog dialog = builder.setView(view).create();
        dialog.show();
        return dialog;
    }

    public static AlertDialog alertDialogWithView(Activity activity, View view,final IAlertDialogCallBack callBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callBack.yes(dialog,which);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.no(dialog,which);
                    }
                });
        AlertDialog dialog = builder.setView(view).create();
        dialog.show();
        return dialog;
    }

    public static AlertDialog showAlertMessage(Activity activity, String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        AlertDialog dialog = builder.setTitle(title)
                .setMessage(msg)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                }).create();
        dialog.show();
        return dialog;
    }

    public static ProgressDialog showLoading(Activity activity, String msg) {
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage(msg);
        progressDialog.show();
        return progressDialog;
    }

    public interface IAlertDialogCallBack {
        void yes(DialogInterface dialogInterface, int which);

        void no(DialogInterface dialogInterface, int which);
    }
}
