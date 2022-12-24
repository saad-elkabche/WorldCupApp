package com.saadev.worldcup.other;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkState {

    public static boolean isConnected(Context con){
        boolean status =false;
        ConnectivityManager connectivityManager=(ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null){
            if(networkInfo.isConnected()){
                status=true;
            }
        }
        return status;
    }

}
