package com.spaceshooter.app;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.Random;

public class EnemyShip {
    private float x, y;
    private int width, height;
    private float speed = 3;
    private Rect rect;
    private Random random;
    private long lastDirectionChange = 0;

    public EnemyShip(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.rect = new Rect();
        this.random = new Random();
        updateRect();
    }

    public void update() {
        // Move downward
        y += speed;
        
        // Random horizontal movement
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastDirectionChange > 1000) {
            if (random.nextBoolean()) {
                x += random.nextFloat() * 2 - 1; // Random movement
            }
            lastDirectionChange = currentTime;
        }
        
        updateRect();
    }

    public void draw(Canvas canvas, Paint paint) {
        // Draw enemy ship body (inverted triangle)
        paint.setColor(0xFFE91E63); // Pink
        float[] points = {
            x + width/2, y + height,  // Bottom point
            x, y,                     // Top left
            x + width, y              // Top right
        };
        canvas.drawVertices(Canvas.VertexMode.TRIANGLES, 6, points, 0, null, 0, null, 0, null, 0, 0, paint);
        
        // Draw ship details
        paint.setColor(0xFFC2185B); // Dark pink
        canvas.drawRect(x + width/2 - 4, y, x + width/2 + 4, y + height/2, paint);
        
        // Draw engine glow
        paint.setColor(0xFFFF4081); // Light pink
        canvas.drawRect(x + width/2 - 2, y + height, x + width/2 + 2, y + height + 8, paint);
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
}