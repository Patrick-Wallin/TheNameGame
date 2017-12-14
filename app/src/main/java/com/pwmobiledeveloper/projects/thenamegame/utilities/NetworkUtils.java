package com.pwmobiledeveloper.projects.thenamegame.utilities;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;

import com.pwmobiledeveloper.projects.thenamegame.R;

/**
 * Created by piwal on 10/28/2017.
 */

public class NetworkUtils  {
    private Context mContext;

    public NetworkUtils(Context context) {
        mContext = context;
    }

    public boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public void showAlertMessageAboutNoInternetConnection(final boolean goBackToPreviousActivity) {
        new AlertDialog.Builder(mContext)
                .setTitle(mContext.getString(R.string.no_internet_connection_title))
                .setMessage(mContext.getString(R.string.no_internet_connection_message))
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

    public void showAlertMessageBasedOnErrorMessage(String errorMessage) {
        new AlertDialog.Builder(mContext)
                .setTitle(mContext.getString(R.string.error_message_title))
                .setMessage(errorMessage)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert).show();
    }

}
