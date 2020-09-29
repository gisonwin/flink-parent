package com.gison.win.flink.dao;

import com.gison.win.flink.model.SeataOrder;

public interface OrderDAO {
    /***
     * insert into orders.
     * @param seataOrder
     * @return
     */
    SeataOrder insert(SeataOrder seataOrder);

}
