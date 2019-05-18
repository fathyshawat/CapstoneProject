package com.example.myapplication;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.myapplication.model.MealModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class UpdateWidget extends IntentService {

    private DatabaseReference mRef;

    public UpdateWidget() {
        super("UpdateWidget");
        mRef = FirebaseDatabase.getInstance().getReference("meals");
    }

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */

    @Override
    protected void onHandleIntent(Intent intent) {
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                    MealModel mealModel =dataSnapshot.getValue(MealModel.class);
                    RemoteViews view = new RemoteViews(getPackageName(), R.layout.app_widget);
                    view.setTextViewText(R.id.meal_name1, mealModel.getMeal());
                    ComponentName theWidget = new ComponentName(getApplicationContext(), AppWidget.class);
                    AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
                    manager.updateAppWidget(theWidget, view);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


}