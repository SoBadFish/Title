package org.sobadfish.title;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.inventory.InventoryTransactionEvent;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.transaction.InventoryTransaction;
import cn.nukkit.inventory.transaction.action.InventoryAction;
import cn.nukkit.item.Item;
import org.sobadfish.title.data.PlayerData;
import org.sobadfish.title.data.TitleData;
import org.sobadfish.title.event.*;
import org.sobadfish.title.panel.BasePlayPanelItemInstance;
import org.sobadfish.title.panel.ChestInventoryPanel;
import org.sobadfish.title.panel.DisPlayWindowsFrom;
import org.sobadfish.title.panel.from.TitleFrom;

/**
 * @author Sobadfish
 * @date 2022/9/15
 */
public class TitleListener implements Listener {

    public TitleMain plugin;



    public TitleListener(TitleMain plugin){
        this.plugin = plugin;
    }


    @EventHandler
    public void onItemChange(InventoryTransactionEvent event) {
        InventoryTransaction transaction = event.getTransaction();
        for (InventoryAction action : transaction.getActions()) {
            for (Inventory inventory : transaction.getInventories()) {
                if (inventory instanceof ChestInventoryPanel) {
                    PlayerData player = ((ChestInventoryPanel) inventory).getPlayer();
                    event.setCancelled();
                    Item i = action.getSourceItem();
                    if(i.hasCompoundTag() && i.getNamedTag().contains("index")){
                        int index = i.getNamedTag().getInt("index");
                        BasePlayPanelItemInstance item = ((ChestInventoryPanel) inventory).getPanel().getOrDefault(index,null);

                        if(item != null){
                            ((ChestInventoryPanel) inventory).clickSolt = index;
                            item.onClick((ChestInventoryPanel) inventory,player.getPlayer());
                            ((ChestInventoryPanel) inventory).update();
                        }
                    }

                }

            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        String playerName = event.getPlayer().getName();
        if(TitleMain.playerManager != null){
            if(TitleMain.enableSQL){
                TitleMain.playerManager.getDataList().remove(new PlayerData(playerName));
            }
        }


    }

    @EventHandler
    public void onFrom(PlayerFormRespondedEvent event) {
        Player player = event.getPlayer();
        if (DisPlayWindowsFrom.CUSTOM.containsKey(player.getName())) {
            TitleFrom simple = DisPlayWindowsFrom.CUSTOM.get(player.getName());
            if(simple.getId() == event.getFormID()) {
                if (event.getResponse() instanceof FormResponseSimple) {
                    BasePlayPanelItemInstance button = simple.getBaseButtons().get(((FormResponseSimple) event.getResponse())
                            .getClickedButtonId());
                    button.onClickButton(player,simple);
                }

            }else{
                DisPlayWindowsFrom.CUSTOM.remove(player.getName());
            }


        }
    }


    @EventHandler
    public void onPlayerAddTitleEvent(PlayerAddTitleEvent event){
        Player player = event.getPlayer();
        TitleData titleData = event.titleData;
        TitleMain.sendMessageToObject("&a获得称号:&r "+titleData.name.replace("@p",player.getName())
                +" 有效期: &7"+  ((titleData.outTime == null || "".equalsIgnoreCase(titleData.outTime))?"永久":titleData.outTime),player);
    }

    @EventHandler
    public void onPlayerWearTitleEvent(PlayerWearTitleEvent event){
        Player player = event.getPlayer();
        TitleData titleData = event.titleData;
        TitleMain.sendMessageToObject("&a穿戴称号:&r "+titleData.name.replace("@p",player.getName()),player);
    }


    @EventHandler
    public void onPlayerUnWearTitleEvent(PlayerUnWearTitleEvent event){
        Player player = event.getPlayer();
        TitleData titleData = event.titleData;
        TitleMain.sendMessageToObject("&c卸下称号:&r "+titleData.name.replace("@p",player.getName()),player);
    }

    @EventHandler
    public void onPlayerTitleTimeOutEvent(PlayerTitleTimeOutEvent event){
        Player player = event.getPlayer();
        TitleData titleData = event.titleData;
        TitleMain.sendMessageToObject("&r "+titleData.name.replace("@p",player.getName())+" &c称号已过期!",player);
    }

    @EventHandler
    public void onPlayerRemoveTitleEvent(PlayerRemoveTitleEvent event){
        Player player = event.getPlayer();
        TitleData titleData = event.titleData;
        TitleMain.sendMessageToObject("&c你失去了 &r "+titleData.name.replace("@p",player.getName())+" &r称号!",player);
    }

}
