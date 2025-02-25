package com.CSI5370.HomePurchaseManagementSystem.Services;

import org.springframework.stereotype.Service;

import java.sql.*;
@Service
public class HomeService {

    String URL = "jdbc:postgresql://localhost:5432/HMPS";

    String USER = "csi5370";

    String PASSWORD = "mypassword";

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
