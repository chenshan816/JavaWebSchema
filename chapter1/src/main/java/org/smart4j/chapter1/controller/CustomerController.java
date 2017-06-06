package org.smart4j.chapter1.controller;

import java.util.List;

import org.smart4j.chapter1.model.Customer;
import org.smart4j.chapter1.service.CustomerService;

import annotation.Action;
import annotation.Controller;
import annotation.Inject;

import common.bean.Data;
import common.bean.Param;
import common.bean.View;


/**
 * ����ͻ������������
 */
@Controller
public class CustomerController {
	@Inject
    private CustomerService customerService;
	/**
     * ���� �ͻ��б� ����
     */
    @Action("get:/customer")
    public View index(Param param) {
        List<Customer> customerList = customerService.getCustomerList();
        return new View("hello.jsp").addModel("customerList", customerList);
    }

    /**
     * ��ʾ�ͻ�����Ϣ
     */
    @Action("get:/customer_show")
    public View show(Param param) {
        long id = param.getLong("id");
        Customer customer = customerService.getCustomer(id);
        return new View("customer_show.jsp").addModel("customer", customer);
    }

    /**
     * ���� �����ͻ� ����
     */
    @Action("get:/customer_create")
    public View create(Param param) {
        return new View("customer_create.jsp");
    }

    /**
     * ���� �����ͻ� ����
     */
//    @Action("post:/customer_create")
//    public Data createSubmit(Param param) {
//        Map<String, Object> fieldMap = param.getFieldMap();
//        boolean result = customerService.createCustomer(fieldMap);
//        return new Data(result);
//    }

    /**
     * ���� �༭�ͻ� ����
     */
    @Action("get:/customer_edit")
    public View edit(Param param) {
        long id = param.getLong("id");
        Customer customer = customerService.getCustomer(id);
        return new View("customer_edit.jsp").addModel("customer", customer);
    }

    /**
     * ���� �༭�ͻ� ����
     */
//    @Action("put:/customer_edit")
//    public Data editSubmit(Param param) {
//        long id = param.getLong("id");
//        Map<String, Object> fieldMap = param.getFieldMap();
//        boolean result = customerService.updateCustomer(id, fieldMap);
//        return new Data(result);
//    }

    /**
     * ���� ɾ��ͻ� ����
     */
    @Action("delete:/customer_edit")
    public Data delete(Param param) {
        long id = param.getLong("id");
        boolean result = customerService.deleteCustomer(id);
        return new Data(result);
    }

}
