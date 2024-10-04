# BridgeLeveling 
BridgeLeveling 是一个 Bukkit 插件，可以为您的搭路服务器提供更多独特的功能

# 功能
- 玩家最大等级限制 (可根据需求自行关闭或调整)
- 通过完成以下事件获得经验值
  - 击杀玩家
  - 放置方块
  - 在线奖励
- 支持 MySQL / SQLite 数据库
- 玩家战斗系统
- 段位系统
- 高度自定义配置
- 支持 PlaceHolderAPI 插件 (版本 >= 2.10.10)

# 下载插件
- [Releases](https://github.com/BongleXD/BridgeLeveling/releases)

# 前置插件
- [PlaceHolderAPI 2.10.10](https://github.com/PlaceholderAPI/PlaceholderAPI/releases/tag/2.10.10) (必选)

# 安装和使用
1. 下载 `BridgeLeveling-*.jar` 文件。
2. 将文件复制到服务器的 `Plugins` 文件夹。
3. 启动你的服务器。
4. 根据你的需求配置插件的配置文件。
5. 重启服务器。
6. 完成。

# API
- 事件监听器 - BridgeLeveling 插件提供了 4 个事件.
```java
// 当玩家完成击杀时触发该事件
@EventHandler
public void onKill(PlayerKillEvent e) {
  // TO DO
}

// 当玩家等级提升时触发该事件
@EventHandler
public void onLevelUp(PlayerLevelUpEvent e) {
  // TO DO
}

// 当玩家段位提升时触发该事件
@EventHandler
public void onRankUp(PlayerRankUpEvent e) {
  // TO DO
}

// 当玩家获得经验时触发该事件
@EventHandler
public void onXpGain(PlayerXpGainEvent e) {
  // TO DO
}

```
- Player Data 获取玩家数据
```java
PlayerData data = PlayerData.getData(p.getUniqueId());
```
