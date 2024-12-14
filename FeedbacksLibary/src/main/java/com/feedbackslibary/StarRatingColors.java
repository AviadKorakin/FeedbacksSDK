package com.feedbackslibary;

import static androidx.core.content.ContextCompat.getColor;

import android.content.Context;
import android.graphics.Color;

import java.util.HashMap;
import java.util.Map;

// StarRating Colors
public class StarRatingColors {
    // Fields for star rating colors
    public int starFillColor; // Active stars
    public int secondaryStarFillColor; // Partially filled stars

    private static final Map<Integer, Integer> colorCache = new HashMap<>();


    // Constructor for light/dark themes using String
    public StarRatingColors(String theme, Context context) {
        if ("light".equalsIgnoreCase(theme)) { // Light theme
            applyLightTheme(context);
        } else if ("dark".equalsIgnoreCase(theme)) { // Dark theme
            applyDarkTheme(context);
        } else {
            throw new IllegalArgumentException("Invalid theme. Use 'light' or 'dark'.");
        }
    }

    // Constructor for custom colors using hex strings
    public StarRatingColors(String starFillColor, String secondaryStarFillColor) {
        this.starFillColor = parseColor(starFillColor);
        this.secondaryStarFillColor = parseColor(secondaryStarFillColor);

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
        this.starFillColor = getCachedColor(context,R.color.star_light_star_fill_color);
        this.secondaryStarFillColor = getCachedColor(context,R.color.star_light_secondary_star_fill_color);
    }

    // Method to apply the dark theme
    private void applyDarkTheme(Context context) {
        this.starFillColor = getCachedColor(context,R.color.star_dark_star_fill_color);
        this.secondaryStarFillColor = getCachedColor(context,R.color.star_dark_secondary_star_fill_color);

    }

    // Method to apply custom colors using hex strings
    public void setCustomColors(String starFillColor, String secondaryStarFillColor) {
        this.starFillColor = parseColor(starFillColor);
        this.secondaryStarFillColor = parseColor(secondaryStarFillColor);

    }
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
