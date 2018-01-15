/*
 *
 *  * Copyright © 2016, Robosoft Technologies Pvt. Ltd
 *  * Written under contract by Robosoft Technologies Pvt. Ltd.
 *
 */

package com.air.movieapp.util.common;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public static void showDialogToChangeDateFormat(Context context, String title, CharSequence[] charSequence, int selectedPref, DialogInterface.OnClickListener onClickListener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setSingleChoiceItems(charSequence, selectedPref, onClickListener);
        String negativeText = context.getString(android.R.string.cancel);
        builder.setNegativeButton(negativeText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        final AlertDialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public static String convertMovieDateFormat(String strDate, String dateFormat){
        DateFormat parseDateFormat;
        DateFormat convertDateFormat;
        if(dateFormat.equalsIgnoreCase(Constants.YEAR_FIRST)){
            convertDateFormat = new SimpleDateFormat("yyyy-MM-d");
            parseDateFormat = new SimpleDateFormat("MMM-d-yyyy");
        }else{
            convertDateFormat = new SimpleDateFormat("MMM-d-yyyy");
            parseDateFormat = new SimpleDateFormat("yyyy-MM-d");
        }
        Date startDate;
        try {
            startDate = parseDateFormat.parse(strDate);
            String newDateString = convertDateFormat.format(startDate);
            System.out.println(newDateString);
            return newDateString;
        } catch (ParseException e) {
            //If the date is already converted then parse exception is fired.
            return strDate;
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;
    }

    public static String convertMovieDateFormatForApiResults(String strDate, String dateFormat){
        DateFormat parseDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(dateFormat!=null && dateFormat.equalsIgnoreCase(Constants.YEAR_FIRST)){
            dateFormat = "yyyy-MM-d";
        }else{
            dateFormat = "MMM-d-yyyy";
        }
        DateFormat convertDateFormat = new SimpleDateFormat(dateFormat);
        Date startDate;
        try {
            startDate = parseDateFormat.parse(strDate);
            String newDateString = convertDateFormat.format(startDate);
            System.out.println(newDateString);
            return newDateString;
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

}
