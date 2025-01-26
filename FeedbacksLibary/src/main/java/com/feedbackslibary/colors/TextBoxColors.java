package com.feedbackslibary.colors;

import static androidx.core.content.ContextCompat.getColor;

import android.content.Context;
import android.graphics.Color;

import com.feedbackslibary.R;

import java.util.HashMap;
import java.util.Map;

// TextBox Colors
public class TextBoxColors {
    // Fields for text box colors
    public int backgroundColor;
    public int textColor;
    public int hintColor;
    public int focusedHintColor;
    public int boxStrokeColor;
    public int focusedBoxStrokeColor;
    public int errorTextColor;
    public int counterTextColor;
    public int cursorColor;
    public int placeholderColor;
    public int helperTextColor;
    // Cache for storing predefined theme instances
    private static final Map<Integer, Integer> colorCache = new HashMap<>();



    // Constructor for light/dark themes using String
    public TextBoxColors(String theme,Context context) {
        if ("light".equalsIgnoreCase(theme)) { // Light theme
            applyLightTheme(context);
        } else if ("dark".equalsIgnoreCase(theme)) { // Dark theme
            applyDarkTheme(context);
        } else {
            throw new IllegalArgumentException("Invalid theme. Use 'light' or 'dark'.");
        }
    }

    // Constructor for custom colors using String
    public TextBoxColors(String backgroundColor, String textColor, String hintColor, String focusedHintColor,
                         String boxStrokeColor, String focusedBoxStrokeColor, String errorTextColor,
                         String counterTextColor, String cursorColor, String placeholderColor, String helperTextColor) {
        this.backgroundColor = parseColor(backgroundColor);
        this.textColor = parseColor(textColor);
        this.hintColor = parseColor(hintColor);
        this.focusedHintColor = parseColor(focusedHintColor);
        this.boxStrokeColor = parseColor(boxStrokeColor);
        this.focusedBoxStrokeColor = parseColor(focusedBoxStrokeColor);
        this.errorTextColor = parseColor(errorTextColor);
        this.counterTextColor = parseColor(counterTextColor);
        this.cursorColor = parseColor(cursorColor);
        this.placeholderColor = parseColor(placeholderColor);
        this.helperTextColor = parseColor(helperTextColor);
    }

    // Helper method to parse color strings
    private int parseColor(String color) {
        try {
            return Color.parseColor(color);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid color string: " + color);
        }
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
        this.backgroundColor = getCachedColor(context, R.color.textbox_light_background_color);
        this.textColor = getCachedColor(context,R.color.textbox_light_text_color);
        this.hintColor = getCachedColor(context,R.color.textbox_light_hint_color);
        this.focusedHintColor = getCachedColor(context,R.color.textbox_light_focused_hint_color);
        this.boxStrokeColor = getCachedColor(context,R.color.textbox_light_box_stroke_color);
        this.focusedBoxStrokeColor = getCachedColor(context,R.color.textbox_light_focused_box_stroke_color);
        this.errorTextColor = getCachedColor(context,R.color.textbox_light_error_text_color);
        this.counterTextColor = getCachedColor(context,R.color.textbox_light_counter_text_color);
        this.cursorColor = getCachedColor(context,R.color.textbox_light_cursor_color);
        this.placeholderColor = getCachedColor(context,R.color.textbox_light_placeholder_color);
        this.helperTextColor = getCachedColor(context,R.color.textbox_light_helper_text_color);
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

    // Method to apply dark theme
    private void applyDarkTheme(Context context) {
        this.backgroundColor = getCachedColor(context,R.color.textbox_dark_background_color);
        this.textColor = getCachedColor(context,R.color.textbox_dark_text_color);
        this.hintColor = getCachedColor(context,R.color.textbox_dark_hint_color);
        this.focusedHintColor = getCachedColor(context,R.color.textbox_dark_focused_hint_color);
        this.boxStrokeColor = getCachedColor(context,R.color.textbox_dark_box_stroke_color);
        this.focusedBoxStrokeColor = getCachedColor(context,R.color.textbox_dark_focused_box_stroke_color);
        this.errorTextColor = getCachedColor(context,R.color.textbox_dark_error_text_color);
        this.counterTextColor = getCachedColor(context,R.color.textbox_dark_counter_text_color);
        this.cursorColor = getCachedColor(context,R.color.textbox_dark_cursor_color);
        this.placeholderColor = getCachedColor(context,R.color.textbox_dark_placeholder_color);
        this.helperTextColor = getCachedColor(context,R.color.textbox_dark_helper_text_color);
    }

    // Method to apply custom colors using String
    public void setCustomColors(String backgroundColor, String textColor, String hintColor, String focusedHintColor,
                                String boxStrokeColor, String focusedBoxStrokeColor, String errorTextColor,
                                String counterTextColor, String cursorColor, String placeholderColor, String helperTextColor) {
        this.backgroundColor = parseColor(backgroundColor);
        this.textColor = parseColor(textColor);
        this.hintColor = parseColor(hintColor);
        this.focusedHintColor = parseColor(focusedHintColor);
        this.boxStrokeColor = parseColor(boxStrokeColor);
        this.focusedBoxStrokeColor = parseColor(focusedBoxStrokeColor);
        this.errorTextColor = parseColor(errorTextColor);
        this.counterTextColor = parseColor(counterTextColor);
        this.cursorColor = parseColor(cursorColor);
        this.placeholderColor = parseColor(placeholderColor);
        this.helperTextColor = parseColor(helperTextColor);
    }
}
