package com.gisonwin.log;

import lombok.*;

import java.io.Serializable;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/22 21:32
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ScanProductLog implements Serializable {
    private int productid;//商品id
    private int producttypeid;//商品类别id
    private String scantime;//浏览时间
    private String staytime;//停留时间
    private int userid;//用户id
    private String usertype;//终端类型 1pc 2 mobile3 小程序
    private String userip;//用户ip
}
