package org.smart4j.plugin.security.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.smart4j.plugin.security.annotation.User;
import org.smart4j.plugin.security.exception.AuthzException;

import common.proxy.AspectProxy;
import annotation.Aspect;
import annotation.Controller;

/**
 * 授权注解切面
 * @author cs
 * @since 1.0.0
 */
@Aspect(Controller.class)
public class AuthzAnnotationAspect extends AspectProxy{
	
	private static final Class[] ANNOTATION_CLASS_ARRAY= {
		User.class
	};
	
	@Override
	public void before(Class<?> cls, Method method, Object[] params) {
		//从目标类与目标方法中获取相应的注解
		Annotation annotation = getAnnotation(cls,method);
		if(annotation != null){
			//判断授权注解的类型
			Class<? extends Annotation> annotationType = annotation.annotationType();
			if(annotationType.equals(User.class)){
				handleUser();
			}
		}
	}

	private void handleUser() {
		Subject currentUser = SecurityUtils.getSubject();
		PrincipalCollection principals = currentUser.getPrincipals();
		if(principals == null || principals.isEmpty()){
			throw new AuthzException("当前用户尚未登录");
		}
	}

	private Annotation getAnnotation(Class<?> cls, Method method) {
		//遍历所有的授权注解
		for(Class<? extends Annotation> annotationClass:ANNOTATION_CLASS_ARRAY){
			//判断目标方法上是否带有授权注解
			if(method.isAnnotationPresent(annotationClass)){
				return method.getAnnotation(annotationClass);
			}
			//判断目标类上是否带有授权注解
			if(cls.isAnnotationPresent(annotationClass)){
				return cls.getAnnotation(annotationClass);
			}
		}
		return null;
	}
}
