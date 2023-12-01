package org.sobadfish.title.utils;

import org.sobadfish.title.TitleMain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.SplittableRandom;

/**
 * @author Sobadfish
 * @date 2022/9/15
 */
public class Tools {

    private static final SplittableRandom RANDOM = new SplittableRandom(System.currentTimeMillis());

    public static int rand(int min, int max) {
        return min == max ? max : RANDOM.nextInt(max + 1 - min) + min;
    }

    public static double rand(double min, double max) {
        return min == max ? max : min + Math.random() * (max - min);
    }

    public static float rand(float min, float max) {
        return min == max ? max : min + (float) Math.random() * (max - min);
    }

    public static boolean rand() {
        return RANDOM.nextBoolean();
    }
    /**
     * 计算时间
     * @param sec 秒
     * @return 计算后的时间
     * */
    public static String mathTime(int sec){
        LocalDateTime ldt = LocalDateTime.now().plusSeconds(sec);
        Date date = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    /**
     * 当前时间之前的时间与当前时间相差多少秒
     * @param startDate 当前时间之前的时间
     * @return 秒
     */
    public static int calLastedTime(String startDate) {

        long nowDate = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long t;
        try {
            t = sdf.parse(startDate).getTime();
        } catch (ParseException ignore) {
            return -1;
        }
        return (int) ((nowDate - t) / 1000);

    }

    /**
     * 判断是否到期
     * @param date 时间
     * @return 是否超时
     * */
    public static boolean isOut(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            if(date == null){
                return false;
            }
            Date d = sdf.parse(date);
            if(d.getTime() < System.currentTimeMillis()){
                return true;
            }
        } catch (ParseException ignore) {

        }
        return false;
    }

    public static String timeToString(int time){
        int days = time / (60 * 60 * 24);
        int hours = (time - days * 3600 * 24) / (60 * 60);
        int minutes = (time - days * 3600 * 24 - hours * 3600) / 60;
        int second = (time - days * 3600 * 24 - hours * 3600 - minutes * 60);
        String s = "";
        if(days > 0){
            s += days+" 天";
        }
        if(hours > 0){
            s += hours+" 小时";
        }
        if(minutes > 0){
            s += minutes+" 分钟";
        }
        if(second > 0){
            s += second+" 秒";
        }

        return s;
    }

    public static TitleMain.UiType loadUiTypeByName(String name) {
        TitleMain.UiType type = TitleMain.UiType.AUTO;
        try {
            type = TitleMain.UiType.valueOf(name.toUpperCase());
        }catch (Exception ignore){}

        return type;
    }
}
