package org.sobadfish.title.manager;

import org.sobadfish.title.data.ShopData;

import java.io.File;
import java.util.List;

/**
 * @author Sobadfish
 * @date 2022/9/15
 */
public class ShopManager extends BaseDataWriterGetterManager<ShopData> {


    public ShopManager(List<ShopData> dataList, File file) {
        super(dataList, file);
    }



    public static ShopManager asFile(File file){
        return (ShopManager) BaseDataWriterGetterManager.asFile(file,"titleShop.json", ShopData[].class,ShopManager.class);
    }
}
