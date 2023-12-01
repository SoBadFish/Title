package org.sobadfish.title.panel.from;

import cn.nukkit.Player;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.utils.TextFormat;
import org.sobadfish.title.panel.BasePlayPanelItemInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SoBadFish
 * 2022/1/12
 */
public class TitleFrom {

    private final int id;

    private List<BasePlayPanelItemInstance> buttons = new ArrayList<>();

    private final String title;

    private final String context;
    public TitleFrom(String title, String context, int id){
        this.title = title;
        this.context = context;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public List<BasePlayPanelItemInstance> getBaseButtons() {
        return buttons;
    }

    public void setBaseButtons(List<BasePlayPanelItemInstance> buttons) {
        this.buttons = buttons;
    }

    public void add(BasePlayPanelItemInstance button){
        buttons.add(button);
    }

    public void disPlay(Player player){
        FormWindowSimple simple = new FormWindowSimple(TextFormat.colorize('&',title),TextFormat.colorize('&', context));
        for(BasePlayPanelItemInstance button: buttons){
            simple.addButton(button.getButton(player));
        }
        player.showFormWindow(simple, getId());
    }

    @Override
    public String toString() {
        return id+" -> "+buttons;
    }
}
