package com.ns.ejk.form.bean;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Entity
public class FormData {
    @Id
    @GeneratedValue
    private Long id;
    private Long schemaId;
    private String value;
    @Column(insertable = false, updatable = false)
    private Date createTime;
    @Column(insertable = false, updatable = false)
    private Date updateTime;

    @Transient
    private String schemaName;
    @Transient
    private Set<DataItem> dataItems;
    @Transient
    private Map<String, Object> dataItemMap;

    public FormData() {
    }

    public FormData(String schemaName, Map<String, Object> dataItemMap) {
        this.schemaName = schemaName;
        this.dataItemMap = dataItemMap;
    }

    public FormData(long id, Map<String, Object> dataItemMap) {
        this.id = id;
        this.dataItemMap = dataItemMap;
    }

    public FormData(long id, String schemaName, Map<String, Object> dataItemMap) {
        this(id, dataItemMap);
        this.schemaName = schemaName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSchemaId() {
        return schemaId;
    }

    public void setSchemaId(Long schemaId) {
        this.schemaId = schemaId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }


    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public Set<DataItem> getDataItems() {
        return dataItems;
    }

    public void setDataItems(Set<DataItem> dataItems) {
        this.dataItems = dataItems;
    }

    public Map<String, Object> getDataItemMap() {
        return dataItemMap;
    }

    public void setDataItemMap(Map<String, Object> dataItemMap) {
        this.dataItemMap = dataItemMap;
    }
}
