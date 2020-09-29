package com.gison.win.flink.service;

public interface BusinessService {
    /***
     * 采购.
     * @param userId
     * @param commodityCode
     * @param orderCount
     */
    void purchase(String userId,String commodityCode,int orderCount);
}
