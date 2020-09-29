package com.gisonwin.map;

import com.gisonwin.entity.YearBase;
import com.gisonwin.util.DateUtils;
import com.gisonwin.util.HBaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/21 10:55
 */
public class YearBaseMap implements MapFunction<String, YearBase> {
    /***
     *   `userid`,
     *   `username`,
     *   `password`,
     *   `sex`,
     *   `msisdn`,
     *   `email`,
     *   `age`,
     *   `registTime`,
     *   `registFrom`
     */
    @Override
    public YearBase map(String value) throws Exception {

        if (StringUtils.isBlank(value)) {
            return null;
        }
        String[] split = value.split(",");
        String userid = split[0];
        String username = split[1];
        String sex = split[2];
        String telphone = split[3];
        String eamil = split[4];
        String age = split[5];
        String registerTime = split[6];
        String registFrom = split[7]; //终端类型 0 pc 1 mobile 2 小程序

        String yearbaseByAge = DateUtils.getYearbaseByAge(age);
        String tablename = "userflaginfo";
        String rowkey = userid;
        String familyname = "baseinfo";
        String colum = "yearbase"; //年代
        HBaseUtils.putdata(tablename, rowkey, familyname, colum, yearbaseByAge);
        YearBase yearBase = new YearBase();
        yearBase.setYeartype(yearbaseByAge);
        yearBase.setGroupfield("yearbase==" + yearbaseByAge);
        yearBase.setCount(1L);
        return yearBase;
    }
}
