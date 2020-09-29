package com.gisonwin.kmeans;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/24 18:10
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        ArrayList<float[]> dataSet = new ArrayList<float[]>();
        dataSet.add(new float[]{1, 2, 3});
        dataSet.add(new float[]{3, 3, 3});
        dataSet.add(new float[]{3, 4, 4});
        dataSet.add(new float[]{5, 6, 7});
        dataSet.add(new float[]{8, 9, 8});
        dataSet.add(new float[]{5, 4, 3});
        dataSet.add(new float[]{6, 4, 2});
        dataSet.add(new float[]{3, 9, 7});
        dataSet.add(new float[]{5, 9, 8});
        dataSet.add(new float[]{4, 2, 10});
        dataSet.add(new float[]{1, 9, 12});
        dataSet.add(new float[]{7, 9, 12});
        dataSet.add(new float[]{5, 9, 5});
        dataSet.add(new float[]{7, 8, 4});
        KMeansRun run = new KMeansRun(3, dataSet);
        Set<Cluster> clusterSet = run.run();
        System.out.println("单次迭代运行次数:{}" + run.getIterRuntimes());
        for (Cluster cluster : clusterSet) {
            System.out.println(cluster.toString());
        }
    }
}
