package com.gisonwin.map;

import com.gisonwin.entity.CarrierInfo;
import com.gisonwin.entity.SpendthriftInfo;
import com.gisonwin.util.CarrierUtils;
import com.gisonwin.util.HBaseUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.api.common.functions.MapFunction;

import java.util.ArrayList;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/22 9:31
 */
public class SpendthriftMap implements MapFunction<String, SpendthriftInfo> {
    /***
     *   `id`,
     *   `userid`,
     *   `productid`,
     *   `producttypeid`,
     *   `createtime`,
     *   `paytime`,
     *   `paytype`,
     *   `paystatus`,
     *   `payamout`,
     *   `couponamount`,
     *   `totalamount`,
     *   `refundamout`,
     *   `productsnums`
     * @param s
     * @return
     * @throws Exception
     */
    @Override
    public SpendthriftInfo map(String s) throws Exception {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        String[] orders = s.split(",");
        String id = orders[0];
        String userid = orders[1];
        String productid = orders[2];
        String producttypeid = orders[3];

        String createtime = orders[4];
        String paytime = orders[5];
        String paytype = orders[6];
        String paystatus = orders[7];
        String payamout = orders[8];
        String couponamount = orders[9];
        String totalamount = orders[10];
        String refundamout = orders[11];
        String productsnums = orders[12];


        SpendthriftInfo spendthriftInfo = new SpendthriftInfo();
        spendthriftInfo.setUserid(userid);
        spendthriftInfo.setAmout(payamout);
//        spendthriftInfo.setCount();
        spendthriftInfo.setCouponamount(couponamount);
        spendthriftInfo.setCreatetime(createtime);
        spendthriftInfo.setRefundamount(refundamout);
        spendthriftInfo.setGroupfield("spendthrift=="+userid);
        spendthriftInfo.setPaystatus(paystatus);
//        spendthriftInfo.setPaytime();
        spendthriftInfo.setPaytype(paytype);
//        spendthriftInfo.setSpendthriftScore();
        spendthriftInfo.setTotalamount(totalamount);
        ArrayList<SpendthriftInfo> spendthriftInfoArrayList = new ArrayList<SpendthriftInfo>();
        spendthriftInfoArrayList.add(spendthriftInfo);
        return spendthriftInfo;
    }
}
