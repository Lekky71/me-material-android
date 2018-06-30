package com.teammusa.mematerial.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.teammusa.mematerial.R;
import com.teammusa.mematerial.data.PreferenceManager;
import com.teammusa.mematerial.models.Metadata;
import com.teammusa.mematerial.utils.NetworkUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by HashCode on 2:22 PM 30/06/2018.
 */
public class ProfileFragment extends Fragment {

    private static final String TAG = ProfileFragment.class.getSimpleName();
    TextView cityEditText, usernameEditText, firstNameEditText, lastNameEditText, phoneEditText, stateEditText;

    CircleImageView pictureImageView;

    PreferenceManager preferenceManager;
    Context context;

    UploadButtonClicklistener uploadButtonClicklistener;

    public interface UploadButtonClicklistener{
        void onUploadClick();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        context = getContext();
        preferenceManager = new PreferenceManager(context);
        findAllViews(view);
        fillAllDetails();
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        uploadButtonClicklistener = (UploadButtonClicklistener) context;
    }

    public void findAllViews(View view) {
        cityEditText = view.findViewById(R.id.edt_user_city);
        stateEditText = view.findViewById(R.id.edt_user_state);
        usernameEditText = view.findViewById(R.id.edt_user_username);
        firstNameEditText = view.findViewById(R.id.edt_user_fname);
        lastNameEditText = view.findViewById(R.id.edt_user_lname);
        phoneEditText = view.findViewById(R.id.edt_user_phone);
        pictureImageView = view.findViewById(R.id.user_profile_picture);

    }

    public void fillAllDetails() {
        Metadata userMetadata = preferenceManager.getPrefUserMetadata();
        cityEditText.setText(userMetadata.getCity());
        stateEditText.setText(userMetadata.getState());
        usernameEditText.setText(userMetadata.getUsername());
        firstNameEditText.setText(userMetadata.getFirstName());
        lastNameEditText.setText(userMetadata.getLastName());
        phoneEditText.setText(userMetadata.getPhoneNumber());
        NetworkUtils.loadImage(context, userMetadata.getProfilePic(), pictureImageView);
        String type = "Student";

    }



}
