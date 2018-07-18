package com.ns.ejk.form.util.errorcode;

import com.ns.common.util.exception.errorcode.ErrorCode;

/**
 * Created by lenovo on 2018/1/4.
 */
public interface FormEngineError {
    ErrorCode NOT_NUll = new ErrorCode(9000, "不能为空");
    ErrorCode NUM_NOT_IN_RANGE = new ErrorCode(9001, "数值不在范围内");
    ErrorCode CHAR_LENTH_NOT_IN_RANGE = new ErrorCode(9001, "字符长度不在范围内");
    ErrorCode NOT_EXITS_COMMA = new ErrorCode(9002, "不存在逗号分隔符");
    ErrorCode RANG_LENGTH_ERROR = new ErrorCode(9004, "范围长度错误");
    ErrorCode DATA_NOT_MATCH = new ErrorCode(9004, "数据不匹配");
    ErrorCode DATA_NOT_EXIST = new ErrorCode(9005, "数据不存在");
    ErrorCode SCHEMA_NOT_EXIST = new ErrorCode(9006, "SCHEMA不存在");
}
