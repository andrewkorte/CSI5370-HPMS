package com.CSI5370.HomePurchaseManagementSystem.Services;

import com.CSI5370.HomePurchaseManagementSystem.Domain.Customer;
import com.CSI5370.HomePurchaseManagementSystem.Domain.Purchase;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.CustomerNotFound;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.PostgresUnavailableException;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.PurchaseNotFound;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.PurchaseNotPossibleException;
import jakarta.validation.constraints.Pattern;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.naming.ServiceUnavailableException;
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
            e.printStackTrace();
            System.out.println(e.getMessage());
            if(e.getMessage().contains("violates foreign key constraint")) {
                throw new PurchaseNotPossibleException("Purchase Not Possible: " + e.getMessage(), e);
            } else {
                throw new PostgresUnavailableException("Service Unavailable", e);
            }
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
        return purchaseId;

    }

    public Purchase getPurchase(int purcahseid){
        Connection conn = null;

        String getSQL = "SELECT * FROM purchase where id = ?;";

        Purchase purchase = new Purchase();

        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            PreparedStatement get = conn.prepareStatement(getSQL);

            get.setInt(1,purcahseid);

            ResultSet rs = get.executeQuery();

            if (rs.next()){

                purchase.setId(rs.getInt("id"));
                purchase.setCustomerid(rs.getInt("customerid"));
                purchase.setRealtorid(rs.getInt("realtorid"));
                purchase.setHomeid(rs.getInt("homeid"));
                purchase.setLoan(rs.getInt("loan"));
                purchase.setDownpayment(rs.getInt("downpayment"));

            } else {
                throw new PurchaseNotFound("Purchase Not Found");
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
        return purchase;
    }

    public void deletePurchase(int purchaseid){
        Connection conn = null;

        String deleteSQL = "DELETE FROM purchase WHERE id = ? RETURNING id;";


        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            getPurchase(purchaseid);

            PreparedStatement delete = conn.prepareStatement(deleteSQL);

            delete.setInt(1,purchaseid);

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
