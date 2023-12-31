package org.sobadfish.title.variables;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import org.sobadfish.title.TitleMain;
import org.sobadfish.title.data.PlayerData;
import org.sobadfish.title.data.TitleData;
import tip.utils.variables.BaseVariable;

/**
 * @author Sobadfish
 * @date 2022/9/15
 */
public class TipVariable extends BaseVariable {
    public TipVariable(Player player) {
        super(player);
    }

    @Override
    public void strReplace() {
        PlayerData data = TitleMain.playerManager.getData(player.getName());
        TitleData data1 = data.wearTitle;
        String title = TitleMain.getEmptyTitle();
        if(data1 != null){
            title = data1.name;
        }
        title = title.replace("@p",data.name);
        addStrReplaceString("{title}", TextFormat.colorize('&',title));
        addStrReplaceString("{?title}", TextFormat.colorize('&',title));
    }
}
