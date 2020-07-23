package com.jiading.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: bbs
 * @description: 将date类型转换为统一的String对象以便于存入数据库
 * @author: JiaDing
 * @create: 2020-07-18 16:24
 **/
public class TimeUtil {
    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getStringTimeNow() {
        return df.format(new Date());
    }

    public static String getStringTime(Date date) {
        return df.format(date);
    }

}
