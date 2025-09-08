# Space Shooter Game - Android

Một game bắn tàu vũ trụ được phát triển dựa trên cấu trúc game xe tăng, với theme không gian và gameplay mới.

## Tính năng

- **Tàu vũ trụ người chơi** (màu xanh cyan) - có thể bắn laser
- **Tàu địch** (màu hồng) - di chuyển xuống và bắn laser đỏ
- **Thiên thạch** - chướng ngại vật di chuyển xuống
- **Background không gian** - với các ngôi sao nhấp nháy
- **Hệ thống mạng sống** - 3 mạng, mất mạng khi va chạm
- **Hệ thống điểm số** - ghi điểm khi tiêu diệt địch và thiên thạch
- **Game Over screen** - hiển thị điểm cuối cùng

## Cách chơi

1. **Chạm màn hình** để bắn laser xanh
2. **Tránh laser đỏ** của tàu địch
3. **Tránh va chạm** với tàu địch và thiên thạch
4. **Tiêu diệt tàu địch** = +20 điểm
5. **Tiêu diệt thiên thạch** = +10 điểm
6. **Va chạm với địch/thiên thạch** = -1 mạng
7. **Hết mạng** = Game Over

## So sánh với Game Xe Tăng

| Tính năng | Game Xe Tăng | Space Shooter |
|-----------|--------------|---------------|
| **Theme** | Chiến tranh mặt đất | Không gian vũ trụ |
| **Nhân vật chính** | Xe tăng xanh | Tàu vũ trụ cyan |
| **Kẻ thù** | Xe tăng đỏ | Tàu địch hồng + Thiên thạch |
| **Vũ khí** | Đạn vàng | Laser xanh/đỏ |
| **Background** | Đen đơn giản | Sao nhấp nháy |
| **Hệ thống mạng** | Không có | 3 mạng |
| **Game Over** | Không có | Có màn hình kết thúc |
| **Chướng ngại vật** | Không có | Thiên thạch |

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
space-shooter/
├── app/src/main/
│   ├── java/com/spaceshooter/app/
│   │   ├── SpaceActivity.java      # Activity chính
│   │   ├── SpaceView.java          # Game loop và rendering
│   │   ├── PlayerShip.java         # Tàu vũ trụ người chơi
│   │   ├── EnemyShip.java          # Tàu địch
│   │   ├── Laser.java              # Laser/đạn
│   │   ├── Asteroid.java           # Thiên thạch
│   │   └── Star.java               # Sao background
│   ├── res/
│   │   ├── values/
│   │   │   ├── strings.xml         # Chuỗi text
│   │   │   └── styles.xml          # Styles
│   │   └── mipmap-hdpi/
│   │       └── ic_launcher.png     # Icon app
│   └── AndroidManifest.xml         # Manifest file
├── build.gradle                    # App build configuration
└── README.md                       # Tài liệu này
```

## Phát triển thêm

Có thể mở rộng game với các tính năng:
- **Power-ups** (tăng tốc độ bắn, lá chắn, đạn đặc biệt)
- **Boss battles** (tàu địch lớn với nhiều mạng)
- **Nhiều level** với độ khó tăng dần
- **Âm thanh và hiệu ứng** (tiếng nổ, nhạc nền)
- **Animation mượt mà** hơn
- **Menu chính** và **pause screen**
- **High score** lưu trữ
- **Multiplayer** mode