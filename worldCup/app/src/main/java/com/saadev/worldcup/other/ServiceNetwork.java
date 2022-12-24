package com.saadev.worldcup.other;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.saadev.worldcup.MainActivity;

public class ServiceNetwork extends Service {
    Handler hand=new Handler();


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
      //  Log.d("hello","im inside service");
        hand.post(listennerToNetwork);
        return START_STICKY;
    }



    private Runnable listennerToNetwork=new Runnable() {
        @Override
        public void run() {
            hand.postDelayed(this,5000);
                Intent broadCast=new Intent();
                broadCast.setAction(MainActivity.ActionNetwork);
                broadCast.putExtra("status",NetworkState.isConnected(getApplicationContext()));
                sendBroadcast(broadCast);
                //Log.d("hello","im inside runnable");
        }
    };
}
