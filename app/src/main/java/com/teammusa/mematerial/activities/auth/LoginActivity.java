package com.teammusa.mematerial.activities.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.teammusa.mematerial.R;
import com.teammusa.mematerial.activities.MainActivity;
import com.teammusa.mematerial.api_network.ApiEndpointInterface;
import com.teammusa.mematerial.api_network.RetrofitBuilder;
import com.teammusa.mematerial.data.PreferenceManager;
import com.teammusa.mematerial.models.requests.LoginRequest;
import com.teammusa.mematerial.models.responses.AuthResponse;
import com.teammusa.mematerial.models.responses.ErrorResponse;
import com.teammusa.mematerial.utils.ClickSpan;
import com.teammusa.mematerial.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LoginActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    ProgressDialog mProgressDialog;
    private PreferenceManager preferenceManager;

    Snackbar messageSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = findViewById(R.id.edt_login_username);
        passwordEditText = findViewById(R.id.edt_login_password);
        preferenceManager = new PreferenceManager(this);
        mProgressDialog = new ProgressDialog(this);

        messageSnackbar = Snackbar.make(passwordEditText,"", Snackbar.LENGTH_LONG);
        NetworkUtils.showNoInternetToast(getApplicationContext());

    }

    public void showMessage(String message){
        messageSnackbar.setText(message).show();
    }

    public boolean isValidForm() {
        if (isEmpty(this.usernameEditText)) {
            Toast.makeText(this, "Enter your username", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isEmpty(this.passwordEditText)) {
            return true;
        } else {
            Toast.makeText(this, "Enter Your pin", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void onLoginButtonClick(View v) {
        if (isValidForm()) {
            login(getString(this.usernameEditText), getString(this.passwordEditText));
        }
    }


    public void login(String username, String password) {
        showProgressBar();
        LoginRequest loginDetails = new LoginRequest(username, password);
        Retrofit retrofit = RetrofitBuilder.getRetrofit();
        ApiEndpointInterface apiService = retrofit.create(ApiEndpointInterface.class);
        Call<Object> call = apiService.logInUser(loginDetails);
        call.enqueue(new Callback<Object>() {

            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                String resp = new Gson().toJson(response.body());
                Log.e(" response", "Log In response ::: \n\t"+ resp);
                mProgressDialog.dismiss();
                if (resp.contains("\"success\":\"true\"")) {
                    AuthResponse authResult = new Gson().fromJson(resp, AuthResponse.class);
                    preferenceManager.setPrefUserLoggedIn(true);
                    preferenceManager.setPrefUserMetadata(authResult.getMetadata());
                    mProgressDialog.cancel();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

                }
                else if(resp.contains("error")) {
                    ErrorResponse errorResponse = new Gson().fromJson(resp, ErrorResponse.class);

                    String errorMessage = errorResponse.getErrorMsg();
                    switch (errorMessage){
                        case "203":
                            showMessage("Invalid Login details");
                            break;
                        case "202":
                            showMessage("Info cannot be empty");
                            break;
                        case "205":
                            showMessage("An error occurred, try again");
                            break;
                        default:
                            showMessage("An error occurred, try again");
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
    }

    public boolean isEmpty(EditText edt) {
        return TextUtils.isEmpty(getString(edt));
    }

    public String getString(EditText edt) {
        return edt.getText().toString().trim();
    }

    public void hideProgressBar() {
        this.mProgressDialog.dismiss();
    }

    public void onSignUpButtonClick(View v) {
        startActivity(new Intent(this, SignUpActivity.class));
    }

    public void setGotoSpan(TextView view, Class whereTo) {
        String text = view.getText().toString();
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new ClickSpan(this, whereTo), 0, text.length(), 0);
        view.setMovementMethod(new LinkMovementMethod());
        view.setText(spannableString);
    }

    public void showProgressBar() {
        mProgressDialog.setTitle("Log In");
        mProgressDialog.setMessage("Logging in to MeMaterial");
        mProgressDialog.setProgress(0);
        mProgressDialog.setProgress(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        this.mProgressDialog.show();
    }

}
