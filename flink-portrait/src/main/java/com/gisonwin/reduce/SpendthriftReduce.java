package com.gisonwin.reduce;

import com.gisonwin.entity.CarrierInfo;
import com.gisonwin.entity.SpendthriftInfo;
import org.apache.flink.api.common.functions.ReduceFunction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/21 13:59
 */
public class SpendthriftReduce implements ReduceFunction<SpendthriftInfo> {
    @Override
    public SpendthriftInfo reduce(SpendthriftInfo value1, SpendthriftInfo value2) throws Exception {
        String userid = value1.getUserid();
        List<SpendthriftInfo> infoList1 = value1.getInfoList();
        List<SpendthriftInfo> infoList2 = value2.getInfoList();

        List<SpendthriftInfo> finalList = new ArrayList<SpendthriftInfo>();
        finalList.addAll(infoList1);
        finalList.addAll(infoList2);

        SpendthriftInfo spendthriftInfo = new SpendthriftInfo();
        spendthriftInfo.setUserid(userid);
        spendthriftInfo.setInfoList(finalList);
        return spendthriftInfo;
    }
}
