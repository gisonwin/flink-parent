package com.gisonwin.task;

import com.gisonwin.entity.CarrierInfo;
import com.gisonwin.entity.SpendthriftInfo;
import com.gisonwin.map.CarrierMap;
import com.gisonwin.map.SpendthriftMap;
import com.gisonwin.reduce.CarrierReduce;
import com.gisonwin.reduce.SpendthriftReduce;
import com.gisonwin.util.DateUtils;
import com.gisonwin.util.HBaseUtils;
import com.gisonwin.util.MongoUtils;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.api.java.operators.ReduceOperator;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/21 13:54
 */
public class SpendthriftTask {
    public static void main(String[] args) {
        ParameterTool params = ParameterTool.fromArgs(args);
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);
        //近一年的数据.
        DataSource<String> text = env.readTextFile(params.get("input"));

        MapOperator<String, SpendthriftInfo> mapresult = text.map(new SpendthriftMap());
        ReduceOperator<SpendthriftInfo> reduceResult = mapresult.groupBy("groupfield").reduce(new SpendthriftReduce());
        try {
            List<SpendthriftInfo> resultList = reduceResult.collect();
            for (SpendthriftInfo spendthriftInfo : resultList) {
                String userid = spendthriftInfo.getUserid();
                List<SpendthriftInfo> infoList = spendthriftInfo.getInfoList();
                Collections.sort(infoList, new Comparator<SpendthriftInfo>() {
                    @Override
                    public int compare(SpendthriftInfo o1, SpendthriftInfo o2) {
                        return o1.getCreatetime().compareTo(o2.getCreatetime());
                    }
                });
                SpendthriftInfo before = null;
                Map<Integer, Integer> frequencymap = new HashMap<Integer, Integer>();
                double maxamount = 0d;
                double sum = 0d;
                //loop infolist
                for (SpendthriftInfo info : infoList) {
                    if (before == null) {
                        before = info;
                        continue;
                    }
                    //计算购买的频率
                    String beforetime = before.getCreatetime();
                    String endtime = info.getCreatetime();
                    int days = (int) DateUtils.getDaysBetween(beforetime, endtime);
                    int i = frequencymap.get(days) == null ? 0 : frequencymap.get(days);
                    frequencymap.put(days, i + 1);
                    //计算最大金额
                    String totalamount = info.getTotalamount();
                    Double aDouble = Double.valueOf(totalamount);
                    if (aDouble > maxamount) {
                        maxamount = aDouble; //最大金额
                    }
                //计算平均值
                    sum += aDouble;
                    before = info;

                }
                double avramount = sum/infoList.size();//平均支付金额
                int totaldays = 0;
                Set<Map.Entry<Integer, Integer>> set = frequencymap.entrySet();
                for (Map.Entry<Integer, Integer> entry : set) {
                    Integer key = entry.getKey();
                    Integer value = entry.getValue();
                    totaldays+= key*value;
                }
                int avrdays = totaldays / infoList.size();//平均天数

                double totalscore = maxamount * 0.3 + avrdays * 0.4 + avramount * 0.3;

                HBaseUtils.putdata("userflaginfo",userid,"baseinfo","spredthritscore",String.valueOf(totalscore));

                env.execute("spendthrift task ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
