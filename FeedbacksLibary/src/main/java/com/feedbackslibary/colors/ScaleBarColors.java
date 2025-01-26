package com.feedbackslibary.colors;

import static androidx.core.content.ContextCompat.getColor;

import android.content.Context;
import android.graphics.Color;

import com.feedbackslibary.R;

import java.util.HashMap;
import java.util.Map;

public class ScaleBarColors {
    public int thumbAndTrailColor;
    public int textColor;

    private static final Map<Integer, Integer> colorCache = new HashMap<>();

    public ScaleBarColors(String theme,Context context) {

        if ("light".equalsIgnoreCase(theme)) {
            applyLightTheme(context);
        } else if ("dark".equalsIgnoreCase(theme)) {
            applyDarkTheme(context);
        } else {
            throw new IllegalArgumentException("Invalid theme. Use 'light' or 'dark'.");
        }
    }

    public ScaleBarColors(String thumbAndTrailColor, String textColor) {
            this.thumbAndTrailColor = parseColor(thumbAndTrailColor);
            this.textColor = parseColor(textColor);
    }

    private void applyLightTheme(Context context) {
        this.thumbAndTrailColor = getCachedColor(context, R.color.scalebar_light_thumb_and_trail_color);
        this.textColor = getCachedColor(context,R.color.scalebar_light_text_color);
    }

    private void applyDarkTheme(Context context) {
        this.thumbAndTrailColor = getCachedColor(context,R.color.scalebar_dark_thumb_and_trail_color);
        this.textColor = getCachedColor(context,R.color.scalebar_dark_text_color);

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

    private int parseColor(String color) {
        try {
            return Color.parseColor(color);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid color string: " + color);
        }
    }
}