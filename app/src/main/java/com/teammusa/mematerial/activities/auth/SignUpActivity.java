package com.teammusa.mematerial.activities.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;
import com.teammusa.mematerial.R;
import com.teammusa.mematerial.activities.MainActivity;
import com.teammusa.mematerial.api_network.ApiEndpointInterface;
import com.teammusa.mematerial.api_network.RetrofitBuilder;
import com.teammusa.mematerial.data.PreferenceManager;
import com.teammusa.mematerial.media.MediaUtility;
import com.teammusa.mematerial.models.requests.SignUpRequest;
import com.teammusa.mematerial.models.responses.AuthResponse;
import com.teammusa.mematerial.models.responses.ErrorResponse;
import com.teammusa.mematerial.utils.NetworkUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    EditText cityEditText, usernameEditText, firstNameEditText, lastNameEditText, password2EditText,
            passwordEditText, phoneEditText, stateEditText;

    Button signUpButton;
    private ProgressDialog mProgressDialog;
    CircleImageView pictureImageView;
    private Uri USER_PROFILE_URI;
    FloatingActionButton uploadFloatingButton;
    private static final int REQUEST_IMAGE = 2;

    Context context;
    PreferenceManager preferenceManager;
    private Snackbar messageSnackbar;

    FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        findAllViews();
        context = getApplicationContext();
        FirebaseApp.initializeApp(context);
        preferenceManager = new PreferenceManager(context);
        mProgressDialog = new ProgressDialog(this);

        firebaseStorage = FirebaseStorage.getInstance();


        uploadFloatingButton.setOnClickListener((view) -> {
            CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(this);
        });
        messageSnackbar = Snackbar.make(passwordEditText, "", Snackbar.LENGTH_LONG);
        NetworkUtils.showNoInternetToast(context);

        signUpButton.setOnClickListener((view) -> {
            if (isValidForm()) {
                showProgressBar();
                String firstName = getString(firstNameEditText);
                String lastName = getString(lastNameEditText);
                String username = getString(usernameEditText);
                String city = getString(cityEditText);
                String password = getString(passwordEditText);
                String phoneNumber = getString(phoneEditText);
                String state = getString(stateEditText);

                String coursePath = "" +"user_image"+ UUID.randomUUID().toString();
                StorageReference profilePicRef = firebaseStorage.getReference("user_images").child(coursePath);

                profilePicRef.putFile(USER_PROFILE_URI)
                        .addOnSuccessListener(taskSnapshot -> {
                            profilePicRef.getDownloadUrl()
                                    .addOnCompleteListener(task -> {
                                        String coursePicUrl = task.getResult().toString();

                                        SignUpRequest details = new SignUpRequest(coursePicUrl, firstName, lastName, username, phoneNumber, city, state, password);
                                        Retrofit retrofit = RetrofitBuilder.getRetrofit();

                                        ApiEndpointInterface apiService = retrofit.create(ApiEndpointInterface.class);
                                        Call<Object> call = apiService.signUpUser(details);
                                        call.enqueue(new Callback<Object>() {
                                            @Override
                                            public void onResponse(Call<Object> call, Response<Object> response) {
                                                mProgressDialog.dismiss();
                                                String resp = new Gson().toJson(response.body());
                                                Log.e("Sign up response", "Sign up response ::: \n\t" + resp);
                                                if (resp.contains("\"success\":\"true\"")) {
                                                    AuthResponse authResult = new Gson().fromJson(resp, AuthResponse.class);
                                                    preferenceManager.setPrefUserLoggedIn(true);
                                                    preferenceManager.setPrefUserMetadata(authResult.getMetadata());

                                                    mProgressDialog.cancel();
                                                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                                                    finish();

                                                } else if (resp.contains("error")) {
                                                    ErrorResponse errorResponse = new Gson().fromJson(resp, ErrorResponse.class);
                                                    String errorMessage = errorResponse.getErrorMsg();
                                                    switch (errorMessage) {
                                                        case "101":
                                                            showMessage("This email is already taken");
                                                            break;
                                                        case "102":
                                                            showMessage("Username is already taken");
                                                            break;
                                                        case "103":
                                                            showMessage("Fill in your details");
                                                            break;
                                                        case "104":
                                                            showMessage("An error occurred. Please, try again.");
                                                            break;
                                                        default:
                                                            showMessage("An error occurred. Please, try again.");
                                                            break;
                                                    }
                                                }

                                            }

                                            @Override
                                            public void onFailure(Call<Object> call, Throwable t) {
                                                mProgressDialog.dismiss();
                                                showMessage("You account couldn't be created. Please, try again.");
                                                Log.e("Sign up response", "Sign up failed ::: \n\t" + t.getMessage() + "\n" + t.getLocalizedMessage());
                                                mProgressDialog.cancel();
                                            }
                                        });
                                    });
                        });
            }
        });

    }

    public void findAllViews() {
        cityEditText = findViewById(R.id.edt_user_city);
        stateEditText = findViewById(R.id.edt_user_state);
        usernameEditText = findViewById(R.id.edt_user_username);
        firstNameEditText = findViewById(R.id.edt_user_fname);
        lastNameEditText = findViewById(R.id.edt_user_lname);
        password2EditText = findViewById(R.id.edt_user_password_2);
        passwordEditText = findViewById(R.id.edt_user_password);
        phoneEditText = findViewById(R.id.edt_user_phone);
        signUpButton = findViewById(R.id.sign_up_button);

        uploadFloatingButton = findViewById(R.id.upload_image_button);
        pictureImageView = findViewById(R.id.user_profile_picture);
    }

    public boolean isValidForm() {
        if (USER_PROFILE_URI == null) {
            Toast.makeText(getApplicationContext(), "please upload your picture", Toast.LENGTH_SHORT).show();
            return false;
        } else if (isEmpty(this.firstNameEditText)) {
            Toast.makeText(getApplicationContext(), "first name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (isEmpty(this.lastNameEditText)) {
            Toast.makeText(getApplicationContext(), "Last name cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if ((getString(usernameEditText).length() < 5) || (getString(usernameEditText).length() > 11)) {
            Toast.makeText(getApplicationContext(), "Username must be a minimum of 5 and maximum of 11 characters", Toast.LENGTH_SHORT).show();
            return false;
        } else if (isEmpty(this.phoneEditText) || (getString(phoneEditText).length() != 11)) {
            Toast.makeText(getApplicationContext(), "phone Number must be 11 digits", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (isEmpty(this.cityEditText)) {
            Toast.makeText(getApplicationContext(), "City cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if (isEmpty(this.stateEditText)) {
            Toast.makeText(getApplicationContext(), "State cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        } else if ((getString(this.passwordEditText).length() < 4)) {
            Toast.makeText(getApplicationContext(), "Pin must be more 4 digits", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!(getString(this.passwordEditText).equals(getString(this.password2EditText)))) {
            Toast.makeText(getApplicationContext(), "Pin does not match", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    public boolean isEmpty(EditText edt) {
        return TextUtils.isEmpty(getString(edt));
    }

    public String getString(EditText edt) {
        return edt.getText().toString();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
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
                    NetworkUtils.loadImage(context, resultUri.getPath(), pictureImageView);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void showProgressBar() {
        mProgressDialog.setTitle("Sign Up");
        mProgressDialog.setMessage(getString(this.usernameEditText) + ", I'm creating your MeMaterial Account");
        mProgressDialog.setProgress(0);
        mProgressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        this.mProgressDialog.show();
    }

    public void showMessage(String message) {
        messageSnackbar.setText(message).show();
    }


}
