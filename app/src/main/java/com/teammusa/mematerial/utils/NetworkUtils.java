package com.teammusa.mematerial.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class NetworkUtils {
    public static boolean isOnline(Context context){
        ConnectivityManager connectivityManager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED;
    }
    public static void showNoInternetToast(Context context){
        if(!NetworkUtils.isOnline(context)){
            Toast.makeText(context, "You are not connected to the internet", Toast.LENGTH_LONG).show();
        }
    }

    private static ProgressDialog progressDialog;

    public static void showProgressDialog(Context context, String title, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setProgress(40);
        progressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }
    public static void stopProgressDialog(){
        if(progressDialog!= null) progressDialog.dismiss();
    }

    public static void loadImage(Context context, String uri, ImageView view){
        GlideApp.with(context).load(uri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true).into(view);
    }

}
