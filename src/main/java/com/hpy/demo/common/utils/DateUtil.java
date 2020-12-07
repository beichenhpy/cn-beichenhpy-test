package com.hpy.demo.common.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author A51398
 * @version 1.0
 * @description TODO
 * @since 2020/12/2 13:54
 */
public class DateUtil {
    /**
     * 获取某月的所有日期
     * @param date 日期
     * @return 所有日期列表
     */
    public List<String> getMonthFullDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        List<String> fullDayList = new ArrayList<>();
        int year = Integer.parseInt(dateStr.substring(0, 4));
        int month = Integer.parseInt(dateStr.substring(5, 7));
        // 所有月份从1号开始
        int day = 1;
        // 获得当前日期对象
        Calendar cal = Calendar.getInstance();
        // 清除信息
        cal.clear();
        cal.set(Calendar.YEAR, year);
        // 1月从0开始
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //计算本月最后一天，这里需要获得一个新实例，因为下面循环获取日期需要上面的开始时间实例
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.YEAR, year);
        cal1.set(Calendar.MONTH, month);
        cal1.set(Calendar.DAY_OF_MONTH, 0);
        String lastDay = sdf.format(cal1.getTime());
        for (int j = 0; j <= (count - 1); ) {
            if (sdf.format(cal.getTime()).equals(lastDay)) {
                break;
            }
            cal.add(Calendar.DAY_OF_MONTH, j == 0 ? +0 : +1);
            j++;
            fullDayList.add(sdf.format(cal.getTime()));
        }
        return fullDayList;
    }
}
