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

/**
 *
 * @author charith
 */
public class loginDao {

    public boolean searchCustomer(String userId,String password, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("select * from Customer where userId=? and password=?");
        statement.setObject(1, userId);
        statement.setObject(2, password);
        ResultSet rst = statement.executeQuery();

        if (rst.next()) {
            return true;
        } else {
            return false;
        }
    }

}
