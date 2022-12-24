package com.saadev.worldcup;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.Date;

public class ValidMatch implements Comparable<ValidMatch>{
    private String id;
    private String homeName;
    private String awayName;
    private String homeImage;
    private String awayImage;
    private String idHome;
    private String idAway;
    private String time;
    private String date;
    private String status;
    private String result;
    private Date localDate;


    public Date getLocalDate() {
        return localDate;
    }

    public void setLocalDate(Date localDate) {
        this.localDate = localDate;
    }

    public String getIdHome() {
        return idHome;
    }

    public void setIdHome(String idHome) {
        this.idHome = idHome;
    }

    public String getIdAway() {
        return idAway;
    }

    public void setIdAway(String idAway) {
        this.idAway = idAway;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public String getAwayName() {
        return awayName;
    }

    public void setAwayName(String awayName) {
        this.awayName = awayName;
    }

    public String getHomeImage() {
        return homeImage;
    }

    public void setHomeImage(String homeImage) {
        this.homeImage = homeImage;
    }

    public String getAwayImage() {
        return awayImage;
    }

    public void setAwayImage(String awayImage) {
        this.awayImage = awayImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @BindingAdapter("teamImage")
    public static void setImage(ImageView img,String url){
        Glide.with(img.getContext())
                .load( url)
                .apply(RequestOptions.circleCropTransform())
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .apply(RequestOptions.placeholderOf(R.drawable.placeholder_logo))
                .fitCenter()
                .into(img);
    }

    @Override
    public int compareTo(ValidMatch validMatch) {
        return getLocalDate().compareTo(validMatch.getLocalDate());
    }
}
