package com.saadev.worldcup.MVVMs;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.saadev.worldcup.Team;
import com.saadev.worldcup.Player;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TeamPlayersViewModel extends ViewModel {
    public static boolean taskInProgresse=false,isTaskInProgressePlayers=false;
    DatabaseReference referenceTeam,referencePlayers;

    private  List<Team> listTeams=new ArrayList<>();

     private static List<Player> listAllPlayers=new ArrayList<>();
     private List<Player> lisTeamPlayers=new ArrayList<>();

    MutableLiveData<List<Team>> liveDataTeams=new MutableLiveData<>();
    MutableLiveData<List<Player>> liveDataPlayer=new MutableLiveData<>();

    public MutableLiveData<List<Player>> getLiveDataPlayer() {
        return liveDataPlayer;
    }

    public MutableLiveData<List<Team>> getLiveDataTeams() {
        return liveDataTeams;
    }

    public void getTeams(){
        if(!taskInProgresse){
            if(listTeams.size()==0){
                taskInProgresse=true;
                referenceTeam= FirebaseDatabase.getInstance().getReference().child("teams");
                referenceTeam.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listTeams.clear();
                        for(DataSnapshot snap: snapshot.getChildren()){
                            Team tm=snap.getValue(Team.class);
                            listTeams.add(tm);
                        }
                        liveDataTeams.setValue(listTeams);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
        taskInProgresse=false;
    }
    public void getAllPlayers(){
        if(!isTaskInProgressePlayers) {
            isTaskInProgressePlayers=true;
            if (listAllPlayers.size() == 0) {
                referencePlayers = FirebaseDatabase.getInstance().getReference().child("Players");
                referencePlayers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Player pla;
                        listAllPlayers.clear();
                        for (DataSnapshot snap : snapshot.getChildren()) {
                            pla = snap.getValue(Player.class);
                            listAllPlayers.add(pla);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }
        isTaskInProgressePlayers=false;
    }
    public void getTeamsPlayers(String id){
        if(listAllPlayers.size()>0){
            lisTeamPlayers.clear();
            lisTeamPlayers.add(new Player());
            lisTeamPlayers.add(new Player());
            for(Player pla:listAllPlayers){

                String idteam= pla.getIdTeam();
                if(idteam.equals(id))
                {
                    lisTeamPlayers.add(pla);
                }
            }
            liveDataPlayer.setValue(lisTeamPlayers);
        }
    }
    public void refresh(){
        liveDataTeams.setValue(listTeams);
    }

    public Team getTeam(String id){
        Team teamm=null;
        if(listTeams!=null){
            for(Team tm:listTeams){
                if(tm.getId().equals(id)){
                    teamm=tm;
                }
            }
        }
        return teamm;
    }




}
