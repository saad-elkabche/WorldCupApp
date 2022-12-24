package com.saadev.worldcup.adsStuff;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ServiceAds {
    public static AdmobAds admobAds;

    public static void getAdsFromFireBase(){
        if(admobAds==null){
            DatabaseReference reference= FirebaseDatabase.getInstance().getReference("AdmobAds");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    admobAds=snapshot.getValue(AdmobAds.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
