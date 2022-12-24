package com.saadev.worldcup.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.saadev.worldcup.R;
import com.saadev.worldcup.Team;
import com.saadev.worldcup.databinding.ItemTeamBinding;
import com.saadev.worldcup.other.ClickListeneronTeams;

import java.util.ArrayList;
import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.VH> {
    List<Team> listTeams=new ArrayList<>();
    private ClickListeneronTeams clickListeneronTeams;

    public TeamsAdapter(ClickListeneronTeams clickListeneronTeams){
        this.clickListeneronTeams=clickListeneronTeams;
    }

    public void setListTeams(List<Team> listTeams) {
        this.listTeams = listTeams;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTeamBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_team,parent,false);
        return new VH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bind(listTeams.get(position),this.clickListeneronTeams);
    }

    @Override
    public int getItemCount() {
        return listTeams.size();
    }

    public class VH extends RecyclerView.ViewHolder {
        private ItemTeamBinding bindingTeam;
        public VH(@NonNull ItemTeamBinding binding) {
            super(binding.getRoot());
            bindingTeam=binding;
        }
        public void bind(Team t,ClickListeneronTeams clickListene){
            bindingTeam.setTeam(t);
            bindingTeam.setClickListener(clickListene);
        }
    }
}
