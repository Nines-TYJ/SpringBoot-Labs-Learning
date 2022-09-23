package com.nines.springboot.lab22.validation.core.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author tanyujie
 * @classname InEnumValidator
 * @description 自定义 InEnum 校验类
 * @date 2022/8/17 15:43
 * @since 1.0
 */
public class InEnumValidator implements ConstraintValidator<InEnum, Object> {

    /**
     * 数值
     */
    private Set<Object> values;

    @Override
    public void initialize(InEnum constraintAnnotation) {
        ArrayValuable[] values = constraintAnnotation.value().getEnumConstants();
        if (values.length == 0) {
            this.values = Collections.emptySet();
        } else {
            this.values = Arrays.stream(values[0].array()).collect(Collectors.toSet());
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 校验通过
        if (values.contains(value)) {
            return true;
        }
        // 禁用默认的 message 的值
        context.disableDefaultConstraintViolation();
        // 校验不通过，自定义提示语句（因为，注解上的 value 是枚举类，无法获得枚举类的实际值） 重新添加错误提示语句
        context
                .buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()
                        .replaceAll("\\{value}", values.toString()))
                .addConstraintViolation();
        return false;
    }
}
