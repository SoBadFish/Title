package org.sobadfish.title.data;

/**
 * 这个就是Title集合库 可以在这里预设好称号后直接给予玩家
 * @author Sobadfish
 * @date 2024/1/8
 */

public class TitleLibData{

    public int lid;

    /**
     * 称号名称
     * */
    public String name;


    /**
     * 称号执行指令
     * */
    public String cmd;

    /**
     * 称号执行指令延迟
     * */
    public int delay;


    public TitleLibData(String title){
        this.name = title;
    }

    public TitleLibData(String title,String cmd){
        this.name = title;
        this.cmd = cmd;
    }

    public TitleLibData(String title,String cmd,int delay){
        this.name = title;
        this.cmd = cmd;
        this.delay = delay;
    }



}
