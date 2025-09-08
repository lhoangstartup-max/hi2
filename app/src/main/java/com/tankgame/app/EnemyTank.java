package com.tankgame.app;

import android.graphics.Canvas;
import android.graphics.Paint;
import java.util.Random;

public class EnemyTank extends Tank {
    private Random random;
    private long lastDirectionChange = 0;
    private float moveSpeed = 2;

    public EnemyTank(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.random = new Random();
        this.direction = 180; // Face left initially
        this.speed = moveSpeed;
    }

    @Override
    public void update() {
        // Change direction randomly
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastDirectionChange > 2000) { // Change every 2 seconds
            direction = random.nextFloat() * 360;
            lastDirectionChange = currentTime;
        }
        
        // Move tank
        super.update();
    }

    @Override
    public void draw(Canvas canvas, Paint paint) {
        // Draw enemy tank body (red)
        paint.setColor(0xFFF44336); // Red
        canvas.drawRect(x, y, x + width, y + height, paint);
        
        // Draw tank barrel
        paint.setColor(0xFFD32F2F); // Dark red
        float barrelLength = width * 0.8f;
        float barrelX = x + width / 2;
        float barrelY = y + height / 2;
        float endX = barrelX + (float) Math.cos(Math.toRadians(direction)) * barrelLength;
        float endY = barrelY + (float) Math.sin(Math.toRadians(direction)) * barrelLength;
        canvas.drawLine(barrelX, barrelY, endX, endY, paint);
        
        // Draw tank tracks
        paint.setColor(0xFFB71C1C); // Very dark red
        canvas.drawRect(x, y, x + width, y + 5, paint); // Top track
        canvas.drawRect(x, y + height - 5, x + width, y + height, paint); // Bottom track
    }
}