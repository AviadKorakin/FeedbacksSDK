package com.feedbackslibary;

import static androidx.core.content.ContextCompat.getColor;
import static okhttp3.internal.Internal.instance;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class DesignColors {
    // Fields for common colors
    public int backgroundColor;
    public int textColor;
    public int buttonBackgroundColor;
    public int buttonTextColor;

    private static final Map<Integer, Integer> colorCache = new HashMap<>();


    // Constructor for light/dark themes using String
    public DesignColors(String theme,Context context) {
        if ("light".equalsIgnoreCase(theme)) { // Light theme
            applyLightTheme(context);
        } else if ("dark".equalsIgnoreCase(theme)) { // Dark theme
            applyDarkTheme(context);
        } else {
            throw new IllegalArgumentException("Invalid theme. Use 'light' or 'dark'.");
        }
    }




    // Constructor for custom colors using hex strings
    public DesignColors(String backgroundColor, String textColor, String buttonBackgroundColor, String buttonTextColor) {
        this.backgroundColor = parseColor(backgroundColor);
        this.textColor = parseColor(textColor);
        this.buttonBackgroundColor = parseColor(buttonBackgroundColor);
        this.buttonTextColor = parseColor(buttonTextColor);
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

    // Method to apply the light theme
    private void applyLightTheme(Context context) {
        this.backgroundColor = getCachedColor(context, R.color.design_light_background_color);
        this.textColor = getCachedColor(context, R.color.design_light_text_color);
        this.buttonBackgroundColor = getCachedColor(context, R.color.design_light_button_background_color);
        this.buttonTextColor = getCachedColor(context, R.color.design_light_button_text_color);
    }

    // Method to apply the dark theme
    private void applyDarkTheme(Context context) {
        this.backgroundColor = getCachedColor(context, R.color.design_dark_background_color);
        this.textColor = getCachedColor(context, R.color.design_dark_text_color);
        this.buttonBackgroundColor = getCachedColor(context, R.color.design_dark_button_background_color);
        this.buttonTextColor = getCachedColor(context, R.color.design_dark_button_text_color);
    }

    // Method to apply custom colors using hex strings
    public void setCustomColors(String backgroundColor, String textColor, String buttonBackgroundColor, String buttonTextColor) {
        this.backgroundColor = parseColor(backgroundColor);
        this.textColor = parseColor(textColor);
        this.buttonBackgroundColor = parseColor(buttonBackgroundColor);
        this.buttonTextColor = parseColor(buttonTextColor);
    }
    // Helper method to get a cached color or resolve it
    private int getCachedColor(Context context, int colorResId) {
        if (colorCache.containsKey(colorResId)) {
            return colorCache.get(colorResId); // Return cached color
        }

        // Resolve the color and cache it
        int resolvedColor = getColor(context, colorResId);
        colorCache.put(colorResId, resolvedColor);
        return resolvedColor;
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
