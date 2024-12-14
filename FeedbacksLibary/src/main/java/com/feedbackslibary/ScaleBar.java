package com.feedbackslibary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Objects;

@SuppressLint("ViewConstructor")
public class ScaleBar extends LinearLayout {
    private SeekBar seekBar;
    private TextView thumbText;
    private int minValue;
    private int maxValue;
    private String customText;

    public ScaleBar(Context context, int minValue, int maxValue, String customText) {
        super(context);
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.customText = customText;
        init(context);
    }


    private void init(Context context) {
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        // Initialize thumb text
        thumbText = new TextView(context);
        thumbText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        thumbText.setGravity(Gravity.CENTER);
        addView(thumbText);
        setBackgroundColor(Color.TRANSPARENT);
        if(!customText.isEmpty()) {
            thumbText.setText(minValue + " " + customText);
        }
        else thumbText.setText(String.valueOf(minValue));

        // Initialize SeekBar
        seekBar = new SeekBar(context);
        addView(seekBar);

        // Set listener to update thumb text dynamically
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int currentValue = minValue + progress;
                thumbText.setText(currentValue + " " + customText);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBar.setMax(maxValue - minValue);
        seekBar.setProgress(0);
    }



    // Get the selected value
    public int getSelectedValue() {
        return minValue + seekBar.getProgress();
    }


    // Change the text color of the thumb text
    public void setTextColor(int color) {
        thumbText.setTextColor(color);
    }

    // Change the background color of the SeekBar
    public void setSeekBarColor(int color) {
        seekBar.setBackgroundColor(color);
    }
    public TextView getThumb()
    {
        return thumbText;
    }

    public void setThumbAndTrailColor(int color) {
        // Set thumb dynamically
        GradientDrawable thumbDrawable = new GradientDrawable();
        thumbDrawable.setShape(GradientDrawable.OVAL);
        thumbDrawable.setSize(60, 60); // Increased thumb size to make it easier to drag
        thumbDrawable.setColor(color); // Thumb color
        seekBar.setThumb(thumbDrawable);

        // Set progress (trail) color dynamically
        LayerDrawable progressDrawable = (LayerDrawable) seekBar.getProgressDrawable();

        // Set the progress (trail) color
        Drawable progressLayer = progressDrawable.findDrawableByLayerId(android.R.id.progress);
        progressLayer.setColorFilter(color, PorterDuff.Mode.SRC_IN);

        // Set the track (background) color if needed
        Drawable backgroundLayer = progressDrawable.findDrawableByLayerId(android.R.id.background);
        backgroundLayer.setColorFilter(0xFFD3D3D3, PorterDuff.Mode.SRC_IN); // Light gray
    }
    public void setThumbTextFont(Typeface typeface) {
        thumbText.setTypeface(typeface);
    }
}