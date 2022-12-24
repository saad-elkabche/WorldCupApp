package com.saadev.worldcup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.saadev.worldcup.Adapters.PlayersGridAdapter;
import com.saadev.worldcup.MVVMs.TeamPlayersViewModel;
import com.saadev.worldcup.adsStuff.ServiceAds;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class players_Activity extends AppCompatActivity {

    @BindView(R.id.imgTeam)
    ImageView imgTeam;
    @BindView(R.id.NameTeam)
    TextView txtName;
    @BindView(R.id.gridPlayers)
    GridView gridView;
   @BindView(R.id.BannerBox)
   RelativeLayout bannerBox;

   AdView adView;

    String idTeam;


    TeamPlayersViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        ButterKnife.bind(this);

       idTeam=getIntent().getStringExtra(teams_frag.teamId);
       txtName.setText(getIntent().getStringExtra(teams_frag.teamName));
        Glide.with(this)
                .load(getIntent().getStringExtra(teams_frag.teamLogo))
                .fitCenter()
                .into(imgTeam);

        PlayersGridAdapter playersGridAdapter=new PlayersGridAdapter(this);
        gridView.setAdapter(playersGridAdapter);


        viewModel= ViewModelProviders.of(this).get(TeamPlayersViewModel.class);
        viewModel.getLiveDataPlayer().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> players) {
                if(players!=null){
                    playersGridAdapter.setListPlayers(players);
                }
            }
        });
        viewModel.getTeamsPlayers(idTeam);

        InitializeAds();
    }

    private void InitializeAds() {
      if(ServiceAds.admobAds!=null && adView==null){
          adView=new AdView(this);
          adView.setAdSize(AdSize.BANNER);
          adView.setAdUnitId(ServiceAds.admobAds.getBannerPlayerActivity().trim());
          MobileAds.initialize(this);
          AdRequest adRequest=new AdRequest.Builder().build();
          adView.loadAd(adRequest);
          adView.setAdListener(new AdListener() {
              @Override
              public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                  super.onAdFailedToLoad(loadAdError);
                  adView=null;
              }

              @Override
              public void onAdLoaded() {
                  super.onAdLoaded();
                  RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                  params.addRule(RelativeLayout.CENTER_IN_PARENT);
                  bannerBox.addView(adView,params);
              }
          });
      }
    }
}