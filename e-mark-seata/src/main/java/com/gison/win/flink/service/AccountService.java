package com.gison.win.flink.service;

public interface AccountService {
    /***
     * 从用户帐户中借出.
     * @param userId
     * @param money
     */
    void debit(String userId,int money);
}
