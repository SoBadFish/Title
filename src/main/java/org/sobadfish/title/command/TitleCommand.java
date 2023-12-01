package org.sobadfish.title.command;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import org.sobadfish.title.TitleMain;
import org.sobadfish.title.data.PlayerData;
import org.sobadfish.title.data.ShopData;
import org.sobadfish.title.manager.DisplayManager;
import org.sobadfish.title.manager.ShopManager;

/**
 * @author Sobadfish
 * @date 2022/9/15
 */
public class TitleCommand extends Command {

    public TitleCommand(String s) {
        super(s);
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if(strings.length == 0){
            //TODO 展示称号窗口
            if(commandSender instanceof Player){
                DisplayManager.displayTitle((Player) commandSender);
            }else{
                TitleMain.sendMessageToConsole("指令错误 输/ch help 查看帮助");
            }
        }else{
            switch (strings[0]){
                case "h":
                case "help":
                case "?":
                    TitleMain.sendMessageToObject("&6&l-----------------------------------\n",commandSender);
                    TitleMain.sendMessageToObject("&6/ch help &7查看帮助指令",commandSender);
                    if(commandSender.isOp()){
                        TitleMain.sendMessageToObject("&6/ch shop &a[add/remove] &a[title] &a[money] &a[time(s)] &7增加商店内称号",commandSender);
                        TitleMain.sendMessageToObject("&6/ch give &a[player] &a[title] &a[time(s)] &7给予玩家一定时长的称号",commandSender);
                        TitleMain.sendMessageToObject("&6/ch remove &a[player] &a[title] &7移除玩家称号",commandSender);
                        TitleMain.sendMessageToObject("&6/ch reload &7重载配置",commandSender);
                    }else{
                        TitleMain.sendMessageToObject("&6/ch shop &7打开称号商店",commandSender);
                    }
                    TitleMain.sendMessageToObject("&6&l-----------------------------------\n",commandSender);
                    break;
                case "shop":
                    if(strings.length > 1 && commandSender.isOp()){
                        if(strings.length > 4){
                            switch (strings[1]){
                                case "add":
                                    ShopManager manager = TitleMain.shopManager;
                                    manager.dataList.add(new ShopData(strings[2],null,0,Integer.parseInt(strings[4]),Double.parseDouble(strings[3])));
                                    TitleMain.sendMessageToObject("&a成功添加到商店",commandSender);
                                    break;
                                case "remove":
                                    break;
                                default:break;
                            }
                        }else{
                            TitleMain.sendMessageToObject("&6/ch shop &a[add/remove] &a[title] &a[money] &a[time(s)] &7增加商店内称号",commandSender);
                        }
                    }else{
                        if(commandSender instanceof Player){
                            DisplayManager.displayShop((Player) commandSender);
                        }else{
                            TitleMain.sendMessageToConsole("指令错误 输/ch help 查看帮助");
                        }
                    }
                    break;
                case "add":
                case "give":
                    if(commandSender.isOp()){
                        if(strings.length > 3){
                            String player = strings[1];
                            Player p = Server.getInstance().getPlayer(player);
                            if(p != null){
                                player = p.getName();
                            }
                            PlayerData manager = TitleMain.playerManager.getData(player);
                            PlayerData.TitleData titleData = new PlayerData.TitleData(strings[2],Integer.parseInt(strings[3]),null,0);
                            manager.addTitle(titleData);

                            TitleMain.sendMessageToObject("&a成功给予玩家称号:&r "+strings[2]+" 有效期: &7"+  ((titleData.outTime == null || "".equalsIgnoreCase(titleData.outTime))?"永久":titleData.outTime),commandSender);

                        }else{
                            TitleMain.sendMessageToObject("指令错误 输/ch help 查看帮助",commandSender);
                        }
                    }else{
                        TitleMain.sendMessageToObject("指令错误 输/ch help 查看帮助",commandSender);
                    }
                    break;
                case "reload":
                    if(commandSender.isOp()){

                        TitleMain.getTitleMain().initConfig();
                        TitleMain.sendMessageToObject("&a重载配置完成",commandSender);
                    }else{
                        TitleMain.sendMessageToObject("指令错误 输/ch help 查看帮助",commandSender);
                    }
                    break;
                case "remove":
                    if(commandSender.isOp()){
                        if(strings.length > 2){
                            String playerName = strings[1];
                            Player p = Server.getInstance().getPlayer(playerName);
                            if(p != null){
                                playerName = p.getName();
                            }
                            PlayerData manager = TitleMain.playerManager.getData(playerName);
                            if(manager.removeTitle(strings[2])){
                                TitleMain.sendMessageToObject("&a成功移除玩家 &7"+playerName+" &a称号 &r"+strings[2],commandSender);
                            }else{
                                TitleMain.sendMessageToObject("&c玩家 &7"+playerName+" &c不存在称号 &r"+strings[2],commandSender);
                            }

                        }else{
                            TitleMain.sendMessageToObject("指令错误 输/ch help 查看帮助",commandSender);
                        }
                    }else{
                        TitleMain.sendMessageToObject("指令错误 输/ch help 查看帮助",commandSender);
                    }
                    break;
                default:

                    TitleMain.sendMessageToObject("指令错误 输/ch help 查看帮助",commandSender);
                    break;
            }
        }

        return false;
    }
}
