package org.sobadfish.title.data;

/**
 * @author Sobadfish
 * @date 2022/9/15
 */
public class ShopData extends TitleData {

    public double money;

    public int time;

    public ShopData(String name, String cmd, int delay,int time,double money) {
        super(name, null, cmd, delay);
        this.money = money;
        this.time = time;
    }



    public TitleData asTitleData(){
        return new TitleData(name,time,cmd,delay);
    }
}
