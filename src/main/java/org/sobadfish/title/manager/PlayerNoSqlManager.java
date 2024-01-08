package org.sobadfish.title.manager;

import org.sobadfish.title.data.PlayerData;
import org.sobadfish.title.data.TitleData;

import java.io.File;
import java.util.List;

/**
 * @author Sobadfish
 * @date 2024/1/3
 */
public class PlayerNoSqlManager extends BaseDataWriterGetterManager<PlayerData> implements IDataManager{


    public PlayerNoSqlManager(List<PlayerData> dataList, File file) {
        super(dataList, file);
    }

    public static PlayerNoSqlManager asFile(File file){
        return (PlayerNoSqlManager) BaseDataWriterGetterManager.asFile(file,"player.json", PlayerData[].class,PlayerNoSqlManager.class);
    }


    @Override
    public List<PlayerData> getDataList() {
        return dataList;
    }

    @Override
    public PlayerData getData(String player) {
        PlayerData data = new PlayerData(player);
        if(!dataList.contains(data)){
            dataList.add(data);
        }
        return dataList.get(dataList.indexOf(data));
    }

    @Override
    public void addTitle(String player, TitleData titleData) {}

    @Override
    public void removeTitle(String player, String titleData) {}

    @Override
    public void wearTitle(String player, TitleData title) {}
}
