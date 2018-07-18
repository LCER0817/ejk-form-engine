package com.ns.ejk.form.util.constant;

/**
 * Created by lenovo on 2018/1/4.
 */
public interface SchemaValidateConstant {

    String VALIDATOR_BEAN_SUFFIX = "Validator";

    enum ValidateType {
        REQURIE(1, "required"), NUMBER_RANGE(3, "numberRange"), LENGTH_RANGE(3, "lengthRange"), REGEX(4, "regex");
        private Integer type;
        private String name;

        ValidateType(Integer type, String name) {
            this.type = type;
            this.name = name;
        }

        public static ValidateType getCheckType(int type) {
            for (ValidateType valiadType : ValidateType.values()) {
                if (valiadType.getType().equals(type)) {
                    return valiadType;
                }
            }
            return null;
        }

        public Integer getType() {
            return type;
        }

        public String getName() {
            return name;
        }
    }
}
