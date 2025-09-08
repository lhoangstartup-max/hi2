package com.spaceshooter.app;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import java.util.Random;

public class Asteroid {
    private float x, y;
    private int size;
    private float speed = 2;
    private float rotation = 0;
    private Rect rect;
    private Random random;

    public Asteroid(float x, float y, int size) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.rect = new Rect();
        this.random = new Random();
        updateRect();
    }

    public void update() {
        // Move downward
        y += speed;
        
        // Rotate
        rotation += 2;
        if (rotation >= 360) rotation = 0;
        
        // Slight horizontal drift
        x += (random.nextFloat() - 0.5f) * 0.5f;
        
        updateRect();
    }

    public void draw(Canvas canvas, Paint paint) {
        // Draw asteroid as irregular shape
        paint.setColor(0xFF795548); // Brown
        
        // Create irregular polygon points
        float[] points = new float[16];
        for (int i = 0; i < 8; i++) {
            float angle = (float) Math.toRadians(i * 45 + rotation);
            float radius = size + (random.nextInt(10) - 5);
            points[i * 2] = x + (float) Math.cos(angle) * radius;
            points[i * 2 + 1] = y + (float) Math.sin(angle) * radius;
        }
        
        // Draw filled polygon
        canvas.drawVertices(Canvas.VertexMode.TRIANGLE_FAN, 16, points, 0, null, 0, null, 0, null, 0, 0, paint);
        
        // Add some surface details
        paint.setColor(0xFF5D4037); // Darker brown
        canvas.drawCircle(x - size/3, y - size/3, size/6, paint);
        canvas.drawCircle(x + size/4, y + size/4, size/8, paint);
    }

    private void updateRect() {
        rect.set((int) (x - size), (int) (y - size), 
                (int) (x + size), (int) (y + size));
    }

    // Getters and setters
    public float getX() { return x; }
    public float getY() { return y; }
    public int getSize() { return size; }
    public Rect getRect() { return rect; }
}