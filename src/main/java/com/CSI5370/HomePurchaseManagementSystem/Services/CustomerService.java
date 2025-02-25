package com.CSI5370.HomePurchaseManagementSystem.Services;

import org.springframework.stereotype.Service;

import java.sql.*;

@Service
public class CustomerService {

    String URL = "jdbc:postgresql://localhost:5432/HMPS";

    String USER = "csi5370";

    String PASSWORD = "mypassword";

    public int createCustomer(String firstName, String lastName, String ssn, float income) throws SQLException {
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
            System.err.println("JDBC Driver not found!");
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return custId;
    }
}
