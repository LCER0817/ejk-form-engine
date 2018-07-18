package com.ns.ejk.form.dao;

import com.ns.common.dao.spi.jpa.JpaDao;
import com.ns.ejk.form.bean.FormSchema;

import java.util.List;

public interface FormSchemaDao extends JpaDao<FormSchema, Long> {
    FormSchema findByName(String name);

    List<FormSchema> findAll();
}
