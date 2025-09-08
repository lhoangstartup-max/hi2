package com.spaceshooter.app;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Star {
    private float x, y;
    private int size;
    private float speed = 1;
    private float twinkle = 0;

    public Star(float x, float y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = size; // Bigger stars move faster
    }

    public void update() {
        // Move downward
        y += speed;
        
        // Twinkle effect
        twinkle += 0.1f;
        if (twinkle >= Math.PI * 2) twinkle = 0;
    }

    public void draw(Canvas canvas, Paint paint) {
        // Calculate twinkle intensity
        float intensity = (float) (0.5f + 0.5f * Math.sin(twinkle));
        
        // Draw star with twinkle
        paint.setColor(0xFFFFFFFF); // White
        paint.setAlpha((int) (255 * intensity));
        canvas.drawCircle(x, y, size, paint);
        
        // Add glow for larger stars
        if (size > 1) {
            paint.setAlpha((int) (128 * intensity));
            canvas.drawCircle(x, y, size * 2, paint);
        }
        
        // Reset alpha
        paint.setAlpha(255);
    }
}