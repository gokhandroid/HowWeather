package com.gkhnnl.howweather.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;

import com.gkhnnl.howweather.listener.DialogDismissListener;

/**
 * Created by gkhnnl on 15/08/16.
 */
public class ProgressDialogUtil {

    private static ProgressDialog progress;
    private static DialogDismissListener dismissListener;

    public static void ShowDialog(Activity activity, String message, boolean cancelable) {
        ShowProgress(activity, message, cancelable);
    }

    public static void ShowDialog(Activity activity, String message, boolean cancelable, DialogDismissListener _dismissListener) {
        ShowProgress(activity, message, cancelable);
        dismissListener = _dismissListener;
    }

    private static void ShowProgress(Activity activity, String message, boolean cancelable) {

        if (activity == null)
            return;

        if (activity.isFinishing())
            return;

        progress = new ProgressDialog(activity, ProgressDialog.THEME_HOLO_LIGHT);
        progress.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (dismissListener != null)
                    dismissListener.OnDismiss();
            }
        });

        progress.setMessage(message);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(cancelable);
        progress.show();
    }

    public static void Dismiss() {
        if (progress != null)
            progress.dismiss();
    }
}
