package org.sobadfish.title.manager;

import com.smallaswater.easysql.v3.mysql.manager.SqlManager;
import org.sobadfish.title.data.TitleData;
import org.sobadfish.title.data.TitleLibData;

import java.util.ArrayList;
import java.util.List;


/**
 * @author Sobadfish
 * @date 2024/1/8
 */
public class TitleLibManager implements ITitleLibDataManager {

    public SqlManager sql;

    public TitleLibManager(SqlManager sql){
        this.sql = sql;
    }

    //本地缓存..
    public List<TitleLibData> dataList = new ArrayList<>();


    @Override
    public void addTitleLibData(TitleLibData titleLibData) {



    }

    @Override
    public void removeTitleLibData(int titleLibData) {

    }

    @Override
    public TitleLibData getTitleLibData(int id) {
        return null;

    }

    @Override
    public TitleData getTitleData(int id,int outTime) {
        return null;
    }
}
