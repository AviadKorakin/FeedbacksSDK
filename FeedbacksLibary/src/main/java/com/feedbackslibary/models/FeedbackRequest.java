package com.feedbackslibary.models;

import java.util.Map;

public class FeedbackRequest {
    private String formId;
    private String email;
    private Map<String, ResponseDetail> responses;

    // Constructor
    public FeedbackRequest(String formId, String email, Map<String, ResponseDetail> responses) {
        if (formId == null || formId.isEmpty()) {
            throw new IllegalArgumentException("Form ID cannot be null or empty.");
        }
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        if (responses == null || responses.isEmpty()) {
            throw new IllegalArgumentException("Responses cannot be null or empty.");
        }

        this.formId = formId;
        this.email = email;
        this.responses = responses;
    }

    // Getters and setters
    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        if (formId == null || formId.isEmpty()) {
            throw new IllegalArgumentException("Form ID cannot be null or empty.");
        }
        this.formId = formId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        this.email = email;
    }

    public Map<String, ResponseDetail> getResponses() {
        return responses;
    }

    public void setResponses(Map<String, ResponseDetail> responses) {
        if (responses == null || responses.isEmpty()) {
            throw new IllegalArgumentException("Responses cannot be null or empty.");
        }
        this.responses = responses;
    }

    @Override
    public String toString() {
        return "FeedbackRequest{" +
                "formId='" + formId + '\'' +
                ", email='" + email + '\'' +
                ", responses=" + responses +
                '}';
    }
}
