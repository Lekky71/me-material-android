package com.teammusa.mematerial.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.teammusa.mematerial.R;
import com.teammusa.mematerial.adapters.ViewPagerAdapter;
import com.teammusa.mematerial.data.PreferenceManager;
import com.teammusa.mematerial.fragments.ChatFragment;
import com.teammusa.mematerial.fragments.LibraryFragment;
import com.teammusa.mematerial.fragments.MaterialsFragment;
import com.teammusa.mematerial.fragments.ProfileFragment;
import com.teammusa.mematerial.media.MediaUtility;
import com.teammusa.mematerial.utils.NetworkUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements ProfileFragment.UploadButtonClicklistener {

    private static final String TAG = MainActivity.class.getSimpleName();
    ViewPager viewPager;
    BottomNavigationView navigation;

    ProfileFragment profileFragment;
    MaterialsFragment materialsFragment;
    LibraryFragment libraryFragment;
    ChatFragment chatFragment;

    Context context;
    PreferenceManager preferenceManager;

    private Uri USER_PROFILE_URI; //for picture update


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
                switch (item.getItemId()) {
                    case R.id.nav_profile:
                        viewPager.setCurrentItem(0, true);
                        return true;
                    case R.id.nav_all_materials:
                        viewPager.setCurrentItem(1, true);
                        return true;
                    case R.id.nav_my_library:
                        viewPager.setCurrentItem(2, true);
                        return true;
                        case R.id.nav_chat:
                            viewPager.setCurrentItem(3, true);
                        return true;
                }
                return false;
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);
        viewPager = findViewById(R.id.viewPager);

        context = getApplicationContext();
        preferenceManager = new PreferenceManager(context);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        setupViewPager(viewPager);


    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        profileFragment = new ProfileFragment();
        materialsFragment = new MaterialsFragment();
        libraryFragment = new LibraryFragment();
        chatFragment = new ChatFragment();

        adapter.addFragment(profileFragment);
        adapter.addFragment(materialsFragment);
        adapter.addFragment(libraryFragment);
        adapter.addFragment(chatFragment);
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onUploadClick() {
        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                USER_PROFILE_URI = resultUri;
                Log.d(TAG, "Uri: " + resultUri);

                File imageFile = new File(MediaUtility.getTransactionsDir(), "user_image.jpg");
                preferenceManager.setIconUrl(imageFile.getAbsolutePath());
                if(imageFile.exists()){
                    imageFile.delete();
                    imageFile = new File(MediaUtility.getTransactionsDir(), "user_image.jpg");
                }
                try {
                    MediaUtility.copyFile(new File(resultUri.getPath()), imageFile);
                    startActivity(new Intent(context, MainActivity.class));
                    finish();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }



}
