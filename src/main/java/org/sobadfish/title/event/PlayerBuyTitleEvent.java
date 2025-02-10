package org.sobadfish.title.event;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;
import org.sobadfish.title.data.ShopData;

/**
 * @author Sobadfish
 * @date 2025/2/10
 */
public class PlayerBuyTitleEvent  extends PlayerEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlers() {
        return HANDLERS;
    }

    public ShopData shopData;

    public PlayerBuyTitleEvent(Player player, ShopData shopData) {
        this.shopData = shopData;
        this.player = player;
    }


}
