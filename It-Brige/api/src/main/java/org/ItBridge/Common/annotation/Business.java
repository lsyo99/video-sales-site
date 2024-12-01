package org.ItBridge.Common.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) //클래스와 인터페이스, 열겨형에 적용하도록 설정
@Retention(RetentionPolicy.RUNTIME) // 런타임시에도 유지되도록
@Service //자동으로 빈관리가 들어가도록
public @interface Business {
    @AliasFor(annotation = Service.class)
    String value() default "";

}
