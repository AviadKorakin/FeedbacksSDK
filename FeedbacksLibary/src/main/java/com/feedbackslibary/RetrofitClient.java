package com.feedbackslibary;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getInstance(String environment) {
        String baseUrl = environment.equals("dev")
                ? "http://10.0.2.2:3000/" // Emulator
                : "http://192.168.10.6:3000/"; // Physical device (replace with your IP)

        if (retrofit == null || !baseUrl.equals(retrofit.baseUrl().toString())) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

