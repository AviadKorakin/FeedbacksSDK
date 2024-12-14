package com.feedbackslibary;


import android.content.Context;
import android.content.res.ColorStateList;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicFormComponentBuilder {

    private final Context context;
    private final DesignColors designColors;
    private final  Typeface regularCustomFont;
    private final  Typeface boldCustomFont;


    // Update the map to hold Pair<View, String> instead of just View
    private final Map<String, MapDetails> inputViews = new HashMap<>();

    public DynamicFormComponentBuilder(Context context, DesignColors designColors, Typeface regularCustomFont, Typeface boldCustomFont) {
        this.context = context;
        this.designColors = designColors;
        this.regularCustomFont = regularCustomFont;
        this.boldCustomFont = boldCustomFont;
    }

    public Map<String, MapDetails> getInputViews() {
        return inputViews;
    }

    public View createTitle(Component component, Form form) {
        TitleColors titleColors = getTitleColors(component, form);

        ConstraintLayout container = createContainer();

        // Create a title TextView
        TextView title = new TextView(context);
        title.setId(View.generateViewId());
        title.setText(component.getText());
        title.setTextSize(24); // Larger size for title
        title.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        title.setTextColor(titleColors.textColor); // Apply custom text color
        title.setTypeface(boldCustomFont); // Apply the custom font
        // Apply the bordered background
        title.setBackground(createBorderedBackground(titleColors.backgroundColor, titleColors.borderColor));

        container.addView(title);

        // Apply constraints to keep the title aligned to the left
        applyTitleConstraints(container, title);

        return container;
    }


    // Updated createTextBox method
    public View createTextBox(Component component, Form form) {
        TextBoxColors textBoxColors = getTextBoxColors(component, form);

        ConstraintLayout container = createContainer();

        View label = createLabel(component.getText());
        container.addView(label);

        TextInputLayout textInputLayout = new TextInputLayout(context);
        textInputLayout.setId(View.generateViewId());
        textInputLayout.setHint(" "+component.getSecondaryText());
        textInputLayout.setTypeface(regularCustomFont); // Apply the custom font

        // Apply color states
        textInputLayout.setBoxBackgroundColor(textBoxColors.backgroundColor);
        textInputLayout.setHintTextColor(createColorStateList(textBoxColors.hintColor, textBoxColors.focusedHintColor));
        textInputLayout.setBoxStrokeColorStateList(createColorStateList(textBoxColors.boxStrokeColor, textBoxColors.focusedBoxStrokeColor));
        textInputLayout.setPlaceholderTextColor(createColorStateList(textBoxColors.placeholderColor, textBoxColors.focusedHintColor));
        textInputLayout.setHelperTextColor(createColorStateList(textBoxColors.helperTextColor, textBoxColors.focusedHintColor));
        textInputLayout.setErrorTextColor(ColorStateList.valueOf(textBoxColors.errorTextColor));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            textInputLayout.setCursorColor(ColorStateList.valueOf(textBoxColors.cursorColor));
        }

        textInputLayout.setCounterEnabled(true);
        textInputLayout.setCounterMaxLength(255);
        textInputLayout.setErrorEnabled(true);

        TextInputEditText editText = new TextInputEditText(context);
        editText.setTextColor(textBoxColors.textColor);
        textInputLayout.setDefaultHintTextColor(createColorStateList(textBoxColors.hintColor, textBoxColors.focusedHintColor));
        editText.setHintTextColor(createColorStateList(textBoxColors.hintColor, textBoxColors.focusedHintColor));
        editText.setBackground(null);
        editText.setTypeface(regularCustomFont);

        textInputLayout.addView(editText);
        container.addView(textInputLayout);

        applyConstraints(container, label, textInputLayout);
        


        // Add to inputViews map with type "textbox"
        inputViews.put(component.getText(),new MapDetails(textInputLayout,"textbox", component.getOrder()));

        return container;
    }

    public View createComboBox(Component component, Form form) {
        SpinnerColors spinnerColors = getSpinnerColors(component, form);

        ConstraintLayout container = createContainer();

        View label = createLabel(component.getText());
        container.addView(label);

        Spinner spinner = new Spinner(context);
        spinner.setId(View.generateViewId());
        spinner.setBackgroundColor(spinnerColors.getBackgroundColor());

        ArrayAdapter<String> adapter = createSpinnerAdapter(component.getOptions(), spinner, spinnerColors);
        spinner.setAdapter(adapter);

        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Handle item selection
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle no selection
            }
        });

        container.addView(spinner);
        applyConstraints(container, label, spinner);

        // Add to inputViews map with type "spinner"
        inputViews.put(component.getText(),new MapDetails(spinner,"combobox", component.getOrder()));

        return container;
    }
    public View createScaleBar(Component component, Form form) {
        ScaleBarColors scaleBarColors = getScaleBarColors(component, form);

        // Extract min and max values from the options
        int minValue = 0;
        int maxValue = 0;
        String unit="";
        if (component.getAdditionalData() != null ) {
            String minValueString=component.getAdditionalData().getOrDefault("min","0");
            minValueString= minValueString.isEmpty() ? "0" : minValueString;
            minValue = Integer.parseInt(minValueString);
            String maxValueString=component.getAdditionalData().getOrDefault("max","0");
            maxValueString= maxValueString.isEmpty() ? "0" : maxValueString;
            maxValue = Integer.parseInt(maxValueString);
            unit=component.getAdditionalData().getOrDefault("unit","");
        }

        ConstraintLayout container = createContainer();

        View label = createLabel(component.getText());
        container.addView(label);

        ScaleBar scaleBar = new ScaleBar(context,minValue, maxValue, unit);
        scaleBar.setId(View.generateViewId());
        scaleBar.setThumbAndTrailColor(scaleBarColors.thumbAndTrailColor);
        scaleBar.setTextColor(scaleBarColors.textColor);
        scaleBar.setThumbTextFont(regularCustomFont);

        container.addView(scaleBar);
        applyConstraints(container, label, scaleBar);

        // Add to inputViews map with type "scaleBar"
        inputViews.put(component.getText(), new MapDetails(scaleBar, "scale-bar", component.getOrder()));

        return container;
    }

    public View createStarRating(Component component, Form form) {
        StarRatingColors starRatingColors = getStarRatingColors(component, form);
        ConstraintLayout container = createContainer();

        View label = createLabel(component.getText());
        container.addView(label);

        RatingBar ratingBar = new RatingBar(context, null, android.R.attr.ratingBarStyle);
        ratingBar.setId(View.generateViewId());
        ratingBar.setNumStars(5);
        ratingBar.setStepSize(0.5f);

        ratingBar.setProgressTintList(ColorStateList.valueOf(starRatingColors.starFillColor));
        ratingBar.setSecondaryProgressTintList(ColorStateList.valueOf(starRatingColors.secondaryStarFillColor));

        container.addView(ratingBar);
        applyConstraints(container, label, ratingBar);


        // Add to inputViews map with type "ratingBar"
        inputViews.put(component.getText(),new MapDetails(ratingBar,"star", component.getOrder()));

        return container;
    }


    private TitleColors getTitleColors(Component component, Form form) {
        return form.getTheme().equals("custom")
                ? new TitleColors(
                component.getColors().get("textColor"),
                component.getColors().get("backgroundColor"),
                component.getColors().get("borderColor"))
                : new TitleColors(form.getTheme(),context);
    }
 
    private void applyTitleConstraints(ConstraintLayout container, View title) {

        // Layout parameters for the label (always match parent width, wrap content height)
        ConstraintLayout.LayoutParams titleParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, // Stretch label to match parent width
                ConstraintLayout.LayoutParams.WRAP_CONTENT  // Wrap content for height
        );
        title.setLayoutParams(titleParams);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(container);

        // Center the title horizontally and position it at the top
        constraintSet.connect(title.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16);
        constraintSet.connect(title.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(title.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);

        constraintSet.applyTo(container);
    }
    private Drawable createBorderedBackground(int backgroundColor, int borderColor) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(backgroundColor); // Inside color
        drawable.setStroke(4, borderColor); // Border width (2~4 pixels) and color
        drawable.setCornerRadius(8); // Optional: Add rounded corners for aesthetics
        return drawable;
    }

    private ConstraintLayout createContainer() {
        ConstraintLayout container = new ConstraintLayout(context);
        container.setId(View.generateViewId());
        container.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
        ));
        container.setPadding(16, 16, 16, 16);
        return container;
    }

    private TextView createLabel(String question) {
        TextView label = new TextView(context);
        label.setId(View.generateViewId());
        label.setText(question);
        label.setTextColor(designColors.textColor); // Default text color
        label.setTextSize(16); // Default text size
        label.setTypeface(regularCustomFont); // Apply the custom font



        return label;
    }










    private ArrayAdapter<String> createSpinnerAdapter(List<Option> options, Spinner spinner, SpinnerColors spinnerColors) {
        return new ArrayAdapter<>(context, R.layout.spinner_item, options.stream()
                .sorted(Comparator.comparingInt(Option::getOrder))
                .map(option -> option.getOrder() + ". " + option.getOptionText())
                .toArray(String[]::new)) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // This method defines the view for the selected item in the spinner
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTypeface(regularCustomFont); // Apply the custom font
                return textView;
            }

            @NonNull
            @Override
            public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
                TextView textView = (TextView) super.getDropDownView(position, convertView, parent);

                // Reset the background and text color for every item
                textView.setBackgroundColor(spinnerColors.dropdownBackgroundColor); // Default background color
                textView.setTextColor(spinnerColors.itemTextColor); // Default text color
                textView.setTypeface(regularCustomFont); // Apply the custom font

                // Apply special styling for the selected item
                if (spinner.getSelectedItemPosition() == position) {
                    textView.setBackgroundColor(spinnerColors.selectedItemBackgroundColor); // Highlighted background
                    textView.setTextColor(spinnerColors.selectedItemTextColor); // Highlighted text color

                }

                return textView;
            }
        };
    }


    private TextBoxColors getTextBoxColors(Component component, Form form) {
        return form.getTheme().equals("custom")
                ? new TextBoxColors(
                component.getColors().get("backgroundColor"),
                component.getColors().get("textColor"),
                component.getColors().get("hintColor"),
                component.getColors().get("focusedHintColor"),
                component.getColors().get("boxStrokeColor"),
                component.getColors().get("focusedBoxStrokeColor"),
                component.getColors().get("errorTextColor"),
                component.getColors().get("counterTextColor"),
                component.getColors().get("cursorColor"),
                component.getColors().get("placeholderColor"),
                component.getColors().get("helperTextColor"))
                : new TextBoxColors(form.getTheme(),context);
    }
    private ScaleBarColors getScaleBarColors(Component component, Form form) {
        if (form.getTheme().equals("custom")) {
            return new ScaleBarColors(
                    component.getColors().get("thumbAndTrailColor"),
                    component.getColors().get("textColor")
            );
        } else {
            return new ScaleBarColors(form.getTheme(),context);
        }
    }

    private SpinnerColors getSpinnerColors(Component component, Form form) {
        return form.getTheme().equals("custom")
                ? new SpinnerColors(

                component.getColors().get("itemTextColor"),
                component.getColors().get("selectedItemTextColor"),
                component.getColors().get("dropdownBackgroundColor"),
                component.getColors().get("selectedItemBackgroundColor"),
                component.getColors().get("backgroundColor"))

                : new SpinnerColors(form.getTheme(),context);
    }

    private StarRatingColors getStarRatingColors(Component component, Form form) {
        return form.getTheme().equals("custom")
                ? new StarRatingColors(
                component.getColors().get("starFillColor"),
                component.getColors().get("secondaryStarFillColor"))
                : new StarRatingColors(form.getTheme(),context);
    }

    private void applyConstraints(ConstraintLayout container, View label, View field) {
        // Determine layout parameters for the field based on its type
        ConstraintLayout.LayoutParams fieldParams = getLayoutParams(field);

        // Layout parameters for the label (always match parent width, wrap content height)
        ConstraintLayout.LayoutParams labelParams = new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT, // Stretch label to match parent width
                ConstraintLayout.LayoutParams.WRAP_CONTENT  // Wrap content for height
        );

        field.setLayoutParams(fieldParams);
        label.setLayoutParams(labelParams);

        // Apply constraints for label and field
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(container);

        // Constraints for the label
        constraintSet.connect(label.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP);
        constraintSet.connect(label.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START,32);
        constraintSet.connect(label.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END,8);

        // Constraints for the field
        constraintSet.connect(field.getId(), ConstraintSet.TOP, label.getId(), ConstraintSet.BOTTOM, 16);
        constraintSet.connect(field.getId(), ConstraintSet.START, label.getId(), ConstraintSet.START,64);
        constraintSet.connect(field.getId(), ConstraintSet.END, label.getId(), ConstraintSet.END,64);

        constraintSet.applyTo(container);
    }

    private @NonNull ConstraintLayout.LayoutParams getLayoutParams(View field) {
        ConstraintLayout.LayoutParams fieldParams;
        if (field instanceof RatingBar) {
            // For RatingBar, use wrap content for both width and height
            fieldParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT, // Wrap content for width
                    ConstraintLayout.LayoutParams.WRAP_CONTENT  // Wrap content for height
            );
        } else {
            // For other fields, match parent width and wrap content height
            fieldParams = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT, // Match parent width
                    ConstraintLayout.LayoutParams.WRAP_CONTENT  // Wrap content height
            );
        }
        return fieldParams;
    }


    private ColorStateList createColorStateList(int defaultColor, int focusedColor) {
        return new ColorStateList(
                new int[][]{
                        new int[]{android.R.attr.state_focused},  // Focused state
                        new int[]{-android.R.attr.state_focused} // Default state (negation of focused)
                },
                new int[]{
                        focusedColor,  // Color when focused
                        defaultColor   // Default color when not focused
                }
        );
    }


}
