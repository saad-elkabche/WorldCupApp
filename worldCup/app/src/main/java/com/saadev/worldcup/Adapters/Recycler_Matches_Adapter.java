package com.saadev.worldcup.Adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.saadev.worldcup.R;
import com.saadev.worldcup.databinding.ItemMatchBinding;
import com.saadev.worldcup.ValidMatch;

import java.util.ArrayList;
import java.util.List;

public class Recycler_Matches_Adapter extends RecyclerView.Adapter<Recycler_Matches_Adapter.ViewMatch> {

    List<ValidMatch> listMatches=new ArrayList<>();

    public void setListMatches(List<ValidMatch> listMatches) {
        this.listMatches = listMatches;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewMatch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMatchBinding matchBinding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_match,parent,false);
        return new ViewMatch(matchBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMatch holder, int position) {

        holder.bind(listMatches.get(position));
    }

    @Override
    public int getItemCount() {
        return listMatches.size();
    }

    public class ViewMatch extends RecyclerView.ViewHolder {
        ItemMatchBinding matchBinding;
        public ViewMatch(ItemMatchBinding matchBinding) {
            super(matchBinding.getRoot());
            this.matchBinding=matchBinding;
        }
        public void bind(ValidMatch match){
            matchBinding.setMatch(match);
        }
    }

}
