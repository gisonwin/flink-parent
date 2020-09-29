package com.gisonwin.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 败家指数=支付金额平均值* 0.3,最大金额*0.3,下单频率*0.4
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/11/21 10:25
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SpendthriftInfo implements Serializable {

    private String spendthriftScore;//败家指数 区段:0-20,20-50,50-70,70-80,80-90,90-100
    private String userid;//用户id
    private long count;//数量
    private String groupfield;//分组
    private String createtime;//订单创建时间
    private String amout;//金额
    private String paytype;//支付类型
    private String paytime;//支付时间
    private String paystatus;//支付状态 0 未支付 1 已支付 2 已退款
    private String couponamount;//代金券
    private String totalamount;//总金额
    private String refundamount;//退款金额

    private List<SpendthriftInfo> infoList;
}
