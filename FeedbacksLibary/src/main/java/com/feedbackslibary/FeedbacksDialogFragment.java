package com.feedbackslibary;


import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FeedbacksDialogFragment extends DialogFragment {

    private static final String ARG_FORM_ID = "formId";
    private String formId;
    private DesignColors designColors;
    private DynamicFormComponentBuilder componentBuilder;
    private Typeface regularCustomFont=null;
    private Typeface boldCustomFont=null;
    private ImageView loadingGifView;
    private ConstraintLayout loadingContainer; // Container for the loading icon



    private float dialogWidthPercent = 1f; // Default width as 80% of the screen
    private int margin = 32; // Default margin between components

    public static FeedbacksDialogFragment newInstance(String formId) {
        FeedbacksDialogFragment fragment = new FeedbacksDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FORM_ID, formId);
        fragment.setArguments(args);

        return fragment;
    }
    public static FeedbacksDialogFragment newInstance(String formId, Typeface regularFont, Typeface boldFont, float dialogWidthPercent, int margin) {
        FeedbacksDialogFragment fragment = new FeedbacksDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FORM_ID, formId);
        fragment.setArguments(args);
        // Pass fonts directly to the fragment
        fragment.setFonts(regularFont, boldFont);
        fragment.dialogWidthPercent= dialogWidthPercent;
        fragment.margin=margin;

        return fragment;
    }
    public static FeedbacksDialogFragment newInstance(String formId, Typeface regularFont, Typeface boldFont) {
        FeedbacksDialogFragment fragment = new FeedbacksDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FORM_ID, formId);
        fragment.setArguments(args);
        // Pass fonts directly to the fragment
        fragment.setFonts(regularFont, boldFont);


        return fragment;
    }

    public void setDialogSize(float widthPercent) {
        this.dialogWidthPercent = widthPercent;
    }

    public void setMargins(int margin) {
        this.margin = margin; // Allow user to set custom margin
    }

    public void setFonts(Typeface regularFont, Typeface boldFont) {
        this.regularCustomFont = regularFont;
        this.boldCustomFont = boldFont;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            formId = getArguments().getString(ARG_FORM_ID);
        }
        if (regularCustomFont == null) {
            regularCustomFont = ResourcesCompat.getFont(requireContext(), R.font.oleoscriptswashcaps_regular);
        }
        if (boldCustomFont == null) {
            boldCustomFont = ResourcesCompat.getFont(requireContext(), R.font.oleoscriptswashcaps_bold);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Create a ScrollView to wrap the root ConstraintLayout
        NestedScrollView nestedScrollView = new NestedScrollView(requireContext());
        nestedScrollView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));


        ConstraintLayout rootLayout = new ConstraintLayout(requireContext());
        rootLayout.setId(View.generateViewId());
        rootLayout.setLayoutParams(new ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT
        ));
        // Make rootLayout focusable
        rootLayout.setFocusable(true);
        rootLayout.setFocusableInTouchMode(true);

        // Add a click listener to the ConstraintLayout to gain focus
        rootLayout.setOnClickListener(v -> rootLayout.requestFocus());


        nestedScrollView.addView(rootLayout);
        fetchAndPopulateForm(rootLayout);

        return nestedScrollView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null && getDialog().getWindow() != null) {
            // Resize dialog
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int width = (int) (displayMetrics.widthPixels * dialogWidthPercent);
            int height = (int) (displayMetrics.heightPixels * 0.9f);
            getDialog().getWindow().setLayout(width, height);

            // Remove default background if needed
            getDialog().getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            // Center the dialog
            WindowManager.LayoutParams params = getDialog().getWindow().getAttributes();
            params.gravity = Gravity.CENTER;
            getDialog().getWindow().setAttributes(params);
        }
    }

    private void fetchAndPopulateForm(ConstraintLayout rootLayout) {
        // Step 1: Add a loading container with an icon
        addLoadingContainer(rootLayout);

        FormApi api = RetrofitClient.getInstance("prod").create(FormApi.class);
        api.getForm(formId).enqueue(new Callback<Form>() {
            @Override
            public void onResponse(@NonNull Call<Form> call, @NonNull Response<Form> response) {
                if (response.isSuccessful() && response.body() != null) {
                    rootLayout.removeView(loadingContainer); // Remove loading container
                    setupComponentBuilder(response.body()); // Setup component builder
                    populateForm(rootLayout, response.body()); // Populate form

                } else {
                    rootLayout.removeView(loadingContainer);
                    showMessagePopup("Form not found.");
                    dismiss();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Form> call, @NonNull Throwable t) {
                showMessagePopup("Network error: " + t.getLocalizedMessage());
                Log.d("SADSADSAQWE",t.getLocalizedMessage());
                dismiss();
            }
        });
    }


    private void addLoadingContainer(ConstraintLayout rootLayout) {
        if (loadingContainer == null) {
            // Create a container for the loading icon
            loadingContainer = new ConstraintLayout(requireContext());
            loadingContainer.setId(View.generateViewId());

            // Create and configure the loading icon
            ImageView loadingIcon = new ImageView(requireContext());
            loadingIcon.setId(View.generateViewId());
            Glide.with(requireContext())
                    .asGif()
                    .load(R.drawable.loading) // Replace with your GIF file
                    .into(loadingIcon);

            // Set layout parameters for the icon
            ConstraintLayout.LayoutParams iconParams = new ConstraintLayout.LayoutParams(
                    150, // Width in pixels (adjust as needed)
                    150  // Height in pixels (adjust as needed)
            );
            loadingIcon.setLayoutParams(iconParams);

            // Add the loading icon to the container
            loadingContainer.addView(loadingIcon);

            // Add the container to the root layout
            rootLayout.addView(loadingContainer);

            // Apply constraints to center the container and the icon
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(rootLayout);

            // Center the loading container in the root layout
            constraintSet.connect(loadingContainer.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
            constraintSet.connect(loadingContainer.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
            constraintSet.connect(loadingContainer.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
            constraintSet.connect(loadingContainer.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);

            // Center the loading icon within its container
            ConstraintSet iconConstraintSet = new ConstraintSet();
            iconConstraintSet.clone(loadingContainer);
            iconConstraintSet.connect(loadingIcon.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
            iconConstraintSet.connect(loadingIcon.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
            iconConstraintSet.connect(loadingIcon.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 0);
            iconConstraintSet.connect(loadingIcon.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 0);

            constraintSet.applyTo(rootLayout);
            iconConstraintSet.applyTo(loadingContainer);
        }

        // Make the loading container visible
        loadingContainer.setVisibility(View.VISIBLE);
    }


    private void setupComponentBuilder(Form form) {
        designColors = form.getTheme().equals("custom")
                ? new DesignColors(
                form.getDesignData().getBackgroundColor(),
                form.getDesignData().getTextColor(),
                form.getDesignData().getButtonBackgroundColor(),
                form.getDesignData().getButtonTextColor())
                : new DesignColors(form.getTheme(),getContext());

        componentBuilder = new DynamicFormComponentBuilder(requireContext(), designColors, regularCustomFont, boldCustomFont);
    }

    private void populateForm(ConstraintLayout rootLayout, Form form) {



        GradientDrawable backgroundDrawable = new GradientDrawable();
        backgroundDrawable.setColor(designColors.backgroundColor); // Set the background color
        backgroundDrawable.setCornerRadius(24 * getResources().getDisplayMetrics().density); // Set corner radius (24dp)
        rootLayout.setBackground(backgroundDrawable);

        ConstraintSet constraintSet = new ConstraintSet();
        rootLayout.removeAllViews();
        View previousView = null;

        // Add form name
        previousView = addFormName(rootLayout, form, constraintSet, previousView);


        // Add the remaining components
        previousView = addComponents(rootLayout, form, constraintSet, previousView);

        // Add dialog buttons
        addDialogButtons(rootLayout, previousView);
    }

    private View addFormName(ConstraintLayout rootLayout, Form form, ConstraintSet constraintSet, View previousView) {
        // Create an instance of BorderedTextView
        BorderedTextView formNameView = new BorderedTextView(requireContext());
        formNameView.setId(View.generateViewId());

        // Set the text and appearance
        formNameView.setText(form.getName());
        formNameView.setTextColor(designColors.textColor); // Main text color
        formNameView.setOuterBorderColor(designColors.buttonBackgroundColor); // Outer border color
        formNameView.setInnerBorderColor(designColors.backgroundColor); // Inner border color
        formNameView.setOuterBorderThickness(12); // Outer border thickness
        formNameView.setInnerBorderThickness(5); // Inner border thickness
        formNameView.setTextSize(48); // Font size for the title
        formNameView.setPadding(16, 16, 16, 16);
        formNameView.setTypeface(boldCustomFont);
        // Add the BorderedTextView to the root layout
        rootLayout.addView(formNameView);
        constraintSet.clone(rootLayout);

        // Apply constraints to position the title
        constraintSet.connect(formNameView.getId(), ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16);
        constraintSet.connect(formNameView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16);
        constraintSet.connect(formNameView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16);
        constraintSet.applyTo(rootLayout);

        return formNameView;
    }

    private void showLoadingIndicator(ConstraintLayout rootLayout) {
        if (loadingGifView == null) {
            // Create and configure ImageView for the GIF
            loadingGifView = new ImageView(requireContext());
            loadingGifView.setId(View.generateViewId());

            // Use Glide to load and play the GIF
            Glide.with(requireContext())
                    .asGif()
                    .load(R.drawable.loading) // Replace with your GIF file name
                    .into(loadingGifView);

            // Set layout parameters
            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(204, 204); // 30x30dp size
            loadingGifView.setLayoutParams(params);

            // Add ImageView to the layout
            rootLayout.addView(loadingGifView);

            // Apply constraints to position the ImageView at the bottom center
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(rootLayout);
            constraintSet.connect(loadingGifView.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 32); // 32dp margin from bottom
            constraintSet.connect(loadingGifView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
            constraintSet.connect(loadingGifView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 0);
            constraintSet.applyTo(rootLayout);
        }

        // Make the ImageView visible
        loadingGifView.setVisibility(View.VISIBLE);
    }


    private void hideLoadingIndicator(ConstraintLayout rootLayout) {
        if (loadingGifView != null) {
            // Remove the ImageView from the parent layout
            rootLayout.removeView(loadingGifView);
            loadingGifView = null; // Clear the reference to avoid memory leaks
        }
    }




    private void showErrorPopup(View anchorView, String message, boolean isSuccess) {
        View popupView = getLayoutInflater().inflate(R.layout.error_popup, null);
        TextView messageTextView = popupView.findViewById(R.id.error_message);
        ImageView iconView = popupView.findViewById(R.id.error_icon);

        // Set message text and text color
        messageTextView.setText(message);
        messageTextView.setTextColor(Color.WHITE);

        // Set background color and icon tint
        if (isSuccess) {
            popupView.setBackgroundColor(Color.parseColor("#4CAF50")); // Green background for success
            iconView.setImageResource(R.drawable.ic_success); // Replace with your success icon
        } else {
            popupView.setBackgroundColor(Color.parseColor("#F44336")); // Red background for failure
            iconView.setImageResource(R.drawable.ic_fail); // Replace with your error icon
        }
        iconView.setColorFilter(Color.WHITE); // White tint for the icon

        // Create the popup window
        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Transparent background
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        // Show the popup window at the start of the layout
        popupWindow.showAtLocation(anchorView, Gravity.TOP, 0,0); // Adjust horizontal and vertical offsets as needed
        // Automatically dismiss the popup if it's a success

    }
    private void showMessagePopup(String message) {
        // Inflate the custom popup view
        View popupView = getLayoutInflater().inflate(R.layout.error_popup, null); // Reuse the layout
        TextView messageTextView = popupView.findViewById(R.id.error_message);
        ImageView iconView = popupView.findViewById(R.id.error_icon);

        // Set the message text
        messageTextView.setText(message);
        messageTextView.setTextColor(Color.WHITE);

        // Set the icon
        iconView.setImageResource(R.drawable.ic_icon); // Replace with your success icon
        iconView.setVisibility(View.VISIBLE); // Ensure the icon is visible

        // Create a circular background with rounded edges
        GradientDrawable background = new GradientDrawable();
        background.setColor(Color.parseColor("#333333")); // Set background color
        background.setCornerRadius(50f); // Set corner radius (adjust for desired roundness)

        // Apply the background to the popup view
        popupView.setBackground(background);

        // Create the popup window
        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT)); // Transparent background
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);

        // Calculate the position to place the popup slightly above the bottom
        int yOffset = 100; // Adjust the vertical offset to position above the screen's bottom

        // Show the popup window near the bottom of the screen
        popupWindow.showAtLocation(requireActivity().getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, yOffset);

        // Add a delay before starting the fade-out animation
        popupView.postDelayed(() -> {
            popupView.animate()
                    .alpha(0f) // Fade to fully invisible
                    .setDuration(800) // 2 seconds duration
                    .withEndAction(popupWindow::dismiss) // Dismiss the popup after the fade-out
                    .start();
        }, 3000); // Initial delay of 2 seconds before starting the fade-out
    }





    private View addComponents(ConstraintLayout rootLayout, Form form, ConstraintSet constraintSet, View previousView) {
        for (Component component : form.getComponents()) {
            View componentView = createComponentView(component, form);
            if (componentView != null) {
                rootLayout.addView(componentView);
                constraintSet.clone(rootLayout);
                constraintSet.connect(componentView.getId(), ConstraintSet.TOP, previousView.getId(), ConstraintSet.BOTTOM, margin);
                constraintSet.connect(componentView.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16);
                constraintSet.connect(componentView.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16);
                constraintSet.applyTo(rootLayout);
                previousView = componentView;
            }
        }
        return previousView;
    }


    private View createComponentView(Component component, Form form) {
        switch (component.getType()) {
            case "textbox":
                return componentBuilder.createTextBox(component, form);
            case "combobox":
                return componentBuilder.createComboBox(component, form);
            case "star":
                return componentBuilder.createStarRating(component, form);
            case "scale-bar":
                return componentBuilder.createScaleBar(component, form);
            case "title":
                return componentBuilder.createTitle(component,form);
            default:
                return null;
        }
    }

    @SuppressLint("SetTextI18n")
    private void addDialogButtons(ConstraintLayout rootLayout, View previousView) {

        MaterialButton submitButton = new MaterialButton(requireContext(), null, com.google.android.material.R.attr.materialButtonOutlinedStyle);
        submitButton.setId(View.generateViewId());
        submitButton.setText("Submit");
        submitButton.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_submit)); // Replace with your icon

// Set icon size (in pixels)
        submitButton.setIconSize(64); // Adjust the size as needed
        submitButton.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_START);
        submitButton.setIconTint(ColorStateList.valueOf(designColors.buttonTextColor));
        submitButton.setBackgroundColor(designColors.buttonBackgroundColor);
        submitButton.setTextColor(designColors.buttonTextColor);

        MaterialButton closeButton = new MaterialButton(requireContext(), null, com.google.android.material.R.attr.materialButtonOutlinedStyle);
        closeButton.setId(View.generateViewId());
        closeButton.setText("Close");
        closeButton.setIcon(ContextCompat.getDrawable(requireContext(), R.drawable.ic_cancel)); // Replace with your icon

            // Set icon size (in pixels)
        closeButton.setIconSize(64); // Adjust the size as needed
        closeButton.setIconGravity(MaterialButton.ICON_GRAVITY_TEXT_START);
        closeButton.setIconTint(ColorStateList.valueOf(designColors.buttonTextColor));
        closeButton.setBackgroundColor(designColors.buttonBackgroundColor);
        closeButton.setTextColor(designColors.buttonTextColor);
        // Set click listener for close button
        closeButton.setOnClickListener(v -> dismiss()); // Close the dialog

        submitButton.setOnClickListener(v -> {
            rootLayout.requestFocus();
            Map<String, ResponseDetail> responses = new HashMap<>();
            boolean hasErrors = false;

            for (Map.Entry<String, MapDetails> entry : componentBuilder.getInputViews().entrySet()) {
                String question = entry.getKey();
                View inputView = entry.getValue().getView();
                String inputType = entry.getValue().getType();
                int order = entry.getValue().getOrder();

                String answer = "";

                switch (inputType) {
                    case "textbox":
                        if (inputView instanceof TextInputLayout) {
                            TextInputEditText editText = (TextInputEditText) ((TextInputLayout) inputView).getEditText();
                            if (editText != null) {
                                answer = Objects.requireNonNull(editText.getText()).toString().trim();
                                if (answer.isEmpty()) {
                                    // Set error if the textbox is empty
                                    ((TextInputLayout) inputView).setError("Please fill the textbox");
                                    hasErrors = true;
                                } else {
                                    ((TextInputLayout) inputView).setError(null); // Clear the error if filled
                                }
                            }
                        }
                        break;
                    case "combobox":
                        if (inputView instanceof Spinner) {
                            Spinner spinner = (Spinner) inputView;
                            answer = spinner.getSelectedItem().toString();
                        }
                        break;
                    case "star":
                        if (inputView instanceof RatingBar) {
                            RatingBar ratingBar = (RatingBar) inputView;
                            answer = String.valueOf(ratingBar.getRating());
                        }
                    case "scale-bar":
                        if (inputView instanceof ScaleBar) {
                            ScaleBar scaleBar= (ScaleBar) inputView;
                            answer = scaleBar.getThumb().getText().toString();
                        }
                        break;
                }

                // If there are no errors, add to the responses
                if (!hasErrors) {
                    ResponseDetail responseDetail = new ResponseDetail(answer, inputType, order);
                    responses.put(question, responseDetail);
                }
            }

            // Stop processing if there are validation errors
            if (hasErrors) {
                showErrorPopup(rootLayout, "Please ensure all fields are completed before proceeding.", false);
                return;
            }

            // Retrieve email
            ResponseDetail emailResponse = responses.get("Email");
            responses.remove("Email");

            if (emailResponse == null || emailResponse.getAnswer() == null) {
                return;
            }

            String email = emailResponse.getAnswer();

            // Create feedback request
            FeedbackRequest feedbackRequest = new FeedbackRequest(formId, email, responses);

            // Send the feedback via the API
            FormApi api = RetrofitClient.getInstance("prod").create(FormApi.class);

            showLoadingIndicator(rootLayout); // Show loading overlay

            api.insertFeedback(feedbackRequest).enqueue(new Callback<>() {
                @Override
                public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                    hideLoadingIndicator(rootLayout);
                    if (response.isSuccessful()) {
                        showErrorPopup(rootLayout, "Submission Successful!", true);
                        // Add a delay of 800ms before dismissing the dialog
                        rootLayout.getRootView().postDelayed(() -> dismiss(), 800);

                    } else {
                        try {
                            assert response.errorBody() != null;
                            String errorMessage = response.errorBody().string();
                            String userFriendlyMessage = parseErrorMessage(errorMessage);
                            showErrorPopup(rootLayout, userFriendlyMessage, false);
                        } catch (Exception e) {
                            showErrorPopup(rootLayout, "Something went wrong, \n  Try again...", false);;
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                    hideLoadingIndicator(rootLayout);
                    showMessagePopup("Network error: " + t.getLocalizedMessage());
                }
            });
        });

        rootLayout.addView(submitButton);
        rootLayout.addView(closeButton);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(rootLayout);

        // Align Submit Button to the left
        constraintSet.connect(submitButton.getId(), ConstraintSet.TOP, previousView.getId(), ConstraintSet.BOTTOM, margin);
        constraintSet.connect(submitButton.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 20);
        constraintSet.connect(submitButton.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 20);

        // Align Close Button to the right
        constraintSet.connect(closeButton.getId(), ConstraintSet.TOP, previousView.getId(), ConstraintSet.BOTTOM, margin);
        constraintSet.connect(closeButton.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 20);
        constraintSet.connect(closeButton.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 20);

        constraintSet.applyTo(rootLayout);
    }



    /**
     * Parse error messages from the server to provide cleaner output.
     * @param errorMessage Raw error message as a string.
     * @return Cleaned-up user-friendly error message.
     */
    private String parseErrorMessage(String errorMessage) {
        try {
            // Assume the server sends JSON errors like { "error": "Invalid input" }
            JSONObject errorJson = new JSONObject(errorMessage);
            if (errorJson.has("error")) {
                return errorJson.getString("error");
            } else {
                return "An error occurred. Please try again."; // Fallback message
            }
        } catch (JSONException e) {
            // If parsing fails, return the raw message
            return errorMessage;
        }
    }


}
