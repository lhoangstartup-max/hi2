package com.spaceshooter.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class SpaceActivity extends Activity {
    private SpaceView spaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Ẩn thanh tiêu đề và làm toàn màn hình
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // Tạo và hiển thị SpaceView
        spaceView = new SpaceView(this);
        setContentView(spaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        spaceView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        spaceView.resume();
    }
}