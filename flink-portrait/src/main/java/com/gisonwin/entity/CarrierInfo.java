package com.gisonwin.entity;

import lombok.*;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/22 9:31
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarrierInfo {
    private String carrier;//运营商
    private long count;//数量
    private String groupfield;//分组
}
