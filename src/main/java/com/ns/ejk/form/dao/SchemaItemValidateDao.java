package com.ns.ejk.form.dao;

import com.ns.common.dao.spi.jpa.JpaDao;
import com.ns.ejk.form.bean.SchemaItemValidate;

import java.util.List;

public interface SchemaItemValidateDao extends JpaDao<SchemaItemValidate, Long> {

    List<SchemaItemValidate> getBySchemaItemId(long schemaItemId);

}
