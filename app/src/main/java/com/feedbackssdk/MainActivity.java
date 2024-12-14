package com.feedbackssdk;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;


import com.feedbackslibary.CustomDialogFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load custom fonts using ResourcesCompat
        Typeface regularFont = ResourcesCompat.getFont(this, R.font.srisakdi_regular);
        Typeface boldFont = ResourcesCompat.getFont(this, R.font.srisakdi_bold);

        // Example button to launch the dialog
        Button openDialogButton = findViewById(R.id.openDialogButton);
        openDialogButton.setOnClickListener(v -> {
            // Replace "your_form_id" with the actual ID to fetch
            CustomDialogFragment dialogFragment = CustomDialogFragment.newInstance("675d9bf352178e707f81c24d");

            dialogFragment.show(getSupportFragmentManager(), "CustomDialogFragment");
        });
    }
}