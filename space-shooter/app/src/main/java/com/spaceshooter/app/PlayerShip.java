package com.spaceshooter.app;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class PlayerShip {
    private float x, y;
    private int width, height;
    private float speed = 8;
    private Rect rect;

    public PlayerShip(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rect = new Rect();
        updateRect();
    }

    public void update() {
        updateRect();
    }

    public void draw(Canvas canvas, Paint paint) {
        // Draw ship body (triangle shape)
        paint.setColor(0xFF00BCD4); // Cyan
        float[] points = {
            x + width/2, y,           // Top point
            x, y + height,            // Bottom left
            x + width, y + height     // Bottom right
        };
        canvas.drawVertices(Canvas.VertexMode.TRIANGLES, 6, points, 0, null, 0, null, 0, null, 0, 0, paint);
        
        // Draw ship details
        paint.setColor(0xFF0097A7); // Dark cyan
        canvas.drawRect(x + width/2 - 5, y + height/2, x + width/2 + 5, y + height, paint);
        
        // Draw engine glow
        paint.setColor(0xFF00E5FF); // Light cyan
        canvas.drawRect(x + width/2 - 3, y + height, x + width/2 + 3, y + height + 10, paint);
    }

    private void updateRect() {
        rect.set((int) x, (int) y, (int) (x + width), (int) (y + height));
    }

    // Getters and setters
    public float getX() { return x; }
    public float getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public Rect getRect() { return rect; }
    
    public void setX(float x) { this.x = x; updateRect(); }
    public void setY(float y) { this.y = y; updateRect(); }
}