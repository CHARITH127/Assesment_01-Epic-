/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simplewebapplication.dao;

import com.mycompany.simplewebapplication.Entity.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author charith
 */
public class CustomerDao {
    
    public boolean saveCustomer(Customer customer, Connection connection) throws SQLException{
        PreparedStatement stm = connection.prepareStatement("Insert into Customer values (?,?,?,?,?,?)");
        stm.setObject(1, customer.getUserId());
        stm.setObject(2, customer.getUserName());
        stm.setObject(3, customer.getAddress());
        stm.setObject(4, customer.getEmail());
        stm.setObject(5, customer.getTelephoneNumber());
        stm.setObject(6, customer.getPassword());
        
        if (stm.executeUpdate()>0) {
            return true;
        }else{
            return false;
        }
    }
    
    
    public boolean updateCustomer(Customer customer , Connection connection) throws SQLException{
        PreparedStatement stm = connection.prepareStatement("Update Customer set userName=? , address=? , email=?, telephoneNumber=? ,password=? where userId=?");
        stm.setObject(1, customer.getUserName());
        stm.setObject(2, customer.getAddress());
        stm.setObject(3, customer.getEmail());
        stm.setObject(4, customer.getTelephoneNumber());
        stm.setObject(5, customer.getPassword());
        stm.setObject(6, customer.getUserId());
        
        if (stm.executeUpdate()>0) {
            return true;
        }else{
            return false;
        }
    }
    
    public boolean deleteCustomer(String userID, Connection connection) throws SQLException{
        PreparedStatement stm = connection.prepareStatement("Delete from Customer where userId=?");
        stm.setObject(1, userID);
        
        if (stm.executeUpdate()>0) {
            return true;
        }else{
            return false;
        }
    }
    
    public Customer searchCustomer(String userID, Connection connection) throws SQLException{
        PreparedStatement stm = connection.prepareStatement("Select * from Customer where userId=?");
        stm.setObject(1, userID);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6));
        }else{
            return null;
        }
    }
    
    public ArrayList<Customer> getAllCustomer(Connection connection) throws SQLException{
        PreparedStatement stm = connection.prepareStatement("select * from Customer");
        ResultSet rst = stm.executeQuery();
        ArrayList<Customer> customersList = new ArrayList<>();
        
        while (rst.next()) {            
            Customer customer = new Customer(rst.getString(1),rst.getString(2),rst.getString(3),rst.getString(4),rst.getString(5),rst.getString(6));
            customersList.add(customer);
        }
        
        return customersList;
    } 
    
    
}
