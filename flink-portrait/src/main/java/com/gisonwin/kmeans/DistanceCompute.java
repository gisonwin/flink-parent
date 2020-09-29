package com.gisonwin.kmeans;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/24 18:02
 */
public class DistanceCompute {
    /***
     * 欧式距离计算.
     * @param p1
     * @param p2
     * @return
     */
    public double getEuclideanDis(Point p1,Point p2){
        double count_dis = 0d;
        float[] p1_local_array = p1.getLocalArray();
        float[] p2_local_array = p2.getLocalArray();
        if (p1_local_array.length != p2_local_array.length) {
            throw new IllegalArgumentException("length fo array must be equal!");
        }
        for (int i = 0; i < p1_local_array.length; i++) {
            count_dis += Math.pow(p1_local_array[i]-p2_local_array[i],2);
        }

        return Math.sqrt(count_dis);
    }
}
