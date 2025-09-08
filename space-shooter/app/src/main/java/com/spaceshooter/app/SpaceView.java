package com.spaceshooter.app;

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

public class SpaceView extends SurfaceView implements Runnable {
    private Thread gameThread;
    private boolean isPlaying = false;
    private SurfaceHolder surfaceHolder;
    private Paint paint;
    private Canvas canvas;
    
    // Game objects
    private PlayerShip playerShip;
    private List<EnemyShip> enemyShips;
    private List<Asteroid> asteroids;
    private List<Laser> lasers;
    private List<Laser> enemyLasers;
    private List<Star> stars;
    
    // Game state
    private int score = 0;
    private int lives = 3;
    private int screenWidth, screenHeight;
    private Random random;
    private long lastEnemySpawn = 0;
    private long lastAsteroidSpawn = 0;
    private long lastEnemyShoot = 0;
    private long lastStarSpawn = 0;
    
    public SpaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        paint = new Paint();
        random = new Random();
        
        // Initialize game objects
        playerShip = new PlayerShip(100, 100, 60, 40);
        enemyShips = new ArrayList<>();
        asteroids = new ArrayList<>();
        lasers = new ArrayList<>();
        enemyLasers = new ArrayList<>();
        stars = new ArrayList<>();
        
        // Initialize background stars
        for (int i = 0; i < 50; i++) {
            stars.add(new Star(random.nextInt(1000), random.nextInt(1000), random.nextInt(3) + 1));
        }
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
        // Update player ship
        playerShip.update();
        
        // Update background stars
        for (Star star : stars) {
            star.update();
        }
        
        // Update lasers
        for (int i = lasers.size() - 1; i >= 0; i--) {
            Laser laser = lasers.get(i);
            laser.update();
            if (laser.isOutOfBounds(screenWidth, screenHeight)) {
                lasers.remove(i);
            }
        }
        
        // Update enemy lasers
        for (int i = enemyLasers.size() - 1; i >= 0; i--) {
            Laser laser = enemyLasers.get(i);
            laser.update();
            if (laser.isOutOfBounds(screenWidth, screenHeight)) {
                enemyLasers.remove(i);
            }
        }
        
        // Update enemy ships
        for (int i = enemyShips.size() - 1; i >= 0; i--) {
            EnemyShip enemy = enemyShips.get(i);
            enemy.update();
            
            // Remove enemy if out of bounds
            if (enemy.getY() > screenHeight + 50) {
                enemyShips.remove(i);
            }
        }
        
        // Update asteroids
        for (int i = asteroids.size() - 1; i >= 0; i--) {
            Asteroid asteroid = asteroids.get(i);
            asteroid.update();
            
            // Remove asteroid if out of bounds
            if (asteroid.getY() > screenHeight + 50) {
                asteroids.remove(i);
            }
        }
        
        // Spawn new objects
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastEnemySpawn > 1500) { // Spawn enemy every 1.5 seconds
            spawnEnemy();
            lastEnemySpawn = currentTime;
        }
        
        if (currentTime - lastAsteroidSpawn > 2000) { // Spawn asteroid every 2 seconds
            spawnAsteroid();
            lastAsteroidSpawn = currentTime;
        }
        
        // Enemy shooting
        if (currentTime - lastEnemyShoot > 2000) { // Shoot every 2 seconds
            enemyShoot();
            lastEnemyShoot = currentTime;
        }
        
        // Add new stars
        if (currentTime - lastStarSpawn > 100) {
            stars.add(new Star(random.nextInt(screenWidth), -10, random.nextInt(3) + 1));
            lastStarSpawn = currentTime;
        }
        
        // Check collisions
        checkCollisions();
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();
            
            // Clear screen with space background
            canvas.drawColor(Color.BLACK);
            
            // Draw background stars
            for (Star star : stars) {
                star.draw(canvas, paint);
            }
            
            // Draw player ship
            playerShip.draw(canvas, paint);
            
            // Draw enemy ships
            for (EnemyShip enemy : enemyShips) {
                enemy.draw(canvas, paint);
            }
            
            // Draw asteroids
            for (Asteroid asteroid : asteroids) {
                asteroid.draw(canvas, paint);
            }
            
            // Draw lasers
            for (Laser laser : lasers) {
                laser.draw(canvas, paint);
            }
            
            // Draw enemy lasers
            for (Laser laser : enemyLasers) {
                laser.draw(canvas, paint);
            }
            
            // Draw UI
            drawUI();
            
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void drawUI() {
        // Draw score
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        canvas.drawText("Score: " + score, 50, 50, paint);
        
        // Draw lives
        canvas.drawText("Lives: " + lives, 50, 100, paint);
        
        // Draw game over if no lives left
        if (lives <= 0) {
            paint.setColor(Color.RED);
            paint.setTextSize(80);
            canvas.drawText("GAME OVER", screenWidth/2 - 200, screenHeight/2, paint);
            paint.setTextSize(40);
            canvas.drawText("Final Score: " + score, screenWidth/2 - 150, screenHeight/2 + 80, paint);
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
            if (lives > 0) {
                // Shoot laser
                Laser laser = new Laser(
                    playerShip.getX() + playerShip.getWidth() / 2,
                    playerShip.getY(),
                    -90 // Shoot upward
                );
                lasers.add(laser);
            }
        }
        return true;
    }

    private void spawnEnemy() {
        int x = random.nextInt(screenWidth - 60) + 30;
        EnemyShip enemy = new EnemyShip(x, -50, 50, 35);
        enemyShips.add(enemy);
    }

    private void spawnAsteroid() {
        int x = random.nextInt(screenWidth - 40) + 20;
        int size = random.nextInt(20) + 20;
        Asteroid asteroid = new Asteroid(x, -size, size);
        asteroids.add(asteroid);
    }

    private void enemyShoot() {
        for (EnemyShip enemy : enemyShips) {
            if (random.nextFloat() < 0.2f) { // 20% chance to shoot
                Laser laser = new Laser(
                    enemy.getX() + enemy.getWidth() / 2,
                    enemy.getY() + enemy.getHeight(),
                    90 // Shoot downward
                );
                enemyLasers.add(laser);
            }
        }
    }

    private void checkCollisions() {
        // Player lasers vs Enemy ships
        for (int i = lasers.size() - 1; i >= 0; i--) {
            Laser laser = lasers.get(i);
            for (int j = enemyShips.size() - 1; j >= 0; j--) {
                EnemyShip enemy = enemyShips.get(j);
                if (laser.getRect().intersect(enemy.getRect())) {
                    lasers.remove(i);
                    enemyShips.remove(j);
                    score += 20;
                    break;
                }
            }
        }
        
        // Player lasers vs Asteroids
        for (int i = lasers.size() - 1; i >= 0; i--) {
            Laser laser = lasers.get(i);
            for (int j = asteroids.size() - 1; j >= 0; j--) {
                Asteroid asteroid = asteroids.get(j);
                if (laser.getRect().intersect(asteroid.getRect())) {
                    lasers.remove(i);
                    asteroids.remove(j);
                    score += 10;
                    break;
                }
            }
        }
        
        // Enemy lasers vs Player ship
        for (int i = enemyLasers.size() - 1; i >= 0; i--) {
            Laser laser = enemyLasers.get(i);
            if (laser.getRect().intersect(playerShip.getRect())) {
                enemyLasers.remove(i);
                lives--;
            }
        }
        
        // Enemy ships vs Player ship
        for (int i = enemyShips.size() - 1; i >= 0; i--) {
            EnemyShip enemy = enemyShips.get(i);
            if (enemy.getRect().intersect(playerShip.getRect())) {
                enemyShips.remove(i);
                lives--;
            }
        }
        
        // Asteroids vs Player ship
        for (int i = asteroids.size() - 1; i >= 0; i--) {
            Asteroid asteroid = asteroids.get(i);
            if (asteroid.getRect().intersect(playerShip.getRect())) {
                asteroids.remove(i);
                lives--;
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
        playerShip.setX(w/2 - 30); // Center player
        playerShip.setY(h - 150); // Position player at bottom
    }
}