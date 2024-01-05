package org.sobadfish.title.panel.items;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import me.onebone.economyapi.EconomyAPI;
import org.sobadfish.title.TitleMain;
import org.sobadfish.title.data.PlayerData;
import org.sobadfish.title.data.ShopData;
import org.sobadfish.title.panel.BasePlayPanelItemInstance;
import org.sobadfish.title.panel.ChestInventoryPanel;
import org.sobadfish.title.panel.from.TitleFrom;
import org.sobadfish.title.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sobadfish
 * @date 2022/9/15
 */
public class ShopItem extends BasePlayPanelItemInstance {


    private final PlayerData info;

    private final ShopData titleData;

    public ShopItem(PlayerData info, ShopData titleData){
        this.info = info;
        this.titleData = titleData;
    }


    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Item getItem() {
        return new Item(323,0);
    }

    @Override
    public void onClick(ChestInventoryPanel inventory, Player player) {
        use(player);
    }

    @Override
    public void onClickButton(Player player, TitleFrom shopFrom) {
        use(player);

    }

    private void use(Player player){

        if(EconomyAPI.getInstance().myMoney(player) >= titleData.money){
            EconomyAPI.getInstance().reduceMoney(player,titleData.money);
            info.addTitle(titleData.asTitleData());
            TitleMain.sendMessageToObject("&a你成功购买称号 &r"+titleData.name.replace("@p",player.getName()),player);
        }else{
            TitleMain.sendMessageToObject("&c你的金钱不足!",player);
        }

    }

    @Override
    public Item getPanelItem(Player info, int index) {
        Item item = getItem().clone();

        item.setCustomName(TextFormat.colorize('&',"&r"+titleData.name.replace("@p",info.getName())));
        //todo 这里似乎可以画个lore
        List<String> lore = new ArrayList<>();
        lore.add(TextFormat.colorize('&',"&r"));
        lore.add(TextFormat.colorize('&', "&r☛ 时长: &a" +
                ((titleData.time > 0 ) ? Tools.timeToString(titleData.time): "永久")));
        lore.add(TextFormat.colorize('&',"&r"));
        lore.add(TextFormat.colorize('&', "&r☛ 价格: &a"+titleData.money));
        lore.add(TextFormat.colorize('&',"&r"));
        lore.add(TextFormat.colorize('&',"&r☛ "+(this.info.titles.contains(titleData.asTitleData())?"&a已拥有":"&c未拥有")));


        item.setLore(lore.toArray(new String[0]));
        CompoundTag tag = item.getNamedTag();
        tag.putInt("index", index);
        item.setNamedTag(tag);
        return item;

    }

    @Override
    public ElementButton getButton(Player info) {
        return new ElementButton(TextFormat.colorize('&',titleData.name+" &2 "+ Tools.timeToString(titleData.time)+" &e￥"+titleData.money+"\n&l&e点击购买"),new ElementButtonImageData("path","textures/ui/message"));
    }
}
