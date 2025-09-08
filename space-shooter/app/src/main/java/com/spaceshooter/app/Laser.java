package com.spaceshooter.app;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Laser {
    private float x, y;
    private float direction;
    private float speed = 12;
    private int width = 4;
    private int height = 20;
    private Rect rect;

    public Laser(float x, float y, float direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.rect = new Rect();
        updateRect();
    }

    public void update() {
        // Move laser based on direction
        float radians = (float) Math.toRadians(direction);
        x += Math.cos(radians) * speed;
        y += Math.sin(radians) * speed;
        updateRect();
    }

    public void draw(Canvas canvas, Paint paint) {
        // Draw laser beam
        if (direction == -90) { // Player laser (upward)
            paint.setColor(0xFF00FF00); // Green
        } else { // Enemy laser (downward)
            paint.setColor(0xFFFF0000); // Red
        }
        
        canvas.drawRect(x - width/2, y - height/2, x + width/2, y + height/2, paint);
        
        // Add glow effect
        paint.setColor(0xFFFFFFFF); // White glow
        canvas.drawRect(x - width/4, y - height/4, x + width/4, y + height/4, paint);
    }

    private void updateRect() {
        rect.set((int) (x - width/2), (int) (y - height/2), 
                (int) (x + width/2), (int) (y + height/2));
    }

    public boolean isOutOfBounds(int screenWidth, int screenHeight) {
        return x < -width || x > screenWidth + width || 
               y < -height || y > screenHeight + height;
    }

    public Rect getRect() {
        return rect;
    }
}