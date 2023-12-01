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
            TitleMain.playerManager.dataList.remove(new PlayerData(playerName));
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
//        if(DisPlayWindowsFrom.SERVER_SETTING.containsKey(player)){
//            if(event.getResponse() instanceof FormResponseCustom){
//                FormResponseCustom custom = (FormResponseCustom) event.getResponse();
//                boolean isOpen = custom.getToggleResponse(1);
//                player.setSprinting(isOpen);
//                PlayerData data = TitleMain.playerManager.getData(player.getName());
////                data.hasSpring = isOpen;
//                TitleMain.sendMessageToObject("&a强制疾跑: &r"+(isOpen?"&e开启":"&c关闭"),player);
//
//            }



//        }
    }



//    @EventHandler
//    public void onMove(PlayerMoveEvent event){
//        Player player = event.getPlayer();
//        PlayerData data = TitleMain.playerManager.getData(player.getName());
//        if(data.hasSpring){
//            player.setSprinting();
//        }
//    }

//    @EventHandler
//    public void onJoin(PlayerJoinEvent event){
//        Player player = event.getPlayer();
//        if (player == null) {
//            return;
//        }
//        DisPlayWindowsFrom.addServerSetting(event.getPlayer());
//        PlayerData data = TitleMain.playerManager.getData(event.getPlayer().getName());
//        data.hasSpring = false;
////        event.getPlayer().setSprinting(true);
//    }

}
