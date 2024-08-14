package org.sobadfish.title.manager;

import org.sobadfish.title.data.TitleData;
import org.sobadfish.title.data.TitleLibData;

import java.io.File;
import java.util.List;

/**
 * @author Sobadfish
 * @date 2024/1/8
 */
public class TitleLibNoSqlManager extends BaseDataWriterGetterManager<TitleLibData> implements ITitleLibDataManager{


    public TitleLibNoSqlManager(List<TitleLibData> dataList, File file) {
        super(dataList, file);
    }

    public static TitleLibNoSqlManager asFile(File file){
        return (TitleLibNoSqlManager) BaseDataWriterGetterManager.asFile(file,"titleLib.json", TitleLibData[].class,TitleLibNoSqlManager.class);
    }

    @Override
    public void addTitleLibData(TitleLibData titleLibData) {
        titleLibData.lid = dataList.stream()
                .mapToInt(data -> data.lid)
                .max()
                .orElse(1) +1;
        dataList.add(titleLibData);
    }

    @Override
    public void removeTitleLibData(int titleLibData) {
        dataList.removeIf(titleLibData1 -> titleLibData1.lid == titleLibData);
    }

    @Override
    public TitleLibData getTitleLibData(int id) {
        return dataList.stream()
                .filter(titleLibData -> titleLibData.lid == id)
                .findFirst()
                .orElse(null);

    }

    @Override
    public TitleData getTitleData(int id,int outTime) {
        TitleLibData data = getTitleLibData(id);
        if(data != null){
            return new TitleData(data.name,outTime,data.cmd,data.delay);
        }
        return null;
    }
}
