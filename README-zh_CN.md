# BridgeLeveling Remake
BridgeLeveling 是一个 Bukkit 插件，可以为您的Bridge服务器提供更多独特的功能

# 功能
- 玩家最大搭路等级限制 (可根据需求自行关闭或调整)
- 通过玩家互动获得EXP经验值
  - 击杀玩家
  - 放置方块
  - 在线奖励
- 支持 MySQL / SQLite 数据库
- 玩家作战系统
- 排名系统
- 自定义配置系统
- 支持 PlaceHolderAPI 插件 (Version = 2.10.10)

# 待实现功能
- 支持 BridgePractice 插件

# 下载插件
- [MCBBS](https://www.mcbbs.net/thread-965207-1-1.html) (国内用户推荐)

# 前置插件
- [PlaceHolderAPI 2.10.10](https://github.com/PlaceholderAPI/PlaceholderAPI/releases/tag/2.10.10) (可选插件)

# 安装和使用
1. 下载 `BridgeLeveling-*.jar` 文件.
2. 将文件复制到服务器的 `Plugins` 文件夹.
3. 启动你的服务器.
4. 根据你的需求配置插件`Config`文件.
5. 重启你的服务器.
6. 完成.

# API
- 事件监听器 - BridgeLeveling 插件提供了4个事件.
```java
// 当玩家完成击杀时触发该事件
@EventHandler
public void onKill(PlayerKillEvent e) {
  // 你的代码
}

// 当玩家等级提升时触发该事件
@EventHandler
public void onLevelUp(PlayerLevelUpEvent e) {
  // 你的代码
}

// 当玩家排名提升时触发该事件
@EventHandler
public void onRankUp(PlayerRankUpEvent e) {
  // 你的代码
}

// 当玩家获得经验时触发该事件
@EventHandler
public void onXpGain(PlayerXpGainEvent e) {
  // 你的代码
}

```
- Player Data 获取玩家数据
```java
PlayerData data = PlayerData.getData(p.getUniqueId());
```
