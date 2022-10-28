/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simplewebapplication.Controllers;

import com.mycompany.simplewebapplication.Bo.CustomerBo;
import com.mycompany.simplewebapplication.DTO.CustomerDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
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
@WebServlet(urlPatterns = "/customer")
public class CustomerController extends HttpServlet {

    CustomerBo customerBo = new CustomerBo();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        BasicDataSource basicDataSource = (BasicDataSource) servletContext.getAttribute("bds");
        resp.setContentType("application/json");
        String option = req.getParameter("options");
        try {
            Connection connection = basicDataSource.getConnection();
            switch (option) {
                case "search":
                    String userId = req.getParameter("userId");
                    JsonObjectBuilder customerObject = Json.createObjectBuilder();

                    CustomerDTO customerDTO = customerBo.searchCustomer(userId, connection);
                    PrintWriter printWriter1 = resp.getWriter();

                    if (customerDTO == null) {
                        JsonObjectBuilder response = Json.createObjectBuilder();
                        response.add("status", 200);
                        response.add("message", "null");
                        response.add("data", JsonValue.NULL);
                        printWriter1.print(response.build());
                        
                    } else {
                        customerObject.add("userID", customerDTO.getUserId());
                        customerObject.add("userName", customerDTO.getUserName());
                        customerObject.add("address", customerDTO.getAddress());
                        customerObject.add("email", customerDTO.getEmail());
                        customerObject.add("telephoneNumber", customerDTO.getTelephoneNumber());
                        customerObject.add("password", customerDTO.getPassword());

                        JsonObjectBuilder response = Json.createObjectBuilder();
                        response.add("status", 200);
                        response.add("message", "Successfully");
                        response.add("data", customerObject.build());

                        printWriter1.print(response.build());
                    }

                    connection.close();
                    break;

                case "getAll":
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

                    ArrayList<CustomerDTO> allCustomer = customerBo.getAllCustomer(connection);

                    for (CustomerDTO customerDTOS : allCustomer) {
                        JsonObjectBuilder customer = Json.createObjectBuilder();
                        customer.add("userID", customerDTOS.getUserId());
                        customer.add("userName", customerDTOS.getUserName());
                        customer.add("address", customerDTOS.getAddress());
                        customer.add("email", customerDTOS.getEmail());
                        customer.add("telephoneNumber", customerDTOS.getTelephoneNumber());
                        customer.add("password", customerDTOS.getPassword());

                        arrayBuilder.add(customer.build());
                    }

                    PrintWriter printWriter = resp.getWriter();
                    printWriter.print(arrayBuilder.build());
                    connection.close();
                    break;

                default:
                    throw new AssertionError();
            }

        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject customerObject = reader.readObject();

        String userId = customerObject.getString("userID");
        String userName = customerObject.getString("userName");
        String address = customerObject.getString("address");
        String email = customerObject.getString("email");
        String telephoneNumber = customerObject.getString("telephoneNumber");
        String password = customerObject.getString("password");

        CustomerDTO customerDTO = new CustomerDTO(userId, userName, address, email, telephoneNumber, password);
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Connection connection = bds.getConnection();

            if (customerBo.saveCustomer(customerDTO, connection)) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status", 200);
                response.add("message", "Successfully Added");
                response.add("data", "");
                writer.print(response.build());
            } else {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status", 500);
                response.add("message", "Can't add the customer");
                response.add("data", "");
                writer.print(response.build());
            }

            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject customerObject = reader.readObject();

        String userId = customerObject.getString("userID");
        String userName = customerObject.getString("userName");
        String address = customerObject.getString("address");
        String email = customerObject.getString("email");
        String telephoneNumber = customerObject.getString("telephoneNumber");
        String password = customerObject.getString("password");

        CustomerDTO customerDTO = new CustomerDTO(userId, userName, address, email, telephoneNumber, password);
        PrintWriter writer = resp.getWriter();
        resp.setContentType("application/json");

        try {
            Connection connection = bds.getConnection();

            if (customerBo.updateCustomer(customerDTO, connection)) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status", 200);
                response.add("message", "Successfully Updated");
                response.add("data", "");
                writer.print(response.build());
            } else {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status", 500);
                response.add("message", "Can't update the customer");
                response.add("data", "");
                writer.print(response.build());
            }
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();
        BasicDataSource bds = (BasicDataSource) servletContext.getAttribute("bds");

        String userId = req.getParameter("userId");
        resp.setContentType("application/json");
        PrintWriter writer = resp.getWriter();
        try {
            Connection connection = bds.getConnection();
            if (customerBo.deleteCustomer(userId, connection)) {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status", 200);
                response.add("message", "Successfully deleted");
                response.add("data", "");
                writer.print(response.build());
            } else {
                JsonObjectBuilder response = Json.createObjectBuilder();
                response.add("status", 500);
                response.add("message", "Can't delete the customer");
                response.add("data", "");
                writer.print(response.build());
            }
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
