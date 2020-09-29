package com.gisonwin.kmeans;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/24 18:16
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Slf4j
public class KMeansRun {
    private int kNum; //簇的个数
    private int iterNum =10;//迭代次数

    private int iterMaxTimes = 100000;//单次迭代最大运行次数
    private int iterRuntimes = 0;//单次迭代实际运行次数
    private float disDiff=0.01f;//单次迭代终止条件,两次运行中类
    private List<float[]> original_data = null;//存放原始数据集
    private static List<Point> ponitList = null;//存储原始数据集所构建的点集合
    private DistanceCompute disC = new DistanceCompute();
    private int len = 0; //用于记录每个数据点的维度

    public KMeansRun(int k ,List<float[]> origial_data){
        this.kNum = k;
        this.original_data = origial_data;
        this.len = original_data.get(0).length;
        //check rules
        check();
        //init point cluster
        init();
    }

    private void check() {
        if (kNum == 0) {
            throw new IllegalArgumentException("k must be the number > 0 ");
        }
        if (original_data == null) {
            throw new IllegalArgumentException("program can not get real data");
        }
    }

    private void init() {
        ponitList = new ArrayList<Point>();
        for (int i =0,j= original_data.size();i <j ;i++) {
            ponitList.add(new Point(i,original_data.get(i)));
        }
    }

    public Set<Cluster> run(){
        Set<Cluster> clusterSet = chooseCenterCluster();
        boolean needIter = true;
        while (needIter) {
            cluster(clusterSet);
            needIter = calculateCenter(clusterSet);
            iterRuntimes++;
        }
        return clusterSet;
    }

    private boolean calculateCenter(Set<Cluster> clusterSet) {
        //计算每个类的中心位置
        boolean needIter = false;
        for (Cluster cluster : clusterSet) {
            List<Point> points = cluster.getMembers();
            float[] sumAll = new float[len];
            //所有点,对应各个 维度进行求和
            for (int i = 0; i < len; i++) {
                for (int j = 0; j < ponitList.size(); j++) {
                    sumAll[i]+=ponitList.get(j).getLocalArray()[i];
                }
            }
            //求平均值
            for (int i = 0; i < sumAll.length; i++) {
                sumAll[i] =(float) sumAll[i]/ponitList.size();
            }
            //计算两个新旧中心的距离,如果任意一个类中心移动的距离大于dis_diff则继续迭代
            if (disC.getEuclideanDis(cluster.getPoint(),new Point(-1,sumAll)) > disDiff) {
                needIter = true;
            }
            //设置新的中心类位置
            cluster.setPoint(new Point(-1,sumAll));
        }
        return needIter;
    }

    private void cluster(Set<Cluster> clusterSet) {
        //计算每个点到K个中心点的距离,并且为每个点标记类别号.
        for (Point point : ponitList) {
            float min_dis = Integer.MAX_VALUE;
            for (Cluster cluster : clusterSet) {
                float tmp_dis = (float) Math.min(disC.getEuclideanDis(point, cluster.getPoint()), min_dis);
                if (tmp_dis!= min_dis) {
                    min_dis = tmp_dis;
                    point.setClusterid(cluster.getId());
                    point.setDist(min_dis);
                }
            }
        }
        //新清除原来所有的类中成员,把所有的点,分别加入 每个类别.
        for (Cluster cluster : clusterSet) {
            cluster.getMembers().clear();
            for (Point point : ponitList) {
                if (point.getClusterid() == cluster.getId()) {
                    cluster.addPoint(point);
                }
            }
        }
        //
    }

    private Set<Cluster> chooseCenterCluster() {
        Set<Cluster> clusterSet = new HashSet<Cluster>();
        Random random = new Random();
        for (int i = 0; i < kNum;) {
            Point point = ponitList.get(random.nextInt(ponitList.size()));
            boolean flag = true;//用户标记是否已经选择过该数据.
            for (Cluster cluster : clusterSet) {
                //如果选中过就不能选择了.
                if (cluster.getPoint().equals(point)) {
                    flag = false;
                }
            }
                //如果随机选取的点没有被选中,则生成一个cluster.
                if(flag){
                    Cluster cluster1 = new Cluster(i, point);
                    clusterSet.add(cluster1);
                    i++;
                }

        }
        return clusterSet;
    }
}
