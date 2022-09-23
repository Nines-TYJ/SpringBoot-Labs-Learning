package com.nines.springboot.lab22.validation.core.enums;

import com.nines.springboot.lab22.validation.core.validator.ArrayValuable;

import java.util.Arrays;

/**
 * @author tanyujie
 * @classname GenderEnum
 * @description TODO
 * @date 2022/8/17 15:34
 * @since 1.0
 */
public enum GenderEnum implements ArrayValuable {
    /**
     * 男
     */
    MALE("1", "男"),

    /**
     * 女
     */
    FEMALE("2", "女");

    /**
     * 值数组
     */
    public static final Object[] ARRAYS = Arrays.stream(values()).map(GenderEnum::getCode).toArray();

    private final String code;

    private final String desc;

    GenderEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public Object[] array() {
        return ARRAYS;
    }
}
