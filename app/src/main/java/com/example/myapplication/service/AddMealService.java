package com.example.myapplication.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;

import com.example.myapplication.Activities.MainActivity;
import com.example.myapplication.Constants;
import com.example.myapplication.R;
import com.example.myapplication.StartApplication;
import com.example.myapplication.model.MealModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

public class AddMealService extends Service {

    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;

    @Override
    public void onCreate() {
        super.onCreate();
      mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("meals");


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


     String restName = intent.getStringExtra(Constants.RESTNAME);
                String mealMeal = intent.getStringExtra(Constants.MEALNAME);
                String img = intent.getStringExtra(Constants.IMG);
                String details = intent.getStringExtra(Constants.DETAILS);
                String address = intent.getStringExtra(Constants.ADDRESS);
                String path = intent.getStringExtra(Constants.PATH);

                StorageReference file = mStorageReference.child("uploads/" + path);

                file.putFile(Uri.parse(img)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        mStorageReference.child("uploads/" + path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                MealModel mealModel = new MealModel(mealMeal, uri.toString(), restName, details, address);
                                String uploadId = mDatabaseReference.push().getKey();
                                mDatabaseReference.child(uploadId).setValue(mealModel);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle any errors
                            }
                        });

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        notif((int) progress);
                        int p = (int) (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        if (p==100)
                            stopSelf();

                    }
                });



        return START_NOT_STICKY;
    }

    private void notif(int progress) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);


        Notification notification = new NotificationCompat.Builder(this, StartApplication.CHANNEL_ID)
                .setContentTitle("Uploading Post.....")
                .setSmallIcon(R.drawable.loading)
                .setContentIntent(pendingIntent)
                .setProgress(100, progress, false)
                .setOngoing(false)
                .build();

        startForeground(1996, notification);

    }

  @Override
    public void onDestroy() {
        super.onDestroy();
        stopJob();
        stopSelf();
    }

    void stopJob() {

        stopForeground(true);
        if (false) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                stopForeground(STOP_FOREGROUND_DETACH);
                stopForeground(STOP_FOREGROUND_REMOVE);

            }
        }
    }

}
