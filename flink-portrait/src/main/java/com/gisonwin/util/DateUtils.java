package com.gisonwin.util;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/21 11:13
 */
public class DateUtils {

    public static String getYearbaseByAge(String age) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        instance.add(Calendar.YEAR, -Integer.valueOf(age));
        Date newdate = instance.getTime();
        String yyyy = new SimpleDateFormat("yyyy").format(newdate);
        int _age = Integer.parseInt(yyyy);
        if (_age >= 1940 && _age < 1950) {
            return "40后";
        } else if (_age >= 1950 && _age < 1960) {
            return "50后";
        } else if (_age >= 1960 && _age < 1970) {
            return "60后";
        } else if (_age >= 1970 && _age < 1980) {
            return "70后";
        } else if (_age >= 1980 && _age < 1990) {
            return "80后";
        } else if (_age >= 1990 && _age < 2000) {
            return "90后";
        } else if (_age >= 2000 && _age < 2010) {
            return "00后";
        } else if (_age >= 2010) {
            return "10后";
        }
        return "未知";
    }

    /***
     *
     * @param start
     * @param end
     * @return
     */
    public static long getSecondsBetween(String start, String end) {
        Duration between = getDuration(start, end);
        long toDays = between.toDays();
        long hours = between.toHours();
        long minutes = between.toMinutes();
        long millis = between.toMillis();
        long nanos = between.toNanos();
        return between.getSeconds();
    }

    public static long getDaysBetween(String start, String end) {
        return getDuration(start, end).toDays();
    }

    /***
     *
     * @param start
     * @param end
     * @return
     */
    private static Duration getDuration(String start, String end) {
        return getDuration(start, end, "yyyy-MM-dd HH:mm:ss");
    }

    /***
     *
     * @param start
     * @param end
     * @param pattern
     * @return
     */
    private static Duration getDuration(String start, String end, String pattern) {
        return getDuration(start, end, pattern, pattern);
    }

    /***
     *
     * @param start
     * @param end
     * @param startpattern
     * @param endpattern
     * @return
     */
    private static Duration getDuration(String start, String end, String startpattern, String endpattern) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(startpattern);
        LocalDateTime startdateTime = LocalDateTime.parse(start, df);
        DateTimeFormatter df2 = DateTimeFormatter.ofPattern(endpattern);
        LocalDateTime enddateTime = LocalDateTime.parse(end, df2);
        return Duration.between(startdateTime, enddateTime);
    }

//    public static void main(String[] args) {
//        String start="2019-11-20 13:14:00",end="2019-11-22 13:10:10";
//        Duration duration = getDuration(start, end);
//        System.out.println(duration.toDays()+" days");
//        System.out.println(duration.toHours()+" hours");
//        System.out.println(duration.getSeconds()+ "seconds ");
//    }
}
