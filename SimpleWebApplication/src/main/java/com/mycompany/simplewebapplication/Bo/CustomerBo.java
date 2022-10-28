/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simplewebapplication.Bo;

import com.mycompany.simplewebapplication.DTO.CustomerDTO;
import com.mycompany.simplewebapplication.Entity.Customer;
import com.mycompany.simplewebapplication.dao.CustomerDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author charith
 */
public class CustomerBo {
    
    CustomerDao customerDao = new CustomerDao();
    
    public boolean saveCustomer(CustomerDTO customerDTO ,Connection connection) throws SQLException{
        return customerDao.saveCustomer(new Customer(customerDTO.getUserId(),customerDTO.getUserName(),customerDTO.getAddress(),customerDTO.getEmail(),customerDTO.getTelephoneNumber(),customerDTO.getPassword()), connection);
    }
    
    public boolean updateCustomer(CustomerDTO customerDTO , Connection connection) throws SQLException{
       return customerDao.updateCustomer(new Customer(customerDTO.getUserId(),customerDTO.getUserName(),customerDTO.getAddress(),customerDTO.getEmail(),customerDTO.getTelephoneNumber(),customerDTO.getPassword()), connection);
    }
    
    public ArrayList<CustomerDTO> getAllCustomer(Connection connection) throws SQLException{
        ArrayList<Customer> allCustomer = customerDao.getAllCustomer(connection);
        ArrayList<CustomerDTO> customerDTOs = new ArrayList<>();
        for (Customer customer : allCustomer) {
            customerDTOs.add(new CustomerDTO(customer.getUserId(),customer.getUserName(),customer.getAddress(),customer.getEmail(),customer.getTelephoneNumber(),customer.getPassword()));
        }
        
        return customerDTOs;
    }
    
    public boolean deleteCustomer(String userId , Connection connection) throws SQLException{
        return customerDao.deleteCustomer(userId, connection);
    }
    
    public CustomerDTO searchCustomer(String userId , Connection connection) throws SQLException{
        Customer searchCustomer = customerDao.searchCustomer(userId, connection);
        if (searchCustomer==null) {
            return null;
        }else{
           return new CustomerDTO(searchCustomer.getUserId(),searchCustomer.getUserName(),searchCustomer.getAddress(),searchCustomer.getEmail(),searchCustomer.getTelephoneNumber(),searchCustomer.getPassword()); 
        }
        
    }
}
