package org.smart4j.chapter1.service;

import java.util.List;
import java.util.Map;

import common.helper.DatabaseHelper;
import common.helper.UploadHelper;

import org.smart4j.chapter1.model.Customer;

import annotation.Service;
import annotation.Transaction;

/**
 * �ṩ�ͻ����ݷ���
 */
@Service
public class CustomerService {

    /**
     * ��ȡ�ͻ��б�
     */
    public List<Customer> getCustomerList() {
        String sql = "SELECT * FROM customer";
        return DatabaseHelper.queryEntityList(Customer.class, sql);
    }

    /**
     * ��ȡ�ͻ�
     */
    public Customer getCustomer(long id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        return DatabaseHelper.queryEntity(Customer.class, sql, id);
    }

    /**
     * �����ͻ�
     */
    @Transaction
    public boolean createCustomer(Map<String, Object> fieldMap) {
         boolean result = DatabaseHelper.insertEntity(Customer.class, fieldMap);
         return result;
    }

    /**
     * ���¿ͻ�
     */
    @Transaction
    public boolean updateCustomer(long id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(Customer.class, id, fieldMap);
    }

    /**
     * ɾ���ͻ�
     */
    @Transaction
    public boolean deleteCustomer(long id) {
        return DatabaseHelper.deleteEntity(Customer.class, id);
    }
}

