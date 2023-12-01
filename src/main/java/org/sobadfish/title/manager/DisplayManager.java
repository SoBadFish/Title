package org.sobadfish.title.manager;

import cn.nukkit.Player;
import org.sobadfish.title.TitleMain;
import org.sobadfish.title.data.PlayerData;
import org.sobadfish.title.panel.DisPlayWindowsFrom;
import org.sobadfish.title.panel.DisPlayerPanel;

/**
 * @author Sobadfish
 * @date 2022/9/15
 */
public class DisplayManager {


    public static void displayTitle(Player player){
        switch (TitleMain.uiType) {
            case PACKET:
                displayPacketTitle(player);
                break;
            case UI:
                displayFromTitle(player);
                break;
            default:
                if (player.getLoginChainData().getDeviceOS() == 7) {
                    displayPacketTitle(player);
                }else{
                    displayFromTitle(player);
                }
                break;
        }
    }

    /**
     * WIN10玩家的箱子界面
     * */
    private static void displayPacketTitle(Player player){
        DisPlayerPanel panel = new DisPlayerPanel();
        PlayerData data = TitleMain.playerManager.getData(player.getName());
        panel.displayPlayer(data,panel.getTitlePanel(data),"称号系统");
    }

    /**
     * 手机版玩家的GUI
     * */
    private static void displayFromTitle(Player player){
        DisPlayWindowsFrom.disPlayerCustomMenu(player,"称号系统",DisPlayWindowsFrom.getPlayerFrom(player));

    }

    public static void displayShop(Player player){
        switch (TitleMain.uiType) {
            case PACKET:
                displayPacketShop(player);
                break;
            case UI:
                displayFromShop(player);
                break;
            default:
                if (player.getLoginChainData().getDeviceOS() == 7) {
                    displayPacketShop(player);
                }else{
                    displayFromShop(player);
                }
                break;
        }
    }

    /**
     * WIN10玩家的箱子界面
     * */
    private static void displayPacketShop(Player player){
        DisPlayerPanel panel = new DisPlayerPanel();
        PlayerData data = TitleMain.playerManager.getData(player.getName());
        panel.displayPlayer(data,panel.getShopPanel(data),"§7◁§f｛§e称号§6☫§b商店§f｝§7▷");
    }

    /**
     * 手机版玩家的GUI
     * */
    private static void displayFromShop(Player player){
        DisPlayWindowsFrom.disPlayerCustomMenu(player,"§7◁§f｛§e称号§6☫§b商店§f｝§7▷",DisPlayWindowsFrom.getShopFrom(player));

    }
}
