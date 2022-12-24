package com.saadev.worldcup.MVVMs;

import android.os.AsyncTask;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.saadev.worldcup.ValidMatch;
import com.saadev.worldcup.matches_frag;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class MatchViewModel extends ViewModel {

   private MutableLiveData<List<ValidMatch>> liveDataMatches=new MutableLiveData<>();
    List<ValidMatch> listVisible=new ArrayList<>();
   public static boolean taskInProgresse=false;




   public MutableLiveData<List<ValidMatch>> getLiveDataMatches() {
      return liveDataMatches;
   }

   public void retrieveData() {
         if(!taskInProgresse){
         if(listVisible.size()==0){
            taskInProgresse=true;
            new Retrieve_EditData().execute();
         }
      }

   }







   class Retrieve_EditData extends AsyncTask<Void,Void,Void>{
      Handler handler;
      DatabaseReference reference= FirebaseDatabase.getInstance().getReference().child("fixtures").child("FT_NS");
      private final String yesterday="Yesterday";
      private final String today="Today";
      private final String tomorrow="Tomorrow";

      @Override
      protected void onPreExecute() {
         super.onPreExecute();

         handler=new Handler();
      }

      @Override
      protected Void doInBackground(Void... voids) {
         DateFormat formatterDate=new SimpleDateFormat("E,MMM-dd");
         DateFormat formatterTime=new SimpleDateFormat("HH:mm");
         reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               listVisible.clear();
               Date localdate;
               for(DataSnapshot item :snapshot.getChildren()){

                  ValidMatch match=item.getValue(ValidMatch.class);
                  //==========================================================================date time stuff
                  localdate=getLocaleDateTime(match.getDate(), match.getTime());
                  match.setDate(formatterDate.format(localdate));
                  match.setTime(formatterTime.format(localdate));
                  match.setLocalDate(localdate);

                  String dateType=checkDate(localdate);

                 if(!dateType.equals("")){
                  match.setDate(dateType);
                 }
                 listVisible.add(match);
               }
               Collections.sort(listVisible);
               liveDataMatches.setValue(listVisible);
               //======================================Search for today data and scroll
               matches_frag.scrollToPosition(listVisible.size()-1);
               for (int i = 0; i < listVisible.size(); i++) {
                  if(listVisible.get(i).getDate().equals(today)){
                     matches_frag.scrollToPosition(i);
                     break;
                  }
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               taskInProgresse=false;
            }
         });

         taskInProgresse=false;
         return null;
      }

      @Override
      protected void onPostExecute(Void unused) {
         super.onPostExecute(unused);
         taskInProgresse=false;
         liveDataMatches.setValue(listVisible);
      }

      private String checkDate(Date localdate) {
         DateFormat formatter=new SimpleDateFormat("MM-dd");
         String result="";
         String todayDate,tomorrowdate,yesterdayDate;

         //yesterday
         Calendar calendarIns=Calendar.getInstance();
         calendarIns.add(Calendar.DAY_OF_MONTH,-1);
         yesterdayDate=formatter.format( calendarIns.getTime());

         //today
         todayDate=formatter.format(Calendar.getInstance().getTime());

         //tomorrow
         calendarIns=Calendar.getInstance();
         calendarIns.add(Calendar.DAY_OF_MONTH,1);
         tomorrowdate=formatter.format(calendarIns.getTime());

         String matchDate=formatter.format(localdate);

        if(matchDate.equals(yesterdayDate)){
           result=yesterday;
        }
        else if(matchDate.equals(todayDate)){
           result=today;
        }
        else if(matchDate.equals(tomorrowdate)){
           result=tomorrow;
        }

        return result;
      }


      //mm-dd  //   HH:MM
      private Date getLocaleDateTime(String date, String time){
         String[] dateArry,timeArry;
         Date result=null;
         dateArry=date.split("-");
         timeArry=time.split(":");
         DateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm");
         TimeZone localeTimezone=TimeZone.getDefault();

         if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ZonedDateTime UTCTime=ZonedDateTime.of(Integer.parseInt(dateArry[0]),Integer.parseInt(dateArry[1]),Integer.parseInt(dateArry[2]),Integer.parseInt(timeArry[0]),Integer.parseInt(timeArry[1]),0,0, ZoneId.of("UTC"));
            ZonedDateTime localtime=UTCTime.withZoneSameInstant(ZoneId.of(localeTimezone.getID()));
            result=Date.from(localtime.toInstant());
         }
         else{
            try {
               result=formatter.parse(dateArry[0]+"-"+dateArry[1]+"-"+dateArry[2]+" "+timeArry[0]+timeArry[1]);
            } catch (ParseException e) {
               e.printStackTrace();
            }
         }

         return result;
      }
   }


}
