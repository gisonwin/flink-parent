package com.gisonwin.task;

import com.gisonwin.entity.YearBase;
import com.gisonwin.map.YearBaseMap;
import com.gisonwin.reduce.YearBaseReduce;
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
public class YearBaseTask {
    public static void main(String[] args) {
        ParameterTool params = ParameterTool.fromArgs(args);
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataSource<String> text = env.readTextFile(params.get("input"));

        MapOperator<String, YearBase> mapresult = text.map(new YearBaseMap());
        ReduceOperator<YearBase> reduceResult = mapresult.groupBy("groupfield").reduce(new YearBaseReduce());
        try {
            List<YearBase> resultList = reduceResult.collect();
            for (YearBase yearBase : resultList) {
                String yeartype = yearBase.getYeartype();
                long count = yearBase.getCount();
                Document doc = MongoUtils.findoneby("yearbasestatics", "portrait", yeartype);
                if (doc == null) {
                    doc = new Document();
                    doc.put("info", yeartype);
                    doc.put("count", count);
                } else {
                    Long cnt = doc.getLong("count");
                    doc.put("count", count + cnt);
                }
                MongoUtils.saveorupdatemongo("yearbasestatics", "portrait", doc);
                env.execute("year base ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




}
