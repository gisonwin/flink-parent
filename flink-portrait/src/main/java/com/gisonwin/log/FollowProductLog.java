package com.gisonwin.log;

import lombok.*;

import java.io.Serializable;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/22 22:10
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FollowProductLog implements Serializable {
    private int productid;//商品id
    private int producttypeid;//商品类别id
    private String operatetime;//操作时间
    private int operatortype;//操作类型0 关注  1 取消
    private int userid;//用户id
    private String usertype;//终端类型 1pc 2 mobile3 小程序
    private String userip;//用户ip

}
