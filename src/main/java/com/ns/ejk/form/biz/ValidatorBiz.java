package com.ns.ejk.form.biz;

import com.ns.common.util.exception.sys.NSException;
import com.ns.common.util.exception.sys.ParameterException;
import com.ns.common.util.spring.SpringUtil;
import com.ns.ejk.form.bean.*;
import com.ns.ejk.form.util.constant.SchemaValidateConstant;
import com.ns.ejk.form.util.validate.Validator;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by lenovo on 2018/1/4.
 */
@Service
public class ValidatorBiz {

    private volatile Map<String, Validator> validatorBeansMap;

    @Resource
    private SchemaItemBiz schemaItemBiz;

    @Resource
    private FormSchemaBiz formSchemaBiz;

    public void checkFiledList(DataItem dataItem, List<SchemaItemValidate> schemaItemValidateList) throws NSException {
        String beanName = null, errMsg = null;
        for (SchemaItemValidate validate : schemaItemValidateList) {
            beanName = validate.getType() + SchemaValidateConstant.VALIDATOR_BEAN_SUFFIX;
            Validator validator = getValidatorBeansMap().get(beanName);
            errMsg = validator.validate(dataItem == null ? null : dataItem.getValue(), validate.getValue());
            if (StringUtils.isNotBlank(errMsg)) {
                throw new ParameterException(String.format("参数【%s】值为【%s】不符合 要求【%s】", dataItem == null ? null : dataItem.getFieldName(), dataItem == null ? null : dataItem.getValue(), validate.getValue()));
            }
        }
    }

    private Map<String, Validator> getValidatorBeansMap() {
        if (validatorBeansMap == null) {
            synchronized (ValidatorBiz.class) {
                if (validatorBeansMap == null)
                    validatorBeansMap = SpringUtil.getApplicationContext().getBeansOfType(Validator.class);
            }
        }
        return validatorBeansMap;
    }


    public void validateDataItems(FormData formData) {
        if (CollectionUtils.isEmpty(formData.getDataItems())) {
            throw new ParameterException("items 不能为空");
        }

        FormSchema schema = formSchemaBiz.getById(formData.getSchemaId());

        Map<String, DataItem> dataItemMap = formData.getDataItems().stream().collect(
                Collectors.toMap(DataItem::getFieldName, item -> item));

        Map<SchemaItem, List<SchemaItemValidate>> schemaItems = schemaItemBiz.getAllBySchemaId(schema.getId());
        Set<Map.Entry<SchemaItem, List<SchemaItemValidate>>> entrySet = schemaItems.entrySet();

        for (Map.Entry<SchemaItem, List<SchemaItemValidate>> entry : entrySet) {
            SchemaItem schemaItem = entry.getKey();
            List<SchemaItemValidate> schemaValidates = entry.getValue();
            DataItem dataItem = dataItemMap.get(schemaItem.getFieldName());
            if (CollectionUtils.isNotEmpty(schemaValidates)) {
                checkFiledList(dataItem, schemaValidates);

             /*   for (SchemaItemValidate validate : schemaValidates) {
                   String validateMsg = ValidatorManager.getValidator(validate.getType()).validate(value, validate.getValue());
                   Optional.ofNullable(validateMsg).ifPresent(errorMsgs::add);
                }*/
               /* if (!errorMsgs.isEmpty()) {
                    validateResult.put(schemaItem.getDisplayName(), errorMsgs);
                }*/
            }

        }
    }
}
