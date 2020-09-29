package com.gisonwin.kmeans;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/24 17:58
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Slf4j
public class Cluster {
    private int id;//唯一标识
    private Point point;//中心
    private List<Point> members = new ArrayList<Point>();//成员

    public Cluster(int id,Point point){
        this.id = id;
        this.point = point;
    }
    public void addPoint(Point newPoint) {
        if (!members.contains(newPoint)) {
            members.add(newPoint);
        } else {
            log.debug("样本数据点{}已经存在了", newPoint.toString().trim());
        }
    }
}
