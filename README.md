# Feedbacks Library - README

## Overview

The **Feedbacks Library** is an Android library designed to seamlessly integrate with the **Form Generator API**, allowing developers to dynamically load forms, capture user responses, and submit feedback. With a focus on ease of use, customization, and responsiveness, the library leverages modern Android UI components to deliver a powerful and intuitive form-handling experience.

---

## Key Features

- **Dynamic Form Loading**: Fetch and render forms dynamically from the server using the form ID.
- **Customizable UI**: Tailor the appearance of forms using themes, custom fonts, and colors.
- **Support for Modular Components**:
  - Textboxes
  - Comboboxes
  - 5-Star Ratings
  - Scale Bars
  - Titles
- **Built-in Validation**: Ensures all required fields are completed before submission.
- **User Feedback Submission**: Integrates directly with the API for submitting responses.
- **Loading and Error Handling**: Displays intuitive loading indicators and error messages during data fetch and submission.

---

## Installation

1. Add the library to your project:
   - Include the library's `.aar` file in your Android project's `libs` directory.
   - Update the `build.gradle` file to include:
     ```gradle
     implementation fileTree(dir: 'libs', include: ['*.aar'])
     ```

2. Add the required permissions to your `AndroidManifest.xml`:
   ```xml
   <uses-permission android:name="android.permission.INTERNET" />
   ```

---

## Usage

### 1️⃣ Initial Setup

To begin using the Feedbacks Library, ensure you have the **Form Generator API** set up and a valid form ID.

### 2️⃣ Displaying a Form

#### Instantiate and Display the Form Dialog:
```java
CustomDialogFragment dialog = CustomDialogFragment.newInstance(
    "FORM_ID", // Replace with your form ID
    Typeface.createFromAsset(getAssets(), "fonts/custom_regular.ttf"), // Regular font
    Typeface.createFromAsset(getAssets(), "fonts/custom_bold.ttf"),    // Bold font
    0.9f,   // Dialog width as a percentage of the screen
    24      // Margin between components in dp
);
dialog.show(getSupportFragmentManager(), "FormDialog");
```

#### Example:
```java
CustomDialogFragment dialog = CustomDialogFragment.newInstance("62df5b8451d9e80008a12345");
dialog.show(getSupportFragmentManager(), "FormDialog");
```

### 3️⃣ Customizing the Form

The dialog allows customization of:
- **Fonts**: Pass custom `Typeface` objects for regular and bold fonts.
- **Dialog Size**: Adjust the dialog width (as a percentage of screen width).
- **Margins**: Customize spacing between components.

### 4️⃣ Submitting Feedback

The library handles feedback submission directly through the API:
- Validates inputs.
- Displays loading indicators during submission.
- Handles success and error responses.

---

## API Integration

### Fetching Forms

The library fetches form definitions from the API based on the provided form ID. Example API endpoint:
```http
GET /forms/{id}
```

### Submitting Feedback

Feedback data is submitted through the API. Example endpoint:
```http
POST /feedbacks
```

---

## Components

The library supports the following modular components:

1. **Textbox**:
   - Single-line or multi-line text input.
   - Customizable background and text colors.

2. **Combobox**:
   - Drop-down selection with options.
   - Customizable text and background colors.

3. **5-Star Rating**:
   - Allows half-step ratings.
   - Customizable filled and unfilled star colors.

4. **Scale Bar**:
   - Adjustable range with a thumb for selecting values.
   - Customizable colors and unit labels.

5. **Titles**:
   - Highlighted text with borders for visual emphasis.
   - Customizable text, background, and border colors.

---

## Advanced Features

### Loading Indicator

Displays a GIF or custom animation while fetching form data:
```java
Glide.with(context)
    .asGif()
    .load(R.drawable.loading)
    .into(loadingIcon);
```

### Error Handling

- Network errors are captured and displayed in a popup.
- Validation errors highlight the affected fields.

### Response Structure

Responses are captured and validated before submission:
```json
{
  "formId": "62df5b8451d9e80008a12345",
  "email": "user@example.com",
  "responses": {
    "question1": {
      "answer": "John Doe",
      "type": "textbox",
      "order": 1
    }
  }
}
```

---

## Customization

### Themes

Customize the form's appearance using the `theme` field in the API or directly in the library:
- **Light Theme**
- **Dark Theme**
- **Custom Theme**:
  - Specify background, text, border, and placeholder colors for components.

### Fonts

Add custom fonts to your project and use them in the dialog:
```java
Typeface regularFont = ResourcesCompat.getFont(context, R.font.custom_regular);
Typeface boldFont = ResourcesCompat.getFont(context, R.font.custom_bold);
```

### Dialog Dimensions

Set dialog width and margins:
```java
dialog.setDialogSize(0.8f); // 80% of screen width
dialog.setMargins(16); // 16dp margin between components
```

---

## Dependencies

The library uses the following:
- **Glide**: For loading GIFs and images.
- **Retrofit**: For API calls.
- **Material Components**: For modern Android UI.
- **ConstraintLayout**: For responsive layouts.

Add these dependencies to your `build.gradle`:
```gradle
implementation "com.github.bumptech.glide:glide:4.12.0"
implementation "com.squareup.retrofit2:retrofit:2.9.0"
implementation "com.google.android.material:material:1.5.0"
```

---

## Contributions

Contributions to enhance the Feedbacks Library are welcome! Follow these steps:
1. Fork the repository.
2. Create a feature branch.
3. Submit a pull request.

---

## License

This project is licensed under the MIT License.  
© 2024 Aviad Korakin. All rights reserved.

---

## Contact

For inquiries or support, please reach out to **[Aviad Korakin](mailto:aviad825@gmail.com)**.
