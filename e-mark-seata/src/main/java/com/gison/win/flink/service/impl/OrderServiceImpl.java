package com.gison.win.flink.service.impl;

import com.gison.win.flink.dao.OrderDAO;
import com.gison.win.flink.model.SeataOrder;
import com.gison.win.flink.service.AccountService;
import com.gison.win.flink.service.OrderService;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/12/15 20:04
 */
public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO;
    private AccountService accountService;
    @Override
    public SeataOrder create(String userId, String commodityCode, int orderCount) {
        int orderMoney = calculate(commodityCode,orderCount);
        accountService.debit(userId,orderMoney);
        SeataOrder seataOrder = new SeataOrder();
        seataOrder.setUserId(userId);
        seataOrder.setCommodityCode(commodityCode);
        seataOrder.setMoney(orderMoney);
        seataOrder.setCount(orderCount);
        return orderDAO.insert(seataOrder);
    }

    private int calculate(String commodityCode, int orderCount) {
        return 0;
    }
}
