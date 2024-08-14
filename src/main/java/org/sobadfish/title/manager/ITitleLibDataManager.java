package org.sobadfish.title.manager;

import org.sobadfish.title.data.TitleData;
import org.sobadfish.title.data.TitleLibData;

/**
 * @author Sobadfish
 * @date 2024/8/14
 */
public interface ITitleLibDataManager {

    void addTitleLibData(TitleLibData titleLibData);

    void removeTitleLibData(int titleLibData);

    TitleLibData getTitleLibData(int id);


    TitleData getTitleData(int id,int outTime);

}
