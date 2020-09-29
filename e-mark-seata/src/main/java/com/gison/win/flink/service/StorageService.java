package com.gison.win.flink.service;

/**
 * @author <a href="mailto:gisonwin@qq.com">GisonWin</a>
 * @date 2019/12/15 19:56
 */
public interface StorageService {
    /***
     * 扣除存储数量.
     * @param commodityCode
     * @param count
     */
    void deduct(String commodityCode,int count);
}
