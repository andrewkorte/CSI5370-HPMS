package com.CSI5370.HomePurchaseManagementSystem.Services;

import org.springframework.stereotype.Service;

import java.sql.*;
@Service
public class CustomerService {

    String URL = "localhost:5432";

    String USER = "csi5370";

    String PASSWORD = "mypassword";

    public int createCustomer(String firstName, String lastName, String ssn, float income) throws SQLException {
        Connection conn = null;

        String createSQL = "INSERT INTO customer (firstName, lastName, ssn, income) values (?, ?, ?, ?) returning id;";

        int custId = 0;

        try(PreparedStatement create = conn.prepareStatement(createSQL)){
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

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
        }finally {
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


    public int createRealtor(int employeenum,String firstName, String lastName, float commissionRate) throws SQLException {
        Connection conn = null;

        String createSQL = "INSERT INTO Realtor (employeenum, firstName, lastName, commissionRate) values (?, ?, ?, ?) returning id;";

        int realId = 0;

        try(PreparedStatement create = conn.prepareStatement(createSQL)){
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            create.setInt(1,employeenum);
            create.setString(2,firstName);
            create.setString(3,lastName);
            create.setFloat(4, commissionRate);

            ResultSet rs = create.executeQuery();

            while (rs.next()){
                realId = rs.getInt("id");
            }

        }catch (SQLException e){
            System.err.println("JDBC Driver not found!");
            e.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return realId;
    }


    public int createHome(int streetNum, String city, String state, float price, int squareFeet) throws SQLException {
        Connection conn = null;

        String createSQL = "INSERT INTO Home (streetNum, city, state, price, squareFeet) values (?, ?, ?, ?) returning id;";

        int homeId = 0;

        try(PreparedStatement create = conn.prepareStatement(createSQL)){
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            create.setInt(1,streetNum);
            create.setString(2,city);
            create.setString(3,state);
            create.setFloat(4, price);
            create.setInt(5,squareFeet);

            ResultSet rs = create.executeQuery();

            while (rs.next()){
                homeId = rs.getInt("id");
            }

        }catch (SQLException e){
            System.err.println("JDBC Driver not found!");
            e.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("Connection closed.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return homeId;
    }



}
