package com.feedbackslibary.colors;

import static androidx.core.content.ContextCompat.getColor;

import android.content.Context;
import android.graphics.Color;

import com.feedbackslibary.R;

import java.util.HashMap;
import java.util.Map;

// Title Colors
public class TitleColors {
    // Fields for title colors
    public int textColor; // Title text color
    public int backgroundColor; // Title background color
    public int borderColor; // Title border color
    private static final Map<Integer, Integer> colorCache = new HashMap<>();

    // Constructor for light/dark themes using String
    public TitleColors(String theme, Context context) {
        if ("light".equalsIgnoreCase(theme)) {
            applyLightTheme(context);
        } else if ("dark".equalsIgnoreCase(theme)) {
            applyDarkTheme(context);
        } else {
            throw new IllegalArgumentException("Invalid theme. Use 'light' or 'dark'.");
        }
    }

    // Constructor for custom colors using hex strings
    public TitleColors(String textColor, String backgroundColor, String borderColor) {
        this.textColor = parseColor(textColor);
        this.backgroundColor = parseColor(backgroundColor);
        this.borderColor = parseColor(borderColor);
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
        this.textColor = getCachedColor(context, R.color.title_light_text_color);
        this.backgroundColor = getCachedColor(context,R.color.title_light_background_color);
        this.borderColor = getCachedColor(context,R.color.title_light_border_color);
    }

    // Method to apply the dark theme
    private void applyDarkTheme(Context context) {
        this.textColor = getCachedColor(context, R.color.title_dark_text_color);
        this.backgroundColor = getCachedColor(context,R.color.title_dark_background_color);
        this.borderColor = getCachedColor(context,R.color.title_dark_border_color);
    }

    // Method to apply custom colors using hex strings
    public void setCustomColors(String textColor, String backgroundColor, String borderColor) {
        this.textColor = parseColor(textColor);
        this.backgroundColor = parseColor(backgroundColor);
        this.borderColor = parseColor(borderColor);
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

