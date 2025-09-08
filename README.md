# Tank Battle Game - Android

Một game bắn xe tăng đơn giản được phát triển cho Android.

## Tính năng

- Điều khiển xe tăng xanh (người chơi)
- Bắn đạn bằng cách chạm vào màn hình
- Xe tăng địch (màu đỏ) xuất hiện và di chuyển ngẫu nhiên
- Xe tăng địch cũng có thể bắn đạn
- Hệ thống điểm số
- Phát hiện va chạm giữa đạn và xe tăng

## Cách chơi

1. Chạm vào màn hình để bắn đạn
2. Tránh đạn của xe tăng địch
3. Tiêu diệt xe tăng địch để ghi điểm
4. Mỗi xe tăng địch bị tiêu diệt = 10 điểm
5. Bị đạn địch bắn trúng = -5 điểm

## Cài đặt

1. Mở dự án trong Android Studio
2. Sync project với Gradle files
3. Build và chạy trên thiết bị Android hoặc emulator

## Yêu cầu hệ thống

- Android API level 21 trở lên
- Màn hình cảm ứng
- Chế độ ngang (landscape)

## Cấu trúc dự án

```
app/
├── src/main/
│   ├── java/com/tankgame/app/
│   │   ├── MainActivity.java      # Activity chính
│   │   ├── GameView.java          # Game loop và rendering
│   │   ├── Tank.java              # Class xe tăng cơ bản
│   │   ├── EnemyTank.java         # Class xe tăng địch
│   │   └── Bullet.java            # Class đạn
│   ├── res/
│   │   ├── values/
│   │   │   ├── strings.xml        # Chuỗi text
│   │   │   └── styles.xml         # Styles
│   │   └── mipmap-hdpi/
│   │       └── ic_launcher.png    # Icon app
│   └── AndroidManifest.xml        # Manifest file
├── build.gradle                   # App build configuration
└── proguard-rules.pro            # ProGuard rules
```

## Phát triển thêm

Có thể mở rộng game với các tính năng:
- Âm thanh và hiệu ứng
- Nhiều level khác nhau
- Power-ups
- Multiplayer
- Animation mượt mà hơn
- Menu chính và game over screen