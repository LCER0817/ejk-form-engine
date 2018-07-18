package com.ns.ejk.form.dao;

import com.ns.common.dao.spi.jpa.JpaDao;
import com.ns.ejk.form.bean.DataItem;

public interface DataItemDao extends JpaDao<DataItem, Long> {

    void deleteByFormDataId(long formDataId);
}
