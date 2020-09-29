package com.gisonwin.reduce;

import com.gisonwin.entity.CarrierInfo;
import com.gisonwin.entity.YearBase;
import org.apache.flink.api.common.functions.ReduceFunction;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/21 13:59
 */
public class CarrierReduce implements ReduceFunction<CarrierInfo> {
    @Override
    public CarrierInfo reduce(CarrierInfo value1, CarrierInfo value2) throws Exception {
        String carrier = value1.getCarrier();
        long count1 = value1.getCount();

        long count2 = value2.getCount();
        CarrierInfo carrierInfo = new CarrierInfo();

        carrierInfo.setCarrier(carrier);
        carrierInfo.setCount(count1 + count2);
        return carrierInfo;
    }
}
