package com.tankgame.app;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Bullet {
    private float x, y;
    private float direction;
    private float speed = 10;
    private int size = 8;
    private Rect rect;

    public Bullet(float x, float y, float direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.rect = new Rect();
        updateRect();
    }

    public void update() {
        // Move bullet based on direction
        float radians = (float) Math.toRadians(direction);
        x += Math.cos(radians) * speed;
        y += Math.sin(radians) * speed;
        updateRect();
    }

    public void draw(Canvas canvas, Paint paint) {
        paint.setColor(0xFFFFD700); // Gold color
        canvas.drawCircle(x, y, size / 2, paint);
        
        // Add glow effect
        paint.setColor(0xFFFFF8DC); // Cornsilk
        canvas.drawCircle(x, y, size / 4, paint);
    }

    private void updateRect() {
        rect.set((int) (x - size/2), (int) (y - size/2), 
                (int) (x + size/2), (int) (y + size/2));
    }

    public boolean isOutOfBounds(int screenWidth, int screenHeight) {
        return x < -size || x > screenWidth + size || 
               y < -size || y > screenHeight + size;
    }

    public Rect getRect() {
        return rect;
    }
}