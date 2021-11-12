English (Current Page)  |  [简体中文](https://github.com/China-Han-1209/BridgeLeveling/blob/main/README-zh_CN.md)

# BridgeLeveling Remake
BridgeLeveling is an Bukkit Plugin can leveling for your Bridge Servers and provides lots of unique functions.

# Features
- Max Level Limitation (Can be disabled)
- Obtain EXP from Kills / Placeing Blocks / Combat / Online Reward
- Support MySQL / SQLite
- Bridge Combat System
- Ranking System
- Fully Configurable
- PlaceHolderAPI Hook (Version = 2.10.10)

# Links (Free Download)
- [MCBBS](https://www.mcbbs.net/thread-965207-1-1.html)

# Dependency (Optional)
- [PlaceHolderAPI 2.10.10](https://github.com/PlaceholderAPI/PlaceholderAPI/releases/tag/2.10.10)

# Installation
1. Download `BridgeLeveling-*.jar` File.
2. Put it in `Plugins` folder.
3. Start your server.
4. Modify configurations you want.
5. Restart your server.
6. Done.

# API
- Events - BridgeLeveling provides total of 4 events.
```java
@EventHandler
public void onKill(PlayerKillEvent e) {
  // to do
}

@EventHandler
public void onLevelUp(PlayerLevelUpEvent e) {
  // to do
}

@EventHandler
public void onRankUp(PlayerRankUpEvent e) {
  // to do
}

@EventHandler
public void onXpGain(PlayerXpGainEvent e) {
  // to do
}

```
- Player Data
```java
PlayerData data = PlayerData.getData(p.getUniqueId());
```
