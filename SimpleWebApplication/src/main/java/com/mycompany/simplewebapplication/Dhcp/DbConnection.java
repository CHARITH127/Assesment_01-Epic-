/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simplewebapplication.Dhcp;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author charith
 */
@WebListener
public class DbConnection implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        BasicDataSource bds = new BasicDataSource();
        bds.setDriverClassName("com.mysql.cj.jdbc.Driver");
        bds.setUrl("jdbc:mysql://localhost:3306/company?autoReconnect=true&useSSL=false");
        bds.setUsername("root");
        bds.setPassword("ijse");
        bds.setMaxTotal(10);
        bds.setInitialSize(10);
        
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.setAttribute("bds", bds);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");
        
        try {
            bds.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
