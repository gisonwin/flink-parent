package com.gison.win.flink.service;

import com.gison.win.flink.model.SeataOrder;

public interface OrderService {
    /****
     * create the order .
     * @param userId
     * @param commodityCode
     * @param orderCount
     * @return
     */
    SeataOrder create(String userId, String commodityCode, int orderCount);
}
