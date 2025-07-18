# 行動裝置程式設計 期末Project
僅限在成大校園使用，以使用者移動為主軸，透過使用者移動讓遊戲中角色觸發一連串事件，最終藉由完成遊戲目標達到運動效果的一個遊戲。

介紹影片:
https://youtu.be/M9KC_KKuO4E?si=qcQIy4GW_Wsy-joS

## Main Application Code
```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/finalproject/
│   │   │   ├── MainActivity.java         # 
│   │   │   ├── MapsActivity.java         # 
│   │   │   ├── BigMap.java               # 
│   │   │   ├── monsterfight.java         # 
│   │   │   ├── boss.java                 # 
│   │   │   ├── Backpack.java             # 
│   │   ├── res/                          # U
│   │   └── AndroidManifest.xml          
│   ├── test/                           
├── build.gradle.kts               
```

| 檔案             | 功能說明 |
|----------------------|----------|
| `MainActivity.java`  | App 入口 |
| `MapsActivity.java`  | 核心功能：地圖顯示、定位追蹤、戰鬥觸發、等級成長、安全區判定、Boss 事件等 |
| `Backpack.java`      | 背包畫面：顯示目前獲得的道具、處理穿戴與卸下裝備的功能 |
| `BigMap.java`        | 顯示放大地圖畫面，純顯示用途 |
| `boss.java`          | Boss 戰鬥畫面，玩家需用手機左右傾斜（感測器）控制角色閃避子彈 |
| `monsterfight.java`  | 怪物戰鬥畫面，玩家在 10 秒內擊敗怪物即可獲勝並隨機獲得道具 |

##  遊戲主要機制

- 玩家需透過實際走路移動角色。
- 遊戲初始後 3 個 Boss 會隨機出現在三個不同的地方。
- 地圖上會隨機出現小怪物。
- 擊敗怪物可隨機獲得裝備（寶劍、隱形斗篷、負重器）。
- 裝備會影響戰力或遊戲條件（例如隱形斗篷可避免怪物遭遇）。
- 背包中可查看與切換裝備狀態。
- 每當里程達一定門檻會自動升級並提升戰力。
- 擊敗所有 Boss 後則贏得遊戲

### 地圖與主邏輯（`MapsActivity.java`）
- 使用 Google Maps API 顯示角色位置。
- 讀取 GPS 更新距離與等級。
- 根據距離與穿戴狀態觸發怪物 (`monsterfight.java`)。

### 怪物戰鬥（`monsterfight.java`）
- 怪物由弱至強分為 A～F。
- 玩家需在 10 秒內攻擊怪物獲勝，否則死亡。
- 每次勝利有機率掉落道具。

###  Boss 戰（`boss.java`）
- 子彈會從螢幕不同位置飛出，玩家需傾斜手機控制角色左右閃避。
- 被擊中即判定失敗。

###  裝備與背包（`Backpack.java`）
- 玩家可穿戴或卸下三種裝備。
- 裝備會改變戰鬥判定、移動獎勵或遭遇率。
- 裝備狀態會透過 `Intent` 傳遞回 `MapsActivity`。


# 開發者
- 測量115 黃 朔
- 測量115 陳耀慶
