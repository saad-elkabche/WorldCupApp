package com.saadev.worldcup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.saadev.worldcup.Player;
import com.saadev.worldcup.R;
import com.saadev.worldcup.databinding.ItemPlayerBinding;
import com.saadev.worldcup.databinding.ItemPlayerSmallBinding;

import java.util.ArrayList;
import java.util.List;

public class PlayersGridAdapter extends BaseAdapter {
    List<Player> listPlayers=new ArrayList<>();
    Context con;

    public PlayersGridAdapter(Context context){
        this.con=context;
    }

    public void setListPlayers(List<Player> listPlayers) {
        this.listPlayers = listPlayers;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listPlayers.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ItemPlayerSmallBinding binding= DataBindingUtil.inflate(LayoutInflater.from(con),R.layout.item_player_small,viewGroup,false);
        binding.setPlayer(listPlayers.get(i));
        if(i==0||i==1){
            binding.getRoot().setVisibility(View.INVISIBLE);
        }
        return binding.getRoot();
    }
}
