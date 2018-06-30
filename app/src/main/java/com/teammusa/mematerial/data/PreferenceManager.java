package com.teammusa.mematerial.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.teammusa.mematerial.models.Metadata;

public class PreferenceManager {
    private static final String PREFERENCE_FILE_NAME = "com.teammusa.mematerial";
    private static final String PREF_ICON_URL = "icon_url";
    private static final String USER_LOGGED_IN = "user-logged-in";
    private static final String PREF_USER_LOGGED_IN = "user-logged-in";

    private static final String PREF_USER_METADATA  = "com.teammusa.mematerial.USER_METADATA";

    private static final String PREF_CURRENT_COURSE  = "com.CURRENT.COURSE";


    SharedPreferences mSharedPreferences;

    public PreferenceManager(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_NAME, 0);
    }


    public void setIconUrl(String iconUrl) {
        Editor mEditor = this.mSharedPreferences.edit();
        mEditor.putString(PREF_ICON_URL, iconUrl);
        mEditor.apply();
    }

    public void setPrefUserLoggedIn(boolean loggedIn) {
        Editor mEditor = this.mSharedPreferences.edit();
        mEditor.putBoolean(PREF_USER_LOGGED_IN, loggedIn);
        mEditor.apply();
    }

    public boolean isUserLoggedIn() {
        return this.mSharedPreferences.getBoolean(PREF_USER_LOGGED_IN, false);
    }

    public String getUsername() {
        Metadata metadata = getPrefUserMetadata();
        return metadata.getUsername();
    }
//
//    public void setCurrentCourse(Course currentCourse) {
//        Editor mEditor = this.mSharedPreferences.edit();
//        mEditor.putString(PREF_CURRENT_COURSE, new Gson().toJson(currentCourse));
//        mEditor.apply();
//    }
//
//    public Course getCurrentCourse(){
//        String json = this.mSharedPreferences.getString(PREF_CURRENT_COURSE, null);
//        Course course = new Course();
//        if(json != null){
//            course = new Gson().fromJson(json, Course.class);
//        }
//        return course;
//    }


    public boolean clearAllData() {
        Editor mEditor = this.mSharedPreferences.edit();
        mEditor.putBoolean(USER_LOGGED_IN, false);
        mEditor.putString(PREF_USER_METADATA, "");
        mEditor.putString(PREF_ICON_URL, "");
        mEditor.apply();
        return true;
    }



    public boolean setPrefUserMetadata(Metadata userMetadata){
        String userString = new Gson().toJson(userMetadata);
        Editor mEditor = this.mSharedPreferences.edit();
        mEditor.putString(PREF_USER_METADATA, userString);
        mEditor.apply();
        return true;
    }


    public Metadata getPrefUserMetadata(){
        String userString = this.mSharedPreferences.getString(PREF_USER_METADATA, "");
        return new Gson().fromJson(userString, Metadata.class);
    }
}
