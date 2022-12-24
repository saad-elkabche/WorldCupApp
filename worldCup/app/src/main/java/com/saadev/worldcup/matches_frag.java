package com.saadev.worldcup;

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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saadev.worldcup.Adapters.Recycler_Matches_Adapter;
import com.saadev.worldcup.MVVMs.MatchViewModel;

import java.util.List;


public class matches_frag extends Fragment {
        private static RecyclerView recyclerView;
       private  Recycler_Matches_Adapter adapter;
    private MatchViewModel viewModel;
    private  IntentFilter intenForBroadCast;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_matches_frag, container, false);

        intenForBroadCast=new IntentFilter(MainActivity.ActionNetwork);

       recyclerView=v.findViewById(R.id.RcMatches);
        adapter=new Recycler_Matches_Adapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);





        viewModel= ViewModelProviders.of(this).get(MatchViewModel.class);
        viewModel.getLiveDataMatches().observe(getViewLifecycleOwner(), new Observer<List<ValidMatch>>() {
            @Override
            public void onChanged(List<ValidMatch> validMatches) {
                if(adapter!=null)
                    adapter.setListMatches(validMatches);
            }
        });

        viewModel.retrieveData();
        return v;
    }

   BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
       @Override
       public void onReceive(Context context, Intent intent) {
           if(intent.getAction().equals(MainActivity.ActionNetwork)){
               if(intent.getBooleanExtra("status",false)){
                   //online
                   if(viewModel!=null){
                       viewModel.retrieveData();
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
        getActivity().registerReceiver(broadcastReceiver,intenForBroadCast);
    }

    public static void scrollToPosition(int index){
        recyclerView.scrollToPosition(index);
    }

}