package org.sobadfish.title.data;

import org.sobadfish.title.utils.Tools;

/**
 * @author Sobadfish
 * @date 2024/1/8
 */
public class TitleData {


    public int tid;
    /**
     * 称号名称
     * */
    public String name;

    /**
     * 称号到期时间
     * */
    public String outTime = "";

    /**
     * 称号执行指令
     * */
    public String cmd;

    /**
     * 称号执行指令延迟
     * */
    public int delay = 0;

    public TitleData(String name,String outTime,String cmd,int delay){
        this.name = name;
        this.outTime = outTime;
        this.cmd = cmd;
        this.delay = delay;
    }

    public TitleData(String name,int sec,String cmd,int delay){
        this.name = name;
        if(sec <= 0){
            this.outTime = null;
        }else{
            this.outTime = Tools.mathTime(sec);
        }

        this.cmd = cmd;
        this.delay = delay;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof TitleData){
            TitleData titleData = (TitleData) o;
            return name.equals(titleData.name);
        }
        return false;

    }

    @Override
    public String toString() {
        return "TitleData{" +
                "tid=" + tid +
                ", name='" + name + '\'' +
                ", outTime='" + outTime + '\'' +
                ", cmd='" + cmd + '\'' +
                ", delay=" + delay +
                '}';
    }

}
