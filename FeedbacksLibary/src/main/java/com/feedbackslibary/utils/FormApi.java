package com.feedbackslibary.utils;

import com.feedbackslibary.models.FeedbackRequest;
import com.feedbackslibary.models.Form;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FormApi {
    @GET("forms/{id}")
    Call<Form> getForm(@Path("id") String formId);

    @POST("feedbacks")
    Call<Void> insertFeedback(@Body FeedbackRequest feedbackRequest);
}