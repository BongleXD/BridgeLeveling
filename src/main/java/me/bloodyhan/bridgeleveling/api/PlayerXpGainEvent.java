package me.bloodyhan.bridgeleveling.api;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Bloody_Han
 */
@Getter
public class PlayerXpGainEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private int amount;

    public PlayerXpGainEvent(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

}
