package com.springboot.jpa.config.annotation;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD) // 이 어노테이션이 어떤 위치에서 선언할 수 있는지 정의 
@Retention(RetentionPolicy.RUNTIME) // 어노테이션이 실제로 적용되고 유지되는 범위
@Constraint(validatedBy = TelephoneValidator.class) // 사용할 ConstrantValidator 구현체 명시
public @interface Telephone {
    String message() default "전화번호 형식이 일치하지 않습니다.";
    Class[] groups() default {};
    Class[] payload() default {};
}
