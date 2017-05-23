package com.example.ananya.giveblood.util;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.example.ananya.giveblood.R;
import com.example.ananya.giveblood.service.handler.AlertDialogHandler;

import java.util.regex.Pattern;

public final class Utility {

    // Kinvey Dev
    public static final String APP_KEY = "kid_HJo2_Sivg";
    public static final String USERNAME_VALUE = "archana.kokala@gmail.com";
    public static final String PASSWORD_VALUE = "Koku@1990";

    // Headers
    public static final String AUTHORIZATION = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";

    // common
    public static final String OBJECT_ID = "_id";

    // Table name
    public static final String USER_TABLE = "GBUser";
    public static final String BLOOD_GROUP = "bloodGroup";
    public static final String USER_NAME = "userName";
    public static final String PHONE_NUMBER = "phoneNumber";
    public static final String LOCATION = "location";
    public static final String EMAIL = "email";
    public static final String IS_DONAR = "isDonar";

    // Related to Methods
    private static ProgressDialog mProgressDialog;
    private static AlertDialog alertDialog;
    private static AlertDialog.Builder myAlertDialog;

    // HIDE PROGRESS BAR
    public static void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    // SHOW ALERT DIALOGUE
    public static void showAlertDialog(Context context, String title, String message, boolean cancelable, String positiveButton, String negativeButton, AlertDialogHandler alertDialogHandler) {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }

        myAlertDialog = new AlertDialog.Builder(context);

        if (title != null) {
            myAlertDialog.setTitle(title);
        }

        if (message != null) {
            SpannableString spannableString = new SpannableString(message);
            Pattern mobilePatern = Pattern.compile("((\\(\\d{3}\\) ?)|(\\d{3}-))?\\d{3}-\\d{4}");
            Linkify.addLinks(spannableString, mobilePatern, "");
            Linkify.addLinks(spannableString, Linkify.WEB_URLS | Linkify.EMAIL_ADDRESSES);
            myAlertDialog.setMessage(spannableString);
        }

        setPositiveButton(positiveButton, alertDialogHandler);
        setNegativeButton(negativeButton, alertDialogHandler, cancelable);
        alertDialog = myAlertDialog.create();
        alertDialog.show();
        ((TextView) (alertDialog.findViewById(android.R.id.message))).setMovementMethod(LinkMovementMethod.getInstance());
    }

    private static void setNegativeButton(String negativeButton, final AlertDialogHandler alertDialogHandler, boolean cancelable) {
        if (negativeButton != null) {
            myAlertDialog.setNegativeButton(negativeButton, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    if (alertDialogHandler != null) {
                        alertDialogHandler.onNegativeButtonClicked();
                    }
                }
            });
            myAlertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    alertDialogHandler.onNegativeButtonClicked();
                }
            });
        } else {
            myAlertDialog.setCancelable(cancelable);
        }
    }

    private static void setPositiveButton(String positiveButton, final AlertDialogHandler alertDialogHandler) {
        if (positiveButton != null) {
            myAlertDialog.setPositiveButton(positiveButton, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    if (alertDialogHandler != null) {
                        alertDialogHandler.onPositiveButtonClicked();
                    }
                }
            });
        }
    }


    // NETWORK ERROR MESSAGE
    public static void showNetworkError(final Context context) {
        showAlertDialog(context, null, context.getString(R.string.network_unavailable), false, context.getString(R.string.ok_msg), null, new AlertDialogHandler() {

            @Override
            public void onPositiveButtonClicked() {
                hideProgressDialog();
            }

            @Override
            public void onNegativeButtonClicked() {

            }

            @Override
            public void onMultiChoiceClicked(int position, boolean isChecked) {

            }
        });
        hideProgressDialog();
    }

    //REQUEST TIME OUT
    public static void showRequestTimeOutError(final Context context) {
        showAlertDialog(context, null, context.getString(R.string.no_network), false, context.getString(R.string.ok_msg), null, new AlertDialogHandler() {

            @Override
            public void onPositiveButtonClicked() {
                hideProgressDialog();
            }

            @Override
            public void onNegativeButtonClicked() {

            }

            @Override
            public void onMultiChoiceClicked(int position, boolean isChecked) {

            }
        });
        hideProgressDialog();
    }

    //UNEXPECTED ERROR
    public static void showUnexpectedError(final Context context) {
        showAlertDialog(context,null, context.getString(R.string.unexpected_error), false, context.getString(R.string.ok_msg), null, new AlertDialogHandler() {

            @Override
            public void onPositiveButtonClicked() {

            }

            @Override
            public void onNegativeButtonClicked() {

            }

            @Override
            public void onMultiChoiceClicked(int position, boolean isChecked) {

            }
        });
        hideProgressDialog();
    }


    // SHOW PROGRESS BAR
    public void showProgressDialog(Context context) {
        hideProgressDialog();

        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setMessage(context.getString(R.string.progress_bar_message));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }
}
