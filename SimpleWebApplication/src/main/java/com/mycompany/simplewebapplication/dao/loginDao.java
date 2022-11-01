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

/**
 *
 * @author charith
 */
public class loginDao {

    EncriptionCode encriptionCode =new EncriptionCode();
    
    public boolean searchCustomer(String userName,String password, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from Customer where userName=?");
        statement.setObject(1, userName);
        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            
            String encriptedPassword = rst.getString(6);
            String decript = encriptionCode.decription(encriptedPassword);
            if (password.equals(decript)) {
                return true;
            }else{
                return false;
            }
            
        } else {
            return false;
        }
    }
    
    

}
