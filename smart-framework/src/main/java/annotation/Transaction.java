package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义需要事务控制的方法注解
 * @author cs
 * @since 1.0.0
 */
@Target(ElementType.METHOD)//方法级别的注解
@Retention(RetentionPolicy.RUNTIME)
public @interface Transaction {

}
