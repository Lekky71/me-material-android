package com.teammusa.mematerial.activities.auth;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.teammusa.mematerial.R;
import com.teammusa.mematerial.activities.MainActivity;
import com.teammusa.mematerial.data.PreferenceManager;
import com.teammusa.mematerial.models.Metadata;


public class WelcomeActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST = 234;
    private static final String TAG = WelcomeActivity.class.getSimpleName();
    Button loginButton, signUpButton;
    PreferenceManager preferenceManager;
    Metadata metadata;
    Context context;
    RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        context = getApplicationContext();
        preferenceManager = new PreferenceManager(getApplicationContext());
        metadata = preferenceManager.getPrefUserMetadata();
        relativeLayout = findViewById(R.id.mContentView);
        hide();
        askForPermissions();

        if(preferenceManager.isUserLoggedIn()) {
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        }

        loginButton = findViewById(R.id.login_button);
        signUpButton = findViewById(R.id.sign_up_button);


        loginButton.setOnClickListener((view -> {
            startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
        }));
        signUpButton.setOnClickListener((view -> {
            startActivity(new Intent(WelcomeActivity.this, SignUpActivity.class));
        }));
    }


    public void askForPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean permissionCheck0 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;

            if (!(permissionCheck0)) {
                ActivityCompat.requestPermissions(WelcomeActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST);
                Log.i(TAG, "No permissions");

            } else {
                Log.i(TAG, "Has permissions");
            }
        } else {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST:
                boolean allAccepted = true;
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        allAccepted = false;
                        askForPermissions();
                        break;
                    }
                }
                if(allAccepted){
                }
            default:
                break;
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            relativeLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    }
}
