English (Current Page)  |  [简体中文](https://github.com/BongleXD/BridgeLeveling/blob/main/README-zh_CN.md)
<p>
  <a href="https://github.com/BongleXD/BridgeLeveling/releases" target="_blank">
    <img alt="downloads" src="https://img.shields.io/github/v/release/BongleXD/BridgeLeveling?color=4166f5&style=flat-square" />
  </a>
  <a href="https://github.com/BongleXD/BridgeLeveling/releases" target="_blank">
    <img alt="downloads" src="https://img.shields.io/github/downloads/BongleXD/BridgeLeveling/total?color=4166f5&style=flat-square" />
  </a>
  <a href="https://github.com/BongleXD/BridgeLeveling/blob/main/LICENSE" target="_blank">
    <img alt="license" src="https://img.shields.io/github/license/BongleXD/BridgeLeveling?color=4166f5&style=flat-square" />
  </a>
</p>

# BridgeLeveling
BridgeLeveling is an Bukkit Plugin can leveling for your Bridge Servers and provides lots of unique functions.

# Features
- Max Level Limitation (config.yml set max-level to -1 to disable)
- Obtain EXP from Placeing Blocks / Combat / Online Reward
- Support MySQL / SQLite
- Bridge Combat System
- Ranking System
- Fully Configurable
- PlaceHolderAPI Hook (Version >= 2.10.10)

# Links (Free Download)
- [Releases](https://github.com/BongleXD/BridgeLeveling/releases)

# Dependency
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
  // TO DO
}

@EventHandler
public void onLevelUp(PlayerLevelUpEvent e) {
  // TO DO
}

@EventHandler
public void onRankUp(PlayerRankUpEvent e) {
  // TO DO
}

@EventHandler
public void onXpGain(PlayerXpGainEvent e) {
  // TO DO
}

```
- Player Data
```java
PlayerData data = PlayerData.getData(Player.getUniqueId());
```
