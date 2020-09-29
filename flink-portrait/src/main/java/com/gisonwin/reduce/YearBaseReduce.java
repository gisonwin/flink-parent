package com.gisonwin.reduce;

import com.gisonwin.entity.YearBase;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/21 13:59
 */
public class YearBaseReduce implements ReduceFunction<YearBase> {
    @Override
    public YearBase reduce(YearBase value1, YearBase value2) throws Exception {
        String yeartype = value1.getYeartype();
        long count1 = value1.getCount();

        long count2 = value2.getCount();
        YearBase finalYearBase = new YearBase();

        finalYearBase.setYeartype(yeartype);
        finalYearBase.setCount(count1 + count2);
        return finalYearBase;
    }
}
