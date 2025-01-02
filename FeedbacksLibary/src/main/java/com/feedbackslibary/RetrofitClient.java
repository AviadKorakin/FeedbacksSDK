package com.feedbackslibary;

import com.feedbackslibary.LoggingInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getInstance(String environment) {
        String baseUrl = environment.equals("dev")
                ? "http://192.168.10.6:3000/" // LOCAL HOST
                : "https://formgeneratorapi.onrender.com/"; // ON CLOUD

        // Create an OkHttpClient with the logging interceptor
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor())
                .build();

        if (retrofit == null || !baseUrl.equals(retrofit.baseUrl().toString())) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient) // Attach the custom OkHttpClient
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
