package com.CSI5370.HomePurchaseManagementSystem.Services;

import org.springframework.stereotype.Service;

import java.sql.*;
@Service
public class RealtorService {

    String URL = "jdbc:postgresql://localhost:5432/HMPS";

    String USER = "csi5370";

    String PASSWORD = "mypassword";
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



}
