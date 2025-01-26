package com.feedbackslibary.models;

public class Option {
    private String option_text;
    private int order;

    public Option(String option_text, int order) {
        this.option_text = option_text;
        this.order = order;
    }

    // Getters and Setters
    public String getOptionText() {
        return option_text;
    }

    public void setOptionText(String option_text) {
        this.option_text = option_text;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}
