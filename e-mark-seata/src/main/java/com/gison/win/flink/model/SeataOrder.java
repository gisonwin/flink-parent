package com.gison.win.flink.model;

import lombok.*;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/12/15 19:59
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SeataOrder {
    private String userId;
    private String commodityCode;
    private int count;
    private int money;
}
