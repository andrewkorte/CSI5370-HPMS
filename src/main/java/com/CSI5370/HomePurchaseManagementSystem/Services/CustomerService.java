package com.CSI5370.HomePurchaseManagementSystem.Services;

import com.CSI5370.HomePurchaseManagementSystem.Domain.Customer;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.CustomerNotFound;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.PostgresUnavailableException;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.TestingException;
import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class CustomerService {

    String URL = "jdbc:postgresql://localhost:5432/HMPS";

    String USER = "csi5370";

    String PASSWORD = "mypassword";

    public int createCustomer(String firstName, String lastName, String ssn, float income) throws SQLException {

        if(income < 800){
            throw new TestingException("Customer Income Lower Boundary Exception");
        }
        if(income > 800000){
            throw new TestingException("Customer Income Upper Boundary Exception");
        }

        if(firstName.toLowerCase().contains("a")){
            throw new TestingException("Customer First Name Exception");
        }
        if(lastName.toLowerCase().contains("p")){
            throw new TestingException("Customer Last Name Exception");
        }

        firstName = firstName.replaceAll("s", "Z");

        Connection conn = null;

        String createSQL = "INSERT INTO customer (firstName, lastName, ssn, income) values (?, ?, ?, ?) returning id;";

        int custId = 0;

        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            PreparedStatement create = conn.prepareStatement(createSQL);

            create.setString(1,firstName);
            create.setString(2,lastName);
            create.setString(3,ssn);
            create.setFloat(4, income);

            ResultSet rs = create.executeQuery();

            while (rs.next()){
                custId = rs.getInt("id");
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new PostgresUnavailableException("Service Unavailable", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    throw new PostgresUnavailableException("Service Unavailable", e);
                }
            }
        }
        return custId;
    }


    public Customer getCustomer(int customerid){
        Connection conn = null;

        String getSQL = "SELECT * FROM customer where id = ?;";

        Customer customer = new Customer();

        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            PreparedStatement get = conn.prepareStatement(getSQL);

            get.setInt(1,customerid);

            ResultSet rs = get.executeQuery();

            if (rs.next()){

                customer.setId(rs.getInt("id"));
                customer.setFirstName(rs.getString("firstname"));
                customer.setLastName(rs.getString("lastname"));
                customer.setSsn(rs.getString("ssn"));
                customer.setIncome(rs.getFloat("income"));

            } else {
                throw new CustomerNotFound("Customer Not Found");
            }

        }catch (SQLException e){
            e.printStackTrace();
            throw new PostgresUnavailableException("Service Unavailable", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    throw new PostgresUnavailableException("Service Unavailable", e);
                }
            }
        }
        return customer;
    }

    public void deleteCustomer(int customerid){
        Connection conn = null;

        String deleteSQL = "DELETE FROM customer WHERE id = ? RETURNING id;";


        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            getCustomer(customerid);

            PreparedStatement delete = conn.prepareStatement(deleteSQL);

            delete.setInt(1,customerid);

            delete.executeQuery();

        }catch (SQLException e){
            e.printStackTrace();
            throw new PostgresUnavailableException("Service Unavailable", e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    throw new PostgresUnavailableException("Service Unavailable", e);
                }
            }
        }
    }
}
