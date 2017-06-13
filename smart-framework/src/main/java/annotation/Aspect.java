package annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 切面注解
 * @author cs
 * @since 1.0.0
 */
@Target(ElementType.TYPE)//接口、类、枚举、注解
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
	/**
	 * 注解--附加在代码中的一些元信息，用于一些工具在编译、运行时进行解析和使用，起到说明、配置的功能
	 */
	Class<? extends Annotation> value();
}
