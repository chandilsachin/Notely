package com.chandilsachin.notely.util;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;


import com.chandilsachin.notely.R;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ConstantMethod {

    public static void handleError(Throwable e, Context context) {
        if (e instanceof UnknownHostException || e instanceof SocketException ||
                e instanceof SocketTimeoutException) {
            Toast.makeText(context, context.getString(R.string.checkInternetConnection),
                    Toast.LENGTH_SHORT).show();
        } else {
            e.printStackTrace();
        }
    }

    public static boolean isInternetError(Throwable e) {
        return e instanceof UnknownHostException || e instanceof SocketException ||
                e instanceof SocketTimeoutException;
    }


    public static void loadFragment(AppCompatActivity activity, int containerId, Fragment fragment) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment).addToBackStack(null);
        transaction.commit();
    }

    public static void loadFragment(AppCompatActivity activity, int containerId, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(containerId, fragment);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }

    public static void loadFragment(AppCompatActivity activity, int containerId, Fragment fragment, View sharedView) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.addSharedElement(sharedView, ViewCompat.getTransitionName(sharedView));
        transaction.replace(containerId, fragment).addToBackStack(null);
        transaction.commit();
    }

    public static AlertDialog creteAlertDialog(Context context, String message) {
        AlertDialog dialog = new AlertDialog.Builder(context).create();
        dialog.setMessage(message);
        return dialog;
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static float convertDpToPixel(float dp) {
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager
                .getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
