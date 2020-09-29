package com.gisonwin.map;

import com.gisonwin.entity.CarrierInfo;
import com.gisonwin.entity.YearBase;
import com.gisonwin.util.CarrierUtils;
import com.gisonwin.util.HBaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/22 9:31
 */
public class CarrierMap implements MapFunction<String, CarrierInfo> {
    @Override
    public CarrierInfo map(String value) throws Exception {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        String[] split = value.split(",");
        String userid = split[0];
        String username = split[1];
        String sex = split[2];
        String tel = split[3];
        String eamil = split[4];
        String age = split[5];
        String registerTime = split[6];
        String registFrom = split[7]; //终端类型 0 pc 1 mobile 2 小程序
        int carriteType = CarrierUtils.getCarrierByTel(tel);
        String carritetypestr = carriteType==0?"未知运营商":carriteType ==1?"中国电信":carriteType==2?"中国联通":"中国移动";
        String tablename = "userflaginfo";
        String rowkey = userid;
        String familyname = "baseinfo";
        String colum = "carrierinfo"; //年代
        HBaseUtils.putdata(tablename, rowkey, familyname, colum, carritetypestr);
        CarrierInfo carrierInfo = new CarrierInfo();
        carrierInfo.setCarrier(carritetypestr);
        carrierInfo.setGroupfield(carritetypestr);
        carrierInfo.setCount(1L);
        return carrierInfo;
    }
}
