package com.gisonwin.kmeans;

import com.gisonwin.entity.CarrierInfo;
import org.apache.flink.api.common.functions.GroupReduceFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.util.Collector;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/21 13:59
 */
public class KMeansReduce implements GroupReduceFunction<KMeansInfo, ArrayList<Double>> {
    @Override
    public void reduce(Iterable<KMeansInfo> iterable, Collector<ArrayList<Double>> collector) throws Exception {
        Iterator<KMeansInfo> iterator = iterable.iterator();
//        CreateDataSet createDataSet = new CreateDataSet();
        while (iterator.hasNext()) {
            KMeansInfo next = iterator.next();
            String var1 = next.getVariable1();
            String var2 = next.getVariable2();
            String var3 = next.getVariable3();
            String groupbyfield = next.getGroupbyfield();

            ArrayList<String> as = new ArrayList<>();
            as.add(var1);
            as.add(var2);
            as.add(var3);

//            createDataSet.data.add(as);
//            createDataSet.labels.add(label);

        }
        ArrayList<Double> weights = new ArrayList<>();
//        weights = Logistic.gradAscent1(createDataSet,createDataSet.lables,500);
        collector.collect(weights);
    }
}
