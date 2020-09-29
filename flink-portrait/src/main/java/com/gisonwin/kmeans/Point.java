package com.gisonwin.kmeans;

import lombok.*;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/24 17:55
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Point {
    private float[] localArray;//维度
    private int id; //
    private int clusterid; //簇id
    private float dist; //距离

    public Point(int i,float[] localArray ){
        this.id = i;
        this.localArray = localArray;
    }
}
