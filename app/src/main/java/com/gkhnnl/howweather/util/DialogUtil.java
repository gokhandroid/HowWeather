package com.gkhnnl.howweather.util;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.gkhnnl.howweather.R;
import com.gkhnnl.howweather.listener.DialogListener;


public class DialogUtil {

    public static void showDialog(Activity activity, String title, String message, String positive, String negative, final DialogListener dialogListener) {

        if (activity == null)
            return;

        if (activity.isFinishing())
            return;

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity, R.style.DialogTheme);
        dialogBuilder.setTitle(title);
        dialogBuilder.setMessage(message);
        dialogBuilder.setCancelable(false);
        dialogBuilder.setNegativeButton(negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogBuilder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (dialogListener != null)
                    dialogListener.onPositive();
                dialog.dismiss();
            }
        });
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();
    }
}
