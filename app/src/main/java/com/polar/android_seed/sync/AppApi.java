package com.polar.android_seed.sync;

import org.json.JSONObject;

import java.util.HashMap;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

public interface AppApi {


    //Seguridad
    @GET("")
    JSONObject getLogin(@Query("login") String login, @Query("password") String password);

    @POST("")
    HashMap<String, Object> postLogin(@Body HashMap<String, Object> credentials);
}
