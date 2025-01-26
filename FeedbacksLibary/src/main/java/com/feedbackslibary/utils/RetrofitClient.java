package com.feedbackslibary.utils;

import android.content.Context;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class RetrofitClient {
    private static Retrofit retrofit;

    public static Retrofit getInstance(Context context, String environment) {
        String baseUrl = environment.equals("dev")
                ? "http://192.168.10.6:3000/" // LOCAL HOST
                : "https://formgeneratorapi.onrender.com/"; // ON CLOUD

        // Retrieve the API key from metadata
        String apiKey = MetadataUtil.getApiKey(context);
        if (apiKey == null) {
            throw new IllegalStateException("API key not found in metadata.");
        }

        // Create an OkHttpClient with the API key interceptor
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggingInterceptor()) // Logging Interceptor
                .addInterceptor(new Interceptor() { // Add API Key Interceptor
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request.Builder requestBuilder = originalRequest.newBuilder()
                                .header("x-api-key", apiKey) // Add the API key header
                                .method(originalRequest.method(), originalRequest.body());
                        Request requestWithHeaders = requestBuilder.build();
                        return chain.proceed(requestWithHeaders);
                    }
                })
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
