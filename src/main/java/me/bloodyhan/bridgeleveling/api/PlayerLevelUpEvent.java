package me.bloodyhan.bridgeleveling.api;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Bloody_Han
 */
@Getter
public class PlayerLevelUpEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private int newLevelUpCost;
    private int newLevel;

    public PlayerLevelUpEvent(Player player, int newLevel, int newLevelUpCost) {
        this.player = player;
        this.newLevel = newLevel;
        this.newLevelUpCost = newLevelUpCost;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
