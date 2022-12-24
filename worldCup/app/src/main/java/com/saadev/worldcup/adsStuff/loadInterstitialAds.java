package com.saadev.worldcup.adsStuff;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.saadev.worldcup.R;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class loadInterstitialAds {
    private static int nbClicks=0;
    private static InterstitialAd interstitialAd;


    public static void setNbClicks(Activity act) {
        loadInterstitialAds.nbClicks++;
        if(nbClicks%3==0){
            nbClicks=0;
            loadAd(act);
        }
    }
    private static void loadAd( Activity act){
        if(ServiceAds.admobAds!=null) {

            AdRequest adRequest = new AdRequest.Builder()
                    .build();

            InterstitialAd.load(act.getApplicationContext(), ServiceAds.admobAds.getInterstesial().trim(), adRequest, new InterstitialAdLoadCallback() {
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    super.onAdFailedToLoad(loadAdError);
                    interstitialAd = null;
                }

                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    super.onAdLoaded(interstitialAd);

                    loadInterstitialAds.interstitialAd = interstitialAd;

                    if (loadInterstitialAds.interstitialAd != null) {

                        interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                super.onAdDismissedFullScreenContent();
                                loadInterstitialAds.interstitialAd = null;
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                                super.onAdFailedToShowFullScreenContent(adError);
                                loadInterstitialAds.interstitialAd = null;
                            }
                        });
                        interstitialAd.show(act);
                    }
                }
            });
        }

    }
}
