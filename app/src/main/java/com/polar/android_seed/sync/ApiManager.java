package com.polar.android_seed.sync;

import retrofit.RestAdapter;
import retrofit.converter.JacksonConverter;

public class ApiManager {

    private static final String API_URL = "";

    private static AppApi appApi;

    public static AppApi geApiClient() {
        // Create our Converter
        JacksonConverter converter = new JacksonConverter();

        if (appApi == null) {
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setEndpoint(API_URL)
                    .setLogLevel(RestAdapter.LogLevel.NONE)
                    .setConverter(converter)
                    .build();

            appApi = restAdapter.create(AppApi.class);
        }

        return appApi;
    }

}
