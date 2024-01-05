package org.sobadfish.title.manager;

import org.sobadfish.title.data.PlayerData;

import java.util.List;

/**
 * @author Sobadfish
 * @date 2024/1/3
 */
public interface IDataManager {

    PlayerData getData(String player);

    void addTitle(String player,PlayerData.TitleData titleData);

    void removeTitle(String player,String titleData);

    void wearTitle(String player, PlayerData.TitleData title);

    List<PlayerData> getDataList();
}
