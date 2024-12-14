package com.feedbackslibary;


import java.util.Date;
import java.util.List;

public class Form {
    private String name; // Added 'name' field
    private String theme; // Enum: ['light', 'dark', 'custom']
    private DesignData designData; // Holds form-level design information
    private List<Component> components; // List of components
    private Date created_at; // Timestamp for creation
    private Date updated_at; // Timestamp for updates

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public DesignData getDesignData() {
        return designData;
    }

    public void setDesignData(DesignData designData) {
        this.designData = designData;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updated_at = updated_at;
    }
}
