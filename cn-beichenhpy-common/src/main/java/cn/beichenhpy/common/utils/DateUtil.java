package cn.beichenhpy.common.utils;

import java.text.DateFormat;
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
     * 日期格式化 使用threadLocal保证线程安全 默认格式为 yyyy-MM-dd 后期改成传入
     */
    private static final ThreadLocal<DateFormat> SDF = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd"));

    /**
     * 获取某月的所有日期
     *
     * @param date 日期
     * @param type 类型 true时 计算今天为止的所有日期
     * @return 所有日期列表
     */
    public static List<String> getMonthFullDay(Date date, boolean type) {
        List<String> fullDayList = new ArrayList<>();
        Calendar cal = getCurrentMonthCal(date);
        int count = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        Integer year = subToWhat(date, true);
        Integer month = subToWhat(date, false);
        String lastDay = getLastDayOfMonth(year, month);
        if (!type) {
            //如果想算到今天为止
            lastDay = SDF.get().format(date);
        }
        //遍历此月份
        for (int j = 0; j < count; j++) {
            if (!SDF.get().format(cal.getTime()).equals(lastDay)) {
                //添加日期
                cal.add(Calendar.DAY_OF_MONTH, j == 0 ? +0 : +1);
                fullDayList.add(SDF.get().format(cal.getTime()));
            }

        }
        return fullDayList;
    }

    /**
     * 获取到当月到某天为止的所有日期
     *
     * @param date 日期
     * @return 返回日期集合
     */
    public static List<String> getMonthFullDay(Date date) {
        return getMonthFullDay(date, false);
    }


    /**
     * 获取某月最后一天
     *
     * @param year  年
     * @param month 月
     * @return 返回date
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        return SDF.get().format(cal.getTime());
    }

    /**
     * 分割年月
     *
     * @param date   日期
     * @param isYear 是否要分割年
     * @return 返回年/月
     */
    public static Integer subToWhat(Date date, boolean isYear) {
        String dateStr = SDF.get().format(date);
        return isYear ? Integer.parseInt(dateStr.substring(0, 4)) : Integer.parseInt(dateStr.substring(5, 7));
    }

    /**
     * 获取传入日期对应月的对应Calendar对象
     */
    public static Calendar getCurrentMonthCal(Date date) {
        Integer year = subToWhat(date, true);
        Integer month = subToWhat(date, false);
        // 所有月份从1号开始
        int day = 1;
        // 获得当前日期对象
        Calendar cal = Calendar.getInstance();
        // 清除默认信息
        cal.clear();
        cal.set(Calendar.YEAR, year);
        // Calendar的月从0开始的 0-11
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return cal;
    }
}
