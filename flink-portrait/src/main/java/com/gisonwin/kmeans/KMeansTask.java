package com.gisonwin.kmeans;

import com.gisonwin.entity.CarrierInfo;
import com.gisonwin.map.CarrierMap;
import com.gisonwin.reduce.CarrierReduce;
import com.gisonwin.util.MongoUtils;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.operators.MapOperator;
import org.apache.flink.api.java.operators.ReduceOperator;
import org.apache.flink.api.java.utils.ParameterTool;
import org.bson.Document;

import java.util.List;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/21 13:54
 */
public class KMeansTask {
    public static void main(String[] args) {
        ParameterTool params = ParameterTool.fromArgs(args);
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataSource<String> text = env.readTextFile(params.get("input"));

        MapOperator<String, KMeansInfo> mapresult = text.map(new KMeansMap());
        ReduceOperator<CarrierInfo> reduceResult = mapresult.groupBy("groupfield").reduce(new CarrierReduce());
        try {
            List<CarrierInfo> resultList = reduceResult.collect();
            for (CarrierInfo yearBase : resultList) {
                String carrier = yearBase.getCarrier();
                long count = yearBase.getCount();
                Document doc = MongoUtils.findoneby("carrierstatics", "portrait", carrier);
                if (doc == null) {
                    doc = new Document();
                    doc.put("info", carrier);
                    doc.put("count", count);
                } else {
                    Long cnt = doc.getLong("count");
                    doc.put("count", count + cnt);
                }
                MongoUtils.saveorupdatemongo("carrierstatics", "portrait", doc);
                env.execute("carrier task ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
