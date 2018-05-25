package com.sppan.base.common.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)//注解会在class中存在，运行时可通过反射获取
@Target(ElementType.METHOD)//目标是方法
@Documented//文档生成时，该注解将被包含在javadoc中，可去掉
public @interface LogAnnotation {

    /**
     * 方法
     * @return
     */
    String action() default "";

    /**
     * 类型
     * @return
     */
    String targetType() default "";

    /**
     * 备注
     * @return
     */
    String remark() default "";
}
