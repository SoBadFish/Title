package org.sobadfish.title.panel;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryHolder;
import org.sobadfish.title.TitleMain;
import org.sobadfish.title.data.PlayerData;
import org.sobadfish.title.data.ShopData;
import org.sobadfish.title.panel.items.PlayerItem;
import org.sobadfish.title.panel.items.ShopItem;
import org.sobadfish.title.panel.lib.AbstractFakeInventory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author SoBadFish
 * 2022/1/2
 */
public class DisPlayerPanel implements InventoryHolder {

    private AbstractFakeInventory inventory;

    public  Map<Integer, BasePlayPanelItemInstance> getTitlePanel(PlayerData playerData){
        Map<Integer,BasePlayPanelItemInstance> panel = new LinkedHashMap<>();
        int index = 10;
        int size = 0;
        for(PlayerData.TitleData titleData: playerData.titles){
            panel.put(index,new PlayerItem(playerData,titleData));
            if(size  == 6){
                size = 0;
                index += 3;
            }else{
                size++;
                index++;
            }
        }
        return panel;
    }

    public  Map<Integer, BasePlayPanelItemInstance> getShopPanel(PlayerData playerData){
        Map<Integer,BasePlayPanelItemInstance> panel = new LinkedHashMap<>();
        int index = 10;
        int size = 0;
        for(ShopData titleData: TitleMain.shopManager.dataList){
            panel.put(index,new ShopItem(playerData,titleData));
            if(size  == 6){
                size = 0;
                index += 3;
            }else{
                size++;
                index++;
            }
        }
        return panel;
    }


    public void displayPlayer(PlayerData player, Map<Integer, BasePlayPanelItemInstance> itemMap, String name){
        ChestInventoryPanel panel = new ChestInventoryPanel(player,this,name);
        panel.setPanel(itemMap);
        panel.id = ++Entity.entityCount;
        inventory = panel;
        String player1 = panel.getPlayer().name;
        Player p = Server.getInstance().getPlayer(player1);
        if(p != null){
            p.addWindow(panel);
        }


    }



    @Override
    public Inventory getInventory() {
        return inventory;
    }


}
