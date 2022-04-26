package com.ominfo.crm_solution.notification;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.ominfo.crm_solution.MainActivity;
import com.ominfo.crm_solution.R;
import com.ominfo.crm_solution.interfaces.SharedPrefKey;
import com.ominfo.crm_solution.ui.SplashActivity;
import com.ominfo.crm_solution.ui.notifications.NotificationsActivity;
import com.ominfo.crm_solution.util.SharedPref;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyFirebaseServices extends FirebaseMessagingService {

    private static final String TAG = "PushNotification";
    private static final String CHANNEL_ID ="109" ;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabaseReference, mRef;
    int mAppCommunityMinutes;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
    }

    //save App community minutes to firebase db
    private void setAppCommunityMinutes(){
        Date mDate = new Date();
        String mCurrentDate = new SimpleDateFormat("yyyy-MM").format(mDate);
        // mCurrentDate = "2021-03-19";
        mRef = mDatabase.getReference("community-minutes");
        mDatabaseReference = mDatabase.getReference("community-minutes").child(mCurrentDate);
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    //if date entry exits then create new entry
                    long value = snapshot.getValue(Long.class);
                    mAppCommunityMinutes = (int) value;
                    if(mAppCommunityMinutes==0){
                        //if no session added then init with 1 min for completed session
                        mDatabaseReference.setValue(1);
                    }
                    else {
                        //add 1 minutes for every complete session
                        mDatabaseReference.setValue(mAppCommunityMinutes +1);
                    }
                }
                catch (NullPointerException e){
                    //if date entry does not exits then create new entry
                    e.printStackTrace();
                    mDatabaseReference = mRef.child(mCurrentDate);
                    mDatabaseReference.setValue(1);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                 Toast.makeText(getApplicationContext(), "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNotification(String title,String message){
        Intent intent = new Intent(this, NotificationsActivity.class);
        SharedPref.getInstance(this).write(SharedPrefKey.IS_NOTIFY, true);
        //String iSNotify = SharedPref.getInstance(getApplicationContext()).read(SharedPrefKey.IS_NOTIFY_COUNT, "0");
        //setAppCommunityMinutes();
        //SharedPref.getInstance(this).write(SharedPrefKey.IS_NOTIFY_COUNT, String.valueOf(Integer.parseInt(iSNotify)+1));
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent;
        //= PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE/* or PendingIntent.FLAG_UPDATE_CURRENT*/);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        }else {
            pendingIntent = PendingIntent.getActivity(this,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_turanthbiz)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }
}
