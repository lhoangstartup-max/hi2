package com.tankgame.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements Runnable {
    private Thread gameThread;
    private boolean isPlaying = false;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private Canvas canvas;
    
    // Game objects
    private Tank playerTank;
    private List<EnemyTank> enemyTanks;
    private List<Bullet> bullets;
    private List<Bullet> enemyBullets;
    
    // Game state
    private int score = 0;
    private int screenWidth, screenHeight;
    private Random random;
    private long lastEnemySpawn = 0;
    private long lastEnemyShoot = 0;
    
    public GameView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();
        random = new Random();
        
        // Initialize game objects
        playerTank = new Tank(100, 100, 50, 30);
        enemyTanks = new ArrayList<>();
        bullets = new ArrayList<>();
        enemyBullets = new ArrayList<>();
    }

    @Override
    public void run() {
        while (isPlaying) {
            update();
            draw();
            control();
        }
    }

    private void update() {
        // Update player tank
        playerTank.update();
        
        // Update bullets
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            bullet.update();
            if (bullet.isOutOfBounds(screenWidth, screenHeight)) {
                bullets.remove(i);
            }
        }
        
        // Update enemy bullets
        for (int i = enemyBullets.size() - 1; i >= 0; i--) {
            Bullet bullet = enemyBullets.get(i);
            bullet.update();
            if (bullet.isOutOfBounds(screenWidth, screenHeight)) {
                enemyBullets.remove(i);
            }
        }
        
        // Update enemy tanks
        for (int i = enemyTanks.size() - 1; i >= 0; i--) {
            EnemyTank enemy = enemyTanks.get(i);
            enemy.update();
            
            // Remove enemy if out of bounds
            if (enemy.getX() < -50 || enemy.getX() > screenWidth + 50) {
                enemyTanks.remove(i);
            }
        }
        
        // Spawn new enemies
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastEnemySpawn > 2000) { // Spawn every 2 seconds
            spawnEnemy();
            lastEnemySpawn = currentTime;
        }
        
        // Enemy shooting
        if (currentTime - lastEnemyShoot > 1500) { // Shoot every 1.5 seconds
            enemyShoot();
            lastEnemyShoot = currentTime;
        }
        
        // Check collisions
        checkCollisions();
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            
            // Clear screen
            canvas.drawColor(Color.BLACK);
            
            // Draw player tank
            playerTank.draw(canvas, paint);
            
            // Draw enemy tanks
            for (EnemyTank enemy : enemyTanks) {
                enemy.draw(canvas, paint);
            }
            
            // Draw bullets
            for (Bullet bullet : bullets) {
                bullet.draw(canvas, paint);
            }
            
            // Draw enemy bullets
            for (Bullet bullet : enemyBullets) {
                bullet.draw(canvas, paint);
            }
            
            // Draw score
            paint.setColor(Color.WHITE);
            paint.setTextSize(50);
            canvas.drawText("Score: " + score, 50, 50, paint);
            
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control() {
        try {
            Thread.sleep(17); // ~60 FPS
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Shoot bullet
            Bullet bullet = new Bullet(
                playerTank.getX() + playerTank.getWidth() / 2,
                playerTank.getY() + playerTank.getHeight() / 2,
                playerTank.getDirection()
            );
            bullets.add(bullet);
        }
        return true;
    }

    private void spawnEnemy() {
        int y = random.nextInt(screenHeight - 100) + 50;
        EnemyTank enemy = new EnemyTank(screenWidth, y, 40, 25);
        enemyTanks.add(enemy);
    }

    private void enemyShoot() {
        for (EnemyTank enemy : enemyTanks) {
            if (random.nextFloat() < 0.3f) { // 30% chance to shoot
                Bullet bullet = new Bullet(
                    enemy.getX() + enemy.getWidth() / 2,
                    enemy.getY() + enemy.getHeight() / 2,
                    enemy.getDirection()
                );
                enemyBullets.add(bullet);
            }
        }
    }

    private void checkCollisions() {
        // Player bullets vs Enemy tanks
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Bullet bullet = bullets.get(i);
            for (int j = enemyTanks.size() - 1; j >= 0; j--) {
                EnemyTank enemy = enemyTanks.get(j);
                if (bullet.getRect().intersect(enemy.getRect())) {
                    bullets.remove(i);
                    enemyTanks.remove(j);
                    score += 10;
                    break;
                }
            }
        }
        
        // Enemy bullets vs Player tank
        for (int i = enemyBullets.size() - 1; i >= 0; i--) {
            Bullet bullet = enemyBullets.get(i);
            if (bullet.getRect().intersect(playerTank.getRect())) {
                enemyBullets.remove(i);
                // Game over logic here
                score = Math.max(0, score - 5);
            }
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        screenWidth = w;
        screenHeight = h;
        playerTank.setY(h - 150); // Position player at bottom
    }
}