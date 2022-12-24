package com.saadev.worldcup;

import android.app.ActivityOptions;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.saadev.worldcup.Adapters.TeamsAdapter;
import com.saadev.worldcup.MVVMs.TeamPlayersViewModel;
//import com.example.worldcup.databinding.ItemTeamBinding;
import com.saadev.worldcup.adsStuff.loadInterstitialAds;
import com.saadev.worldcup.other.ClickListeneronTeams;

import java.util.List;


public class teams_frag extends Fragment implements ClickListeneronTeams {
RecyclerView recyclerView;
 TeamsAdapter adapter;
IntentFilter intentFilter;
 TeamPlayersViewModel team_player_viewModel;
 public static final String teamId="teamId",teamLogo="teamLogo",teamName="teamName";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_teams_frag, container, false);

        intentFilter=new IntentFilter(MainActivity.ActionNetwork);


        recyclerView=v.findViewById(R.id.RecyclerTeams);
        adapter=new TeamsAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        team_player_viewModel= ViewModelProviders.of(this).get(TeamPlayersViewModel.class);
        team_player_viewModel.getLiveDataTeams().observe(getViewLifecycleOwner(), new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                if(adapter!=null && teams!=null)
                    adapter.setListTeams(teams);
            }
        });
        team_player_viewModel.getTeams();
        team_player_viewModel.getAllPlayers();
        return v;
    }

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(MainActivity.ActionNetwork)){
                if(intent.getBooleanExtra("status",false)){
                    if(team_player_viewModel!=null){
                        team_player_viewModel.getTeams();
                        team_player_viewModel.getAllPlayers();
                    }
                }
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    public void onclicks(View cardView) {


        ImageView imgteam=cardView.findViewById(R.id.imgTeam);
        TextView txtName=cardView.findViewById(R.id.NameTeam);

        Intent inten=new Intent(getContext(),players_Activity.class);


        Team team=team_player_viewModel.getTeam(cardView.getTag().toString());
        if(team!=null){
            inten.putExtra(teamId,team.getId());
            inten.putExtra(teamName,team.getName());
            inten.putExtra(teamLogo,team.getLogo());
        }

        Pair[] pairs=new Pair[3];
        pairs[0]=new Pair<View,String>(cardView,"cardView");
        pairs[1]=new Pair<View,String>(imgteam,"imgTeam");
        pairs[2]=new Pair<View,String>(txtName,"nameTeam");

        ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(getActivity(),pairs);
        loadInterstitialAds.setNbClicks(getActivity());
        startActivity(inten,options.toBundle());
    }
}