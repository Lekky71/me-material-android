package com.teammusa.mematerial.api_network;


import com.teammusa.mematerial.models.Message;
import com.teammusa.mematerial.models.Rating;
import com.teammusa.mematerial.models.RatingInput;
import com.teammusa.mematerial.models.requests.LoginRequest;
import com.teammusa.mematerial.models.requests.MessageFetchRequest;
import com.teammusa.mematerial.models.requests.NewMessageRequest;
import com.teammusa.mematerial.models.requests.SignUpRequest;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by HashCode on 6:28 AM 20/03/2018.
 */

public interface ApiEndpointInterface {

    @POST("api/user/signup/")
    Call<Object> signUpUser(@Body SignUpRequest userSignUpDetails);

    @POST("api/user/login/")
    Call<Object> logInUser(@Body LoginRequest userLoginDetails);

//    @GET("api/all-courses")
//    Call<ArrayList<Course>> fetchAllCourses();

    @POST("api/rate")
    Call<Rating> rateCourse(@Body RatingInput ratingInput);

    @POST("api/add-message")
    Call<ArrayList<Message>> sendMessage(@Body NewMessageRequest messageInput);

    @POST("api/all-messages")
    Call<ArrayList<Message>> fetchAllMessages(@Body MessageFetchRequest messageFetchInput);

//    @POST("api/enroll")
//    Call<EnrollResponse> enrollStudent(@Body EnrollRequest enrollRequest);

}
