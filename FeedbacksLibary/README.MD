Here is the updated section with **How to Use** included, styled and formatted consistently with the README:

---

## How to Use

### 1️⃣ Add Metadata to `AndroidManifest.xml`

Include the following metadata entry in your application's `AndroidManifest.xml` file to set the API key:

```xml
<meta-data
    android:name="com.feedbackslibary.API_KEY"
    android:value="YOUR-API-KEY" />
```

Replace `"YOUR-API-KEY"` with the actual API key provided during setup.

---

### 2️⃣ Create a Form

Use the Form Builder to design and generate a form. Include modular components such as textboxes, dropdowns, and rating bars.

You can easily create forms on **[our website](https://formgeneratorapi.onrender.com/)**.

**Example Request Body:**
```json
{
  "name": "Customer Feedback Form",
  "theme": "light",
  "components": [
    {
      "type": "textbox",
      "text": "What is your name?",
      "secondaryText": "Enter your full name.",
      "order": 1
    }
  ]
}
``` 

Now your users can directly use the website link to design their forms!

---

### 3️⃣ Use the Form ID in the SDK

Integrate the form into your Android application using the SDK. Fetch and display the form dynamically.

**Example Code:**
```java
openDialogButton.setOnClickListener(v -> {
    // Replace "your_form_id" with the actual ID to fetch
    FeedbacksDialogFragment dialogFragment = FeedbacksDialogFragment.newInstance("your_form_id");

    dialogFragment.show(getSupportFragmentManager(), "FeedbacksDialogFragment");
});
```

Replace `"your_form_id"` in the code above with the form ID generated from the Form Builder API.

---

This section is now consistent with the rest of the README's structure, style, and icons. Let me know if further adjustments are needed!