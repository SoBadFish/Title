package org.sobadfish.title.thread;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.scheduler.PluginTask;
import org.sobadfish.title.TitleMain;
import org.sobadfish.title.data.PlayerData;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Sobadfish
 * @date 2022/9/15
 */
public class TitleLoadTask extends PluginTask<TitleMain> {

    public Map<PlayerData,Long> loadTime = new LinkedHashMap<>();

    public TitleLoadTask(TitleMain titleMain) {
        super(titleMain);
    }

    @Override
    public void onRun(int i) {
        for(PlayerData playerData: TitleMain.playerManager.dataList){
            playerData.deleteTimeOut();
            if(!loadTime.containsKey(playerData)){
                loadTime.put(playerData,System.currentTimeMillis());
            }
            if(playerData.wearTitle != null
                    && playerData.wearTitle.delay > 0
                    &&( playerData.wearTitle.cmd != null
                    && !"".equalsIgnoreCase(playerData.wearTitle.cmd))){
                PlayerData.TitleData titleData = playerData.wearTitle;
                Player player = Server.getInstance().getPlayer(titleData.name);
                if(player != null) {
                    if (System.currentTimeMillis() - loadTime.get(playerData) >= playerData.wearTitle.delay * 1000L) {
                        Server.getInstance().getCommandMap().dispatch(new ConsoleCommandSender(),
                                titleData.cmd.replace("@p", "'"+player.getName()+"'"));
                        loadTime.put(playerData,System.currentTimeMillis());
                    }
                }
            }
        }
    }
}
