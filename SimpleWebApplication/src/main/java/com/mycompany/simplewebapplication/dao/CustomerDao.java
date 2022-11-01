/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simplewebapplication.dao;

import com.mycompany.simplewebapplication.Entity.Customer;
import com.mycompany.simplewebapplication.Util.EncriptionCode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author charith
 */
public class CustomerDao {

    EncriptionCode encriptionCode = new EncriptionCode();

    public boolean saveCustomer(Customer customer, Connection connection) throws SQLException {

        Customer searchCustomer = searchCustomer(customer.getUserId(), connection);
        if (searchCustomer == null) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String createTime = dtf.format(now);

            String password = customer.getPassword();
            String encryptedPassword = encriptionCode.encript(customer.getPassword());

            System.out.println(customer.getPassword());

            PreparedStatement stm = connection.prepareStatement("Insert into Customer values (?,?,?,?,?,?,?,?,?,?)");
            stm.setObject(1, customer.getUserId());
            stm.setObject(2, customer.getUserName());
            stm.setObject(3, customer.getAddress());
            stm.setObject(4, customer.getEmail());
            stm.setObject(5, customer.getTelephoneNumber());
            stm.setObject(6, encryptedPassword);
            stm.setObject(7, createTime);
            stm.setObject(8, customer.getUserId());
            stm.setObject(9, "");
            stm.setObject(10, "");

            if (stm.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        }else{
            return false;
        }

    }

    public boolean updateCustomer(Customer customer, Connection connection) throws SQLException {
        
        Customer checkCustomer = searchCustomer(customer.getUserId(), connection);
        String decriptionPassword = encriptionCode.decription(checkCustomer.getPassword());

        if ((customer.getUserId().equalsIgnoreCase(checkCustomer.getUserId())) && (customer.getUserName().equalsIgnoreCase(checkCustomer.getUserName())) && (customer.getAddress().equalsIgnoreCase(checkCustomer.getAddress())) && (customer.getEmail().equalsIgnoreCase(checkCustomer.getEmail())) && (customer.getTelephoneNumber().equalsIgnoreCase(checkCustomer.getTelephoneNumber())) & (customer.getPassword().equalsIgnoreCase(decriptionPassword))) {
            return false;
        } else {
            Customer searchCustomer = searchCustomerWithFullDetails(customer.getUserId(), connection);
            String createdDate = searchCustomer.getCreateDate();
            String createdUser = searchCustomer.getCreateUser();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String lastUpdateTime = dtf.format(now);

            String encryptedPassword = encriptionCode.encript(customer.getPassword());

            PreparedStatement stm = connection.prepareStatement("Update Customer set userName=? , address=? , email=?, telephoneNumber=? ,password=?, createTime=?, createUser=?, lastUpdateTime=?, lastUpdateUser=? where userId=?");
            stm.setObject(1, customer.getUserName());
            stm.setObject(2, customer.getAddress());
            stm.setObject(3, customer.getEmail());
            stm.setObject(4, customer.getTelephoneNumber());
            stm.setObject(5, encryptedPassword);
            stm.setObject(6, createdDate);
            stm.setObject(7, createdUser);
            stm.setObject(8, lastUpdateTime);
            stm.setObject(9, customer.getUserName());
            stm.setObject(10, customer.getUserId());

            if (stm.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        }

    }

    public boolean deleteCustomer(String userID, Connection connection) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("Delete from Customer where userId=?");
        stm.setObject(1, userID);

        if (stm.executeUpdate() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Customer searchCustomer(String checkId, Connection connection) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("Select * from Customer where userId=? or userName=? or userName=? or userName=?");
        stm.setObject(1, checkId);
        stm.setObject(2, checkId);
        stm.setObject(3, checkId);
        stm.setObject(4, checkId);
        ResultSet rst = stm.executeQuery();
        if (rst.next()) {
            return new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6));
        } else {
            return null;
        }
    }

    public ArrayList<Customer> getAllCustomer(Connection connection) throws SQLException {
        PreparedStatement stm = connection.prepareStatement("select * from Customer");
        ResultSet rst = stm.executeQuery();
        ArrayList<Customer> customersList = new ArrayList<>();

        while (rst.next()) {
            Customer customer = new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6));
            customersList.add(customer);
        }

        return customersList;
    }

    private Customer searchCustomerWithFullDetails(String userID, Connection connection) {
        PreparedStatement stm;
        try {
            stm = connection.prepareStatement("Select * from Customer where userId=?");
            stm.setObject(1, userID);
            ResultSet rst = stm.executeQuery();
            if (rst.next()) {
                return new Customer(rst.getString(1), rst.getString(2), rst.getString(3), rst.getString(4), rst.getString(5), rst.getString(6), rst.getString(7), rst.getString(8), rst.getString(9), rst.getString(10));
            } else {
                return null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CustomerDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
