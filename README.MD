To integrate the **"How to Use"** section with the dependency resolution and implementation details for your library (`FeedbacksSDK`), here’s how you can structure it:

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

### 2️⃣ Add the SDK Dependency

Ensure that your project is configured to fetch the library from JitPack. Update your **`settings.gradle`** and **`build.gradle`** as follows:

#### **`settings.gradle`**
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }
    }
}
```

#### **`build.gradle` (App Module)**
Add the library dependency to your app module's dependencies block:
```kotlin
dependencies {
    implementation("com.github.AviadKorakin:FeedbacksSDK:v1.0.4")
}
```

Sync your Gradle project after adding these changes.

---

### 3️⃣ Create a Form

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

### 4️⃣ Use the Form ID in the SDK

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

This structured approach ensures developers can quickly set up and integrate your library into their projects. Let me know if you’d like further refinements! 😊