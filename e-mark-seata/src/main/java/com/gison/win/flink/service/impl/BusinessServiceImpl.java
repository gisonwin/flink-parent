package com.gison.win.flink.service.impl;

import com.gison.win.flink.service.BusinessService;
import com.gison.win.flink.service.OrderService;
import com.gison.win.flink.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/12/15 20:02
 */
public class BusinessServiceImpl implements BusinessService {
    private OrderService orderService;
    private StorageService storageService;
    @GlobalTransactional
    @Override
    public void purchase(String userId, String commodityCode, int orderCount) {
        storageService.deduct(commodityCode,orderCount);
        orderService.create(userId,commodityCode,orderCount);
    }
}
