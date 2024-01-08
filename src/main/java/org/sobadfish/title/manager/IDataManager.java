package org.sobadfish.title.manager;

import org.sobadfish.title.data.PlayerData;
import org.sobadfish.title.data.TitleData;

import java.util.List;

/**
 * @author Sobadfish
 * @date 2024/1/3
 */
public interface IDataManager {

    PlayerData getData(String player);

    void addTitle(String player, TitleData titleData);

    void removeTitle(String player,String titleData);

    void wearTitle(String player, TitleData title);

    List<PlayerData> getDataList();
}
