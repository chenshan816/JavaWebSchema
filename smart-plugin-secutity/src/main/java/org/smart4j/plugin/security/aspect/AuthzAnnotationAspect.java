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
 * ��Ȩע������
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
		//��Ŀ������Ŀ�귽���л�ȡ��Ӧ��ע��
		Annotation annotation = getAnnotation(cls,method);
		if(annotation != null){
			//�ж���Ȩע�������
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
			throw new AuthzException("��ǰ�û���δ��¼");
		}
	}

	private Annotation getAnnotation(Class<?> cls, Method method) {
		//�������е���Ȩע��
		for(Class<? extends Annotation> annotationClass:ANNOTATION_CLASS_ARRAY){
			//�ж�Ŀ�귽�����Ƿ������Ȩע��
			if(method.isAnnotationPresent(annotationClass)){
				return method.getAnnotation(annotationClass);
			}
			//�ж�Ŀ�������Ƿ������Ȩע��
			if(cls.isAnnotationPresent(annotationClass)){
				return cls.getAnnotation(annotationClass);
			}
		}
		return null;
	}
}
