package com.feedbackslibary;

import static androidx.core.content.ContextCompat.getColor;

import android.content.Context;
import android.graphics.Color;

public class SpinnerColors {
    // Fields for spinner colors
    public int itemTextColor; // Default item text color
    public int selectedItemTextColor; // Selected item text color
    public int dropdownBackgroundColor; // Dropdown background color
    public int selectedItemBackgroundColor; // Background for selected item
    private int backgroundColor; // Overall background color for spinner


    // Constructor for light/dark themes using String
    public SpinnerColors(String theme,Context context) {
        if ("light".equalsIgnoreCase(theme)) { // Light theme
            applyLightTheme(context);
        } else if ("dark".equalsIgnoreCase(theme)) { // Dark theme
            applyDarkTheme(context);
        } else {
            throw new IllegalArgumentException("Invalid theme. Use 'light' or 'dark'.");
        }
    }

    // Constructor for custom colors using hex strings
    public SpinnerColors(String itemTextColor, String selectedItemTextColor, String dropdownBackgroundColor, String selectedItemBackgroundColor, String backgroundColor) {
        this.itemTextColor = parseColor(itemTextColor);
        this.selectedItemTextColor = parseColor(selectedItemTextColor);
        this.dropdownBackgroundColor = parseColor(dropdownBackgroundColor);
        this.selectedItemBackgroundColor = parseColor(selectedItemBackgroundColor);
        this.backgroundColor = parseColor(backgroundColor);
    }

    // Method to switch between light and dark themes
    public void setTheme(String theme,Context context) {
        if ("light".equalsIgnoreCase(theme)) {
            applyLightTheme(context);
        } else if ("dark".equalsIgnoreCase(theme)) {
            applyDarkTheme(context);
        } else {
            throw new IllegalArgumentException("Invalid theme. Use 'light' or 'dark'.");
        }
    }

    // Method to apply light theme
    private void applyLightTheme(Context context) {
        this.itemTextColor = getColor(context, R.color.combobox_light_item_text_color);
        this.selectedItemTextColor = getColor(context, R.color.combobox_light_selected_item_text_color);
        this.dropdownBackgroundColor = getColor(context, R.color.combobox_light_dropdown_background_color);
        this.selectedItemBackgroundColor = getColor(context, R.color.combobox_light_selected_item_background_color);
        this.backgroundColor = getColor(context, R.color.combobox_light_background_color);
    }

    // Method to apply dark theme
    private void applyDarkTheme(Context context) {
        this.itemTextColor = getColor(context, R.color.combobox_dark_item_text_color);
        this.selectedItemTextColor = getColor(context, R.color.combobox_dark_selected_item_text_color);
        this.dropdownBackgroundColor = getColor(context, R.color.combobox_dark_dropdown_background_color);
        this.selectedItemBackgroundColor = getColor(context, R.color.combobox_dark_selected_item_background_color);
        this.backgroundColor = getColor(context, R.color.combobox_dark_background_color);
    }

    // Method to apply custom colors using hex strings
    public void setCustomColors(String itemTextColor, String selectedItemTextColor, String dropdownBackgroundColor, String selectedItemBackgroundColor, String backgroundColor) {
        this.itemTextColor = parseColor(itemTextColor);
        this.selectedItemTextColor = parseColor(selectedItemTextColor);
        this.dropdownBackgroundColor = parseColor(dropdownBackgroundColor);
        this.selectedItemBackgroundColor = parseColor(selectedItemBackgroundColor);
        this.backgroundColor = parseColor(backgroundColor);
    }

    // Getter and Setter for backgroundColor
    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = parseColor(backgroundColor);
    }

    // Helper method to parse color strings
    private int parseColor(String color) {
        try {
            return Color.parseColor(color);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid color string: " + color);
        }
    }
}
