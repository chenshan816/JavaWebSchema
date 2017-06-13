package org.smart4j.chapter1.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import plugin.security.SecurityHelper;
import plugin.security.exception.AuthcException;
import common.bean.Param;
import common.bean.View;
import annotation.Action;
import annotation.Controller;

/**
 * ����ϵͳ����
 * @author cs
 */
@Controller
public class SystemController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);
	
	/**
	 * ������ҳ
	 */
	@Action("get:/")
	public View index(){
		return new View("index.jsp");
	}
	
	/**
     * �����¼����
     */
    @Action("get:/login")
    public View login() {
        return new View("login.jsp");
    }
    
    /**
     * �ύ��¼��
     */
    @Action("post:/login")
    public View loginSubmit(Param param) {
        String username = param.getString("username");
        String password = param.getString("password");
        try {
            SecurityHelper.login(username, password);
        } catch (AuthcException e) {
            LOGGER.error("login failure", e);
            return new View("/login");
        }
        return new View("/customer");
    }
    
    /**
     * �ύע������
     */
    @Action("get:/logout")
    public View logout() {
        SecurityHelper.logout();
        return new View("/");
    }
}
