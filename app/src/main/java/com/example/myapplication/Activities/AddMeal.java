package com.example.myapplication.Activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.myapplication.R;
import com.example.myapplication.model.MealModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMeal extends AppCompatActivity {


    private static final int PICK_IMAGE = 55;
    private static final String TAG = "AddMeal";
    private Uri uriImage;
    private String path;
    private DatabaseReference mDatabaseReference;
    private StorageReference mStorageReference;
    private String mResturantName, mMealName, details, address, image;
    @BindView(R.id.rest_name)
    EditText resturantNameEd;
    @BindView(R.id.meal_name)
    EditText mealNameEd;
    @BindView(R.id.address)
    EditText addressEd;
    @BindView(R.id.detail)
    EditText detailsEd;
    @BindView(R.id.img)
    ImageView imageView;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    @OnClick(R.id.add_image)
    void addImage() {
        openFileChoicer();
    }

    @OnClick(R.id.post)
    void post() {
        upload();

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meal);
        ButterKnife.bind(this);
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("meals");
    }

    private void openFileChoicer() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE);
    }

    //get image extention
    private String getFileExtenstion(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }


    //upload image and other information to firebase
    private void upload() {

        if (uriImage != null && !resturantNameEd.getText().toString().trim().equals("") &&
                !mealNameEd.getText().toString().trim().equals("") &&
                !addressEd.getText().toString().trim().equals("") &&
                !detailsEd.getText().toString().trim().equals("")
        ) {
            path = System.currentTimeMillis() + "." + getFileExtenstion(uriImage);
            StorageReference file = mStorageReference.child("uploads/" + path);
            file.putFile(uriImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(0);
                        }
                    }, 500);
                    mMealName = mealNameEd.getText().toString().trim();
                    mResturantName = resturantNameEd.getText().toString().trim();
                    address = addressEd.getText().toString().trim();
                    details = detailsEd.getText().toString().trim();

                    mStorageReference.child("uploads/" + path).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            image = uri.toString();
                            MealModel mealModel = new MealModel(mMealName, image, mResturantName, details, address);
                            String uploadId = mDatabaseReference.push().getKey();
                            mDatabaseReference.child(uploadId).setValue(mealModel);
                            finish();
                            Log.i(TAG, image);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            Log.i(TAG, exception.getMessage());
                        }
                    });

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Snackbar.make(progressBar, getString(R.string.fail), Snackbar.LENGTH_LONG).show();
                }
            })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int) progress);
                        }
                    });
        } else Snackbar.make(progressBar, getString(R.string.blanks), Snackbar.LENGTH_LONG).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            uriImage = data.getData();
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageURI(uriImage);
        }

    }

}
