package org.sobadfish.title;

import cn.nukkit.Player;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.TextFormat;
import org.sobadfish.title.command.TitleCommand;
import org.sobadfish.title.manager.PlayerManager;
import org.sobadfish.title.manager.ShopManager;
import org.sobadfish.title.panel.lib.AbstractFakeInventory;
import org.sobadfish.title.thread.TitleLoadTask;
import org.sobadfish.title.utils.Tools;
import org.sobadfish.title.variables.TipVariable;
import tip.utils.Api;

import java.io.File;

/**
 * @author Sobadfish
 * @date 2022/9/15
 */
public class TitleMain extends PluginBase {

    public static TitleMain titleMain;

    public static PlayerManager playerManager;

    public static ShopManager shopManager;

    public static UiType uiType;

    //系统设置



    @Override
    public void onEnable() {
        titleMain = this;
        this.saveDefaultConfig();
        this.reloadConfig();
        sendMessageToConsole("正在加载称号系统 MYSQL版");
        checkServer();
        initConfig();
        sendMessageToConsole("称号系统加载完成");
        this.getServer().getPluginManager().registerEvents(new TitleListener(this),this);
        this.getServer().getCommandMap().register("title",new TitleCommand("ch"));
        this.getServer().getScheduler().scheduleRepeatingTask(new TitleLoadTask(this),10);
        Api.registerVariables("Title", TipVariable.class);



    }

    public void initConfig(){
        this.saveDefaultConfig();
        this.reloadConfig();

        uiType = Tools.loadUiTypeByName(getConfig().getString("UI","auto"));
//        saveResource("player.json",false);
        saveResource("titleShop.json",false);

        shopManager = ShopManager.asFile(new File(this.getDataFolder()+File.separator+"titleShop.json"));
        playerManager = PlayerManager.asDb(this);


    }

    public static TitleMain getTitleMain() {
        return titleMain;
    }

    public static void sendTipMessageToObject(String msg,Object o){
        String message = TextFormat.colorize('&',msg);
        if(o != null){
            if(o instanceof Player){
                if(((Player) o).isOnline()) {
                    ((Player) o).sendMessage(message);
                    return;
                }
            }
        }
        titleMain.getLogger().info(message);

    }

    public static void sendMessageToConsole(String msg){
        sendMessageToObject(msg,null);
    }

    public static String getTitle(){
        return TextFormat.colorize('&',titleMain.getConfig().getString("title"));
    }

    public static String getEmptyTitle(){
        return TextFormat.colorize('&',titleMain.getConfig().getString("empty-display","&c无称号"));
    }


    public static void sendMessageToObject(String msg,Object o){
        String message = TextFormat.colorize('&',getTitle()+" &b>>&r "+msg);
        sendTipMessageToObject(message,o);
    }

    public enum UiType{
        /**
         * auto: 自动
         *
         * packet: GUI界面
         *
         * ui: 箱子界面
         * */
        AUTO,PACKET,UI
    }

    private void checkServer(){
        boolean ver = false;
        //双核心兼容
        try {
            Class<?> c = Class.forName("cn.nukkit.Nukkit");
            c.getField("NUKKIT_PM1E");
            ver = true;

        } catch (ClassNotFoundException | NoSuchFieldException ignore) { }
        try {
            Class<?> c = Class.forName("cn.nukkit.Nukkit");
            c.getField("NUKKIT").get(c).toString().equalsIgnoreCase("Nukkit PetteriM1 Edition");
            ver = true;
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException ignore) {
        }

        AbstractFakeInventory.IS_PM1E = ver;
        if(ver){
            sendMessageToConsole("&e当前核心为 Nukkit PM1E");
        }else{
            sendMessageToConsole("&e当前核心为 Nukkit");
        }
    }

    @Override
    public void onDisable() {
//        if(playerManager != null){
//            playerManager.update();
//        }
        if(shopManager != null){
            shopManager.save();
        }

    }
}
