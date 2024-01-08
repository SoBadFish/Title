package org.sobadfish.title.panel.items;

import cn.nukkit.Player;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.element.ElementButtonImageData;
import cn.nukkit.item.Item;
import cn.nukkit.nbt.tag.CompoundTag;
import cn.nukkit.utils.TextFormat;
import org.sobadfish.title.TitleMain;
import org.sobadfish.title.data.PlayerData;
import org.sobadfish.title.data.TitleData;
import org.sobadfish.title.panel.BasePlayPanelItemInstance;
import org.sobadfish.title.panel.ChestInventoryPanel;
import org.sobadfish.title.panel.from.TitleFrom;
import org.sobadfish.title.utils.Tools;

import java.util.ArrayList;
import java.util.List;

public class PlayerItem extends BasePlayPanelItemInstance {

    private final PlayerData info;

    private final TitleData titleData;

    public PlayerItem(PlayerData info, TitleData titleData){
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
    public void onClickButton(Player player, TitleFrom titleFrom) {
        use(player);
    }

    private void use(Player player){
        if(Tools.isOut(titleData.outTime) || !info.titles.contains(titleData)){
            TitleMain.sendMessageToObject("&c该称号已到期或不存在",player);
            return;
        }
        PlayerData data = TitleMain.playerManager.getData(player.getName());
        if(data.wearTitle != null){
            if(data.wearTitle.equals(titleData)){
//                data.wearTitle = null;
                TitleMain.sendMessageToObject("&a成功卸下称号",player);
                data.wearTitle(null);
                return;
            }
        }

        data.wearTitle(titleData);
        TitleMain.sendMessageToObject("&a成功更换称号 &r"+titleData.name.replace("@p",player.getName()),player);
    }

    @Override
    public Item getPanelItem(Player i, int index) {
        Item item = getItem().clone();
        PlayerData playerData = TitleMain.playerManager.getData(i.getName());
        item.setCustomName(TextFormat.colorize('&',"&r"+titleData.name.replace("@p",i.getName())));
        //todo 这里似乎可以画个lore
        List<String> lore = new ArrayList<>();
        lore.add(TextFormat.colorize('&',"&r"));
        lore.add(TextFormat.colorize('&', "&r☛ 到期时间: &a" +
                ((titleData.outTime == null || "".equalsIgnoreCase(titleData.outTime)) ? "永久" : titleData.outTime)));
        lore.add(TextFormat.colorize('&',"&r"));
        if(titleData.outTime != null && !"".equalsIgnoreCase(titleData.outTime.trim())) {
            int s = Tools.calLastedTime(titleData.outTime);
            if(s > 0){
                lore.add(TextFormat.colorize('&', "&r☛  &c" + Tools.timeToString(s) + " 后到期"));
            }

        }
        lore.add(TextFormat.colorize('&',"&r"));
        lore.add(TextFormat.colorize('&', "&r☛ 是否佩戴: "+((playerData.wearTitle != null && playerData.wearTitle.equals(titleData))?"&a已佩戴":"&c未佩戴")));

        item.setLore(lore.toArray(new String[0]));
        CompoundTag tag = item.getNamedTag();
        tag.putInt("index", index);
        item.setNamedTag(tag);


        return item;
    }

    @Override
    public ElementButton getButton(Player info) {
        PlayerData playerData = TitleMain.playerManager.getData(info.getName());
        return new ElementButton(TextFormat.colorize('&',titleData.name.replace("@p",info.getName())+"&r&7有效期 ("+((titleData.outTime == null || "".equalsIgnoreCase(titleData.outTime) || "null".equalsIgnoreCase(titleData.outTime))?"永久":titleData.outTime)+"&7)\n&r"
                +((playerData.wearTitle != null && playerData.wearTitle.equals(titleData))?"&a已佩戴":"&c未佩戴")
        ),new ElementButtonImageData("path","textures/ui/message"));
    }
}
