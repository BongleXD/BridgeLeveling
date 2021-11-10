package me.bloodyhan.bridgeleveling.api;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Bloody_Han
 */
@Getter
public class PlayerRankUpEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Rank newRank;

    public PlayerRankUpEvent(Player player, Rank newRank) {
        this.player = player;
        this.newRank = newRank;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
