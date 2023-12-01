package org.sobadfish.title.variables;

import cn.nukkit.Player;
import cn.nukkit.utils.TextFormat;
import org.sobadfish.title.TitleMain;
import org.sobadfish.title.data.PlayerData;
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
        PlayerData.TitleData data1 = data.wearTitle;
        String title = TitleMain.getEmptyTitle();
        if(data1 != null){
            title = data1.name;
        }
        addStrReplaceString("{?Title}", TextFormat.colorize('&',title));
    }
}
