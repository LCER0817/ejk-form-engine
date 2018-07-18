package com.ns.ejk.form.dao;

import com.ns.common.dao.spi.jpa.JpaDao;
import com.ns.ejk.form.bean.SchemaItem;

import java.util.List;

public interface SchemaItemDao extends JpaDao<SchemaItem, Long> {

    List<SchemaItem> getBySchemaId(long schemaId);

}
