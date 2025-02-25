package com.CSI5370.HomePurchaseManagementSystem.Services;

import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.*;

@Service
public class PurchaseService {

    String URL = "jdbc:postgresql://localhost:5432/HMPS";

    String USER = "csi5370";

    String PASSWORD = "mypassword";

    public int createPurchase(int customerId, int realtorId, int homeId, int loan, int downPayment){
        Connection conn = null;

        String createSQL = "INSERT INTO purchase (customerid, realtorid, homeid, loan, downpayment) values (?, ?, ?, ?, ?) returning id;";

        int purchaseId = 0;

        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            PreparedStatement create = conn.prepareStatement(createSQL);

            create.setInt(1, customerId);
            create.setInt(2, realtorId);
            create.setInt(3, homeId);
            create.setInt(4, loan);
            create.setInt(5, downPayment);

            ResultSet rs = create.executeQuery();

            while (rs.next()){
                purchaseId = rs.getInt("id");
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
        return purchaseId;

    }
}
