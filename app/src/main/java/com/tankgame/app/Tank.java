package com.tankgame.app;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Tank {
    protected float x, y;
    protected int width, height;
    protected float direction; // 0 = right, 90 = down, 180 = left, 270 = up
    protected float speed = 5;
    protected Rect rect;

    public Tank(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.direction = 0;
        this.rect = new Rect();
        updateRect();
    }

    public void update() {
        // Move tank based on direction
        float radians = (float) Math.toRadians(direction);
        x += Math.cos(radians) * speed;
        y += Math.sin(radians) * speed;
        updateRect();
    }

    public void draw(Canvas canvas, Paint paint) {
        // Draw tank body
        paint.setColor(0xFF4CAF50); // Green
        canvas.drawRect(x, y, x + width, y + height, paint);
        
        // Draw tank barrel
        paint.setColor(0xFF2E7D32); // Dark green
        float barrelLength = width * 0.8f;
        float barrelX = x + width / 2;
        float barrelY = y + height / 2;
        float endX = barrelX + (float) Math.cos(Math.toRadians(direction)) * barrelLength;
        float endY = barrelY + (float) Math.sin(Math.toRadians(direction)) * barrelLength;
        canvas.drawLine(barrelX, barrelY, endX, endY, paint);
        
        // Draw tank tracks
        paint.setColor(0xFF1B5E20); // Very dark green
        canvas.drawRect(x, y, x + width, y + 5, paint); // Top track
        canvas.drawRect(x, y + height - 5, x + width, y + height, paint); // Bottom track
    }

    protected void updateRect() {
        rect.set((int) x, (int) y, (int) (x + width), (int) (y + height));
    }

    // Getters and setters
    public float getX() { return x; }
    public float getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public float getDirection() { return direction; }
    public Rect getRect() { return rect; }
    
    public void setX(float x) { this.x = x; updateRect(); }
    public void setY(float y) { this.y = y; updateRect(); }
    public void setDirection(float direction) { this.direction = direction; }
}