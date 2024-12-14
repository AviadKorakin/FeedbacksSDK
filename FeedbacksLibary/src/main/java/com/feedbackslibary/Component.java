package com.feedbackslibary;

import java.util.List;
import java.util.Map;

public class Component {
    private String type; // Enum: ['textbox', 'star', 'combobox', 'title', 'scale-bar']
    private String text; // Main text (e.g., question or title)
    private String secondaryText; // Secondary text (e.g., hint or additional info)
    private int order; // Component order
    private List<Option> options; // List of options for combobox (optional for other types)
    private Map<String, String> colors; // Colors map: key is the identifier, value is the color
    private Map<String, String> additionalData; // Additional data map for custom properties (optional)

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Map<String, String> getColors() {
        return colors;
    }

    public void setColors(Map<String, String> colors) {
        this.colors = colors;
    }

    public Map<String, String> getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(Map<String, String> additionalData) {
        this.additionalData = additionalData;
    }
}
