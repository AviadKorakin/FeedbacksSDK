package com.feedbackslibary;

import android.view.View;

public class MapDetails {
    private View view;
    private String type;
    private int order;

    public MapDetails(View view, String type, int order) {
        this.view = view;
        this.type = type;
        this.order = order;
    }

    // Getters and setters
    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
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
