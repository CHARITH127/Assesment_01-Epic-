/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simplewebapplication.Controllers;

import com.mycompany.simplewebapplication.Bo.LoginBo;
import com.mycompany.simplewebapplication.DTO.CustomerDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.dbcp2.BasicDataSource;

/**
 *
 * @author charith
 */
@WebServlet(urlPatterns = "/login")
public class LoginController extends HttpServlet {

    LoginBo loginBo = new LoginBo();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");
        resp.setContentType("application/json");
        try {
            Connection connection = bds.getConnection();
            
            JsonReader createReader = Json.createReader(req.getReader());
            JsonObject loginObject = createReader.readObject();
            
            String userName = loginObject.getString("userName");
            String userPassword = loginObject.getString("userPassword");


            boolean checkCustomer = loginBo.checkCustomer(userName,userPassword, connection);
            PrintWriter writer = resp.getWriter();
            
            if (checkCustomer) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status", 200);
                response.add("message", "true");
                response.add("data", JsonValue.NULL);
                writer.print(response.build());
                
                
            } else {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status", 200);
                response.add("message", "false");
                response.add("data", JsonValue.NULL);
                writer.print(response.build());
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
