package org.sobadfish.title.panel;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.form.element.ElementLabel;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.window.FormWindowCustom;
import cn.nukkit.utils.TextFormat;
import org.sobadfish.title.TitleMain;
import org.sobadfish.title.data.PlayerData;
import org.sobadfish.title.data.ShopData;
import org.sobadfish.title.panel.from.TitleFrom;
import org.sobadfish.title.panel.items.PlayerItem;
import org.sobadfish.title.panel.items.ShopItem;
import org.sobadfish.title.utils.Tools;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author SoBadFish
 * 2022/1/11
 */
public class DisPlayWindowsFrom {

    public static int FROM_ID = 2666;

    public static int FROM_MAX_ID = 105478;

    public static LinkedHashMap<String, TitleFrom> CUSTOM = new LinkedHashMap<>();

    public static LinkedHashMap<Player, Integer> SERVER_SETTING = new LinkedHashMap<>();

    public static List<BasePlayPanelItemInstance> getPlayerFrom(Player player){
        List<BasePlayPanelItemInstance> basePlayPanelItemInstances = new ArrayList<>();
        PlayerData data = TitleMain.playerManager.getData(player.getName());
        for(PlayerData.TitleData titleData: data.titles){
            basePlayPanelItemInstances.add(new PlayerItem(data,titleData));
        }
        return basePlayPanelItemInstances;
    }

    public static List<BasePlayPanelItemInstance> getShopFrom(Player player){
        List<BasePlayPanelItemInstance> basePlayPanelItemInstances = new ArrayList<>();
        PlayerData data = TitleMain.playerManager.getData(player.getName());
        for(ShopData titleData: TitleMain.shopManager.dataList){
            basePlayPanelItemInstances.add(new ShopItem(data,titleData));
        }
        return basePlayPanelItemInstances;

    }

    public static void disPlayerCustomMenu(Player player, String tag, List<BasePlayPanelItemInstance> from){
        TitleFrom titleFrom = new TitleFrom(tag,"",getId());
        titleFrom.setBaseButtons(from);
        CUSTOM.put(player.getName(), titleFrom);
        titleFrom.disPlay(player);
    }


    public static int getId(){
        return Tools.rand(FROM_ID,FROM_MAX_ID);
    }

    public static int getId(int min,int max){
        return Tools.rand(min,max);
    }



}
