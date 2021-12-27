package com.binhao.drive.common.util;/*
 * @Author: zeng
 * @Data: 2021/11/26 17:03
 * @Description: TODO
 */

import java.util.Calendar;
import java.util.Date;

public class NewYearUtil {

    public static Date getNextYearNow(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.YEAR, +1);
        return c.getTime();
    }
}
