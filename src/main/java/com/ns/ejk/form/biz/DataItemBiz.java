package com.ns.ejk.form.biz;

import com.ns.ejk.form.bean.DataItem;
import com.ns.ejk.form.dao.DataItemDao;
import com.ns.ejk.form.util.checker.DataItemChecker;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DataItemBiz {
    @Resource
    private DataItemDao dao;
    @Resource
    private EntityManager em;
    @Resource
    private DataItemChecker checker;

    public DataItem getById(long id) {
        return dao.findOne(id);
    }

    public DataItem getAndLock(long id) {
        return dao.getAndLock(em, DataItem.class, id);
    }

    public List<DataItem> createBatch(Set<DataItem> dataItems) {
        checker.checkCreateBatch(dataItems);
        return dao.saves(new ArrayList<>(dataItems));
    }

    public void deleteByDataId(long dataId) {
        dao.deleteByFormDataId(dataId);
    }
}
