package com.feedbackslibary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class BorderedTextView extends AppCompatTextView {

    private TextPaint outerBorderPaint;
    private TextPaint innerBorderPaint;
    private TextPaint textPaint;

    private int outerBorderColor = Color.MAGENTA; // Default outer border color
    private int innerBorderColor = Color.WHITE;  // Default inner border color
    private int textColor = Color.BLACK;         // Default text color

    private float outerBorderThickness = 12f;    // Default outer border thickness
    private float innerBorderThickness = 8f;     // Default inner border thickness

    public BorderedTextView(Context context) {
        super(context);
        init();
    }

    public BorderedTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BorderedTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // Initialize outer border paint
        outerBorderPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        outerBorderPaint.setStyle(Paint.Style.STROKE);
        outerBorderPaint.setStrokeWidth(outerBorderThickness);
        outerBorderPaint.setColor(outerBorderColor);

        // Initialize inner border paint
        innerBorderPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        innerBorderPaint.setStyle(Paint.Style.STROKE);
        innerBorderPaint.setStrokeWidth(innerBorderThickness);
        innerBorderPaint.setColor(innerBorderColor);

        // Initialize text paint
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setColor(textColor);
        textPaint.setTextSize(getTextSize());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String text = getText().toString();

        // Set paint attributes
        outerBorderPaint.setTextSize(getTextSize());
        outerBorderPaint.setTypeface(getTypeface());

        innerBorderPaint.setTextSize(getTextSize());
        innerBorderPaint.setTypeface(getTypeface());

        textPaint.setTextSize(getTextSize());
        textPaint.setTypeface(getTypeface());

        // Calculate width for text wrapping
        int availableWidth = getWidth() - getPaddingLeft() - getPaddingRight();

        // Create StaticLayout for each layer using StaticLayout.Builder
        StaticLayout outerBorderLayout = StaticLayout.Builder
                .obtain(text, 0, text.length(), outerBorderPaint, availableWidth)
                .setAlignment(Layout.Alignment.ALIGN_CENTER)
                .setLineSpacing(0, 1.0f)
                .setIncludePad(false)
                .build();

        StaticLayout innerBorderLayout = StaticLayout.Builder
                .obtain(text, 0, text.length(), innerBorderPaint, availableWidth)
                .setAlignment(Layout.Alignment.ALIGN_CENTER)
                .setLineSpacing(0, 1.0f)
                .setIncludePad(false)
                .build();

        StaticLayout mainTextLayout = StaticLayout.Builder
                .obtain(text, 0, text.length(), textPaint, availableWidth)
                .setAlignment(Layout.Alignment.ALIGN_CENTER)
                .setLineSpacing(0, 1.0f)
                .setIncludePad(false)
                .build();

        // Save canvas state and translate for padding
        canvas.save();
        canvas.translate(getPaddingLeft(), getPaddingTop());

        // Draw the outer border
        canvas.save();
        canvas.translate(outerBorderThickness / 2, outerBorderThickness / 2);
        outerBorderLayout.draw(canvas);
        canvas.restore();

        // Draw the inner border
        canvas.save();
        canvas.translate(innerBorderThickness / 2, innerBorderThickness / 2);
        innerBorderLayout.draw(canvas);
        canvas.restore();

        // Draw the main text
        mainTextLayout.draw(canvas);

        // Restore canvas state
        canvas.restore();
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
        if (textPaint != null) {
            textPaint.setColor(color);
        }
    }

    public void setOuterBorderColor(int color) {
        if (outerBorderPaint != null) {
            outerBorderPaint.setColor(color);
        }
    }

    public void setInnerBorderColor(int color) {
        if (innerBorderPaint != null) {
            innerBorderPaint.setColor(color);
        }
    }

    public void setOuterBorderThickness(float thickness) {
        if (outerBorderPaint != null) {
            outerBorderThickness = thickness;
            outerBorderPaint.setStrokeWidth(thickness);
        }
    }

    public void setInnerBorderThickness(float thickness) {
        if (innerBorderPaint != null) {
            innerBorderThickness = thickness;
            innerBorderPaint.setStrokeWidth(thickness);
        }
    }
}
