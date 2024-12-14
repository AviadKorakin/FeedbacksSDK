package com.feedbackslibary;

public class ResponseDetail {
    private String answer;
    private String type;
    private int order; // Add the order field

    public ResponseDetail(String answer, String type, int order) {
        this.answer = answer;
        this.type = type;
        this.order = order;
    }

    // Getters and setters
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
