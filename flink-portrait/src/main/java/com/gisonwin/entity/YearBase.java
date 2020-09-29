package com.gisonwin.entity;

import lombok.*;

/**
 *   年代:40年代,50 60 70 8090 00后 10后.
 *   统计每个年代群里的数量,做到近实时统计,每半小时进行一次任务统计.
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/21 10:52
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class YearBase {
    private String yeartype;
    private long count;
    private String groupfield;
}
