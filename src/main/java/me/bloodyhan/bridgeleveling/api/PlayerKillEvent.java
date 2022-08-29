package me.bloodyhan.bridgeleveling.api;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * @author Bloody_Han
 */
@Getter
public class PlayerKillEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Player killer;

    public PlayerKillEvent(Player player, Player killer) {
        this.player = player;
        this.killer = killer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

	public static HandlerList getHandlerList() {
			return handlers;
		}

}
