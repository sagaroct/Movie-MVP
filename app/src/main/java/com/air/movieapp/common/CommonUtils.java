/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */

package com.air.movieapp.common;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Created by sagar on 20/8/16.
 */
public class CommonUtils {

    public static Bundle getBundleWithValue(int pos){
        Bundle bundle = new Bundle();
        switch (pos){
            case 0:
                bundle.putSerializable(Constants.TAB, Constants.TOP_RATED);
                break;
            case 1:
                bundle.putSerializable(Constants.TAB, Constants.UPCOMING);
                break;
            case 2:
                bundle.putSerializable(Constants.TAB, Constants.POPULAR);
                break;
        }
        return bundle;
    }

    public static void showDialogToChangeDateFormat(Context context, String title, CharSequence[] charSequence, DialogInterface.OnClickListener onClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setSingleChoiceItems(charSequence, 0, onClickListener);
        String negativeText = context.getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

}
