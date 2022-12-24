package com.saadev.worldcup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;

import com.saadev.worldcup.Adapters.fragmentAdapter;
import com.saadev.worldcup.adsStuff.ServiceAds;
import com.saadev.worldcup.adsStuff.loadInterstitialAds;
import com.saadev.worldcup.other.ServiceNetwork;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar1)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.Pager)
    ViewPager view;
    @BindView(R.id.offlineWindow)
    RelativeLayout offlineWindow;
    @BindView(R.id.Banner)
    RelativeLayout BannerBox;


    fragmentAdapter adapter;
    AdView adview;
    public static final String ActionNetwork="ActionNetwork";
    public final  static String teamId="teamId";
    private boolean online=true,isalreadyAdded=false;
    private float originalY;




    IntentFilter intentFilterBroadcast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

       //adview = findViewById(R.id.adView);

        originalY=offlineWindow.getY();







        //========================================================================services
        Intent intenService=new Intent(this, ServiceNetwork.class);
        startService(intenService);

        intentFilterBroadcast=new IntentFilter(MainActivity.ActionNetwork);
        //====================================================================




        //////////////////////////
        ServiceAds.getAdsFromFireBase();
        initializeAds();
        //////////////////////////////////////////////////////

        adapter = new fragmentAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        tabLayout.setupWithViewPager(view);
        view.setAdapter(adapter);

        view.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                loadInterstitialAds.setNbClicks(MainActivity.this);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    //====================================================

    private void initializeAds() {
        //Ads stuff
       if(ServiceAds.admobAds!=null && adview==null){
          adview =new AdView(this);
           adview.setAdSize(AdSize.BANNER);
           adview.setAdUnitId(ServiceAds.admobAds.getBannerMainActivity().trim());
           MobileAds.initialize(this);
           AdRequest adRequest=new AdRequest.Builder().build();
           adview.loadAd(adRequest);
           adview.setAdListener(new AdListener() {
               @Override
               public void onAdLoaded() {
                   super.onAdLoaded();
                   RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                   params.addRule(RelativeLayout.CENTER_IN_PARENT);
                   BannerBox.addView(adview,params);
               }

               @Override
               public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                   super.onAdFailedToLoad(loadAdError);
                   adview=null;
               }
           });
       }

    }




    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MainActivity.ActionNetwork)){
                if(intent.getBooleanExtra("status",false)){
                    //=================================online
                    if(!online){
                        //hide window
                        hideWindowOffLine();
                        online=true;
                    }
                    ServiceAds.getAdsFromFireBase();
                    initializeAds();
                }
                else{
                    //=======================offline
                    if(online){
                        //show window
                        showWindowOffLine();
                        online=false;
                    }
                }
            }
        }
    };

    private void showWindowOffLine(){
        TranslateAnimation animation=new TranslateAnimation(offlineWindow.getX(),offlineWindow.getX(),this.originalY,tabLayout.getY()+tabLayout.getHeight()-offlineWindow.getY());
        animation.setDuration(1500);
        animation.setRepeatCount(0);
        animation.setFillAfter(true);
        offlineWindow.startAnimation(animation);
    }
    private void hideWindowOffLine(){
        TranslateAnimation animation=new TranslateAnimation(offlineWindow.getX(),offlineWindow.getX(),tabLayout.getY()+tabLayout.getHeight()-offlineWindow.getY(),this.originalY);
        animation.setDuration(1500);
        animation.setRepeatMode(0);
        offlineWindow.startAnimation(animation);
        offlineWindow.setVisibility(View.INVISIBLE);
    }










    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver,intentFilterBroadcast);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(broadcastReceiver);



    }

}