package com.example.myapplication.Activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.Constants;
import com.example.myapplication.R;
import com.example.myapplication.Utlis.Utils;
import com.example.myapplication.service.AddMealService;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddMeal extends AppCompatActivity {


    private static final int PICK_IMAGE = 55;
    private static final String TAG = "AddMeal";
    private Uri uriImage;
    private String path;

    private String mResturantName, mMealName, details, address, image;
    @BindView(R.id.rest_name)
    TextInputLayout resturantNameEd;
    @BindView(R.id.meal_name)
    TextInputLayout mealNameEd;
    @BindView(R.id.address)
    TextInputLayout addressEd;
    @BindView(R.id.detail)
    TextInputLayout detailsEd;
    @BindView(R.id.img)
    ImageView imageView;
    @BindView(R.id.parent)
    LinearLayout linearLayout;

    @OnClick(R.id.add_image)
    void addImage() {
        openFileChoicer();
    }

    @OnClick(R.id.post)
    void post() {

        info();

        if (!validateAdress() | !validateDetails() | !validateName() | !validateRest())
            return;
        else if (path.isEmpty())
            Snackbar.make(resturantNameEd, "Please Select Image", Snackbar.LENGTH_LONG).show();
        else {

            Intent intent = new Intent(this, AddMealService.class);
            intent.putExtra(Constants.RESTNAME, mResturantName);
            intent.putExtra(Constants.MEALNAME, mMealName);
            intent.putExtra(Constants.DETAILS, details);
            intent.putExtra(Constants.ADDRESS, address);
            intent.putExtra(Constants.IMG, uriImage.toString());
            intent.putExtra(Constants.PATH, path);


            ContextCompat.startForegroundService(AddMeal.this, intent);
            finish();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_meal);
        ButterKnife.bind(this);
        Utils.setupUI(linearLayout, AddMeal.this);

    }

    private void info() {
        mMealName = mealNameEd.getEditText().getText().toString().trim();
        mResturantName = resturantNameEd.getEditText().getText().toString().trim();
        address = addressEd.getEditText().getText().toString().trim();
        details = detailsEd.getEditText().getText().toString().trim();
        path = System.currentTimeMillis() + "." + getFileExtenstion(uriImage);

    }

    private Boolean validateName() {

        if (mMealName.isEmpty()) {
            mealNameEd.setError(getString(R.string.required));
            return false;
        } else
            mealNameEd.setError(null);
        return true;

    }

    private Boolean validateRest() {

        if (mResturantName.isEmpty()) {
            resturantNameEd.setError(getString(R.string.required));
            return false;
        } else
            resturantNameEd.setError(null);
        return true;

    }

    private Boolean validateAdress() {

        if (address.isEmpty()) {
            addressEd.setError(getString(R.string.required));
            return false;
        } else
            addressEd.setError(null);
        return true;

    }

    private Boolean validateDetails() {

        if (details.isEmpty()) {
            detailsEd.setError(getString(R.string.required));
            return false;
        } else
            detailsEd.setError(null);
        return true;

    }

    //open window to select Image
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            uriImage = data.getData();
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageURI(uriImage);
            Toast.makeText(this, uriImage.toString(), Toast.LENGTH_SHORT).show();
        }

    }

}
