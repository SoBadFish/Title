package org.sobadfish.title.event;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;
import cn.nukkit.event.player.PlayerEvent;
import org.sobadfish.title.data.TitleData;

/**
 * @author Sobadfish
 * @date 2025/2/10
 */
public class PlayerAddTitleEvent extends PlayerEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlers() {
        return HANDLERS;
    }

    public TitleData titleData;

    public PlayerAddTitleEvent(Player player, TitleData titleData) {
        this.titleData = titleData;
        this.player = player;
    }


}
