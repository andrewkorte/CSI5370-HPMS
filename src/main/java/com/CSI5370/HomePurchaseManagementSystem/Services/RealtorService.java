package com.CSI5370.HomePurchaseManagementSystem.Services;

import com.CSI5370.HomePurchaseManagementSystem.Domain.Customer;
import com.CSI5370.HomePurchaseManagementSystem.Domain.Realtor;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.CustomerNotFound;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.PostgresUnavailableException;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.RealtorNotFound;
import org.springframework.stereotype.Service;

import java.sql.*;
@Service
public class RealtorService {

    String URL = "jdbc:postgresql://localhost:5432/HMPS";

    String USER = "csi5370";

    String PASSWORD = "mypassword";
    public int createRealtor(int employeenum,String firstName, String lastName, float commissionRate) throws SQLException {
//        if((commissionRate > 0.3) && (commissionRate < 0.7)){
//            commissionRate += 0.1F;
//        }

        Connection conn = null;

        String createSQL = "INSERT INTO Realtor (employeenum, firstName, lastName, commissionRate) values (?, ?, ?, ?) returning id;";

        int realId = 0;

        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            PreparedStatement create = conn.prepareStatement(createSQL);

            create.setInt(1,employeenum);
            create.setString(2,firstName);
            create.setString(3,lastName);
            create.setFloat(4, commissionRate);

            ResultSet rs = create.executeQuery();

            while (rs.next()){
                realId = rs.getInt("id");
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
        return realId;
    }

    public Realtor getrealtor(int realtorid){
        Connection conn = null;

        String getSQL = "SELECT * FROM realtor where id = ?;";

        Realtor realtor = new Realtor();

        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            PreparedStatement get = conn.prepareStatement(getSQL);

            get.setInt(1,realtorid);

            ResultSet rs = get.executeQuery();

            if (rs.next()){

                realtor.setId(rs.getInt("id"));
                realtor.setFirstName(rs.getString("firstname"));
                realtor.setLastName(rs.getString("lastname"));
                realtor.setCommissionRate(rs.getFloat("commissionrate"));
                realtor.setEmployeenum(rs.getInt("employeenum"));

            } else {
                throw new RealtorNotFound("Realtor Not Found");
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
        return realtor;
    }

    public void deleteRealtor(int realtorid){
        Connection conn = null;

        String deleteSQL = "DELETE FROM realtor WHERE id = ? RETURNING id;";


        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            getrealtor(realtorid);

            PreparedStatement delete = conn.prepareStatement(deleteSQL);

            delete.setInt(1,realtorid);

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
