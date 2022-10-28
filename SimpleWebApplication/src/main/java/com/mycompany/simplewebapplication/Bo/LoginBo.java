/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simplewebapplication.Bo;

import com.mycompany.simplewebapplication.dao.loginDao;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author charith
 */
public class LoginBo {

    loginDao dao = new loginDao();

    public boolean checkCustomer(String userId,String password, Connection connection) throws SQLException {
        boolean searchCustomer = dao.searchCustomer(userId,password, connection);
        if (searchCustomer) {
            return true;
        } else {
            return false;
        }

    }

}
