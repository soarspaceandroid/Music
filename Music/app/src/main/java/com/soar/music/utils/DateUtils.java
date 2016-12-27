package com.soar.music.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gaofei on 2016/12/27.
 */
public class DateUtils {

    public static String getLength(long millis)
    {
        SimpleDateFormat sdf= new SimpleDateFormat("mm:ss");
        Date dt = new Date(millis);
        return sdf.format(dt);
    }

}
