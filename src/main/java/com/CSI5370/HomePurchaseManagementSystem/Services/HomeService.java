package com.CSI5370.HomePurchaseManagementSystem.Services;

import com.CSI5370.HomePurchaseManagementSystem.Domain.Home;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.HomeNotFound;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.PostgresUnavailableException;
import org.springframework.stereotype.Service;

import java.sql.*;
@Service
public class HomeService {

    String URL = "jdbc:postgresql://localhost:5432/HMPS";

    String USER = "csi5370";

    String PASSWORD = "mypassword";

    public int createHome(int address, String street, String city, String state, float price, int squareFeet) throws SQLException {
        Connection conn = null;

        String createSQL = "INSERT INTO Home (streetNum, city, state, price, squareFeet) values (?, ?, ?, ?) returning id;";

        int homeId = 0;

        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            PreparedStatement create = conn.prepareStatement(createSQL);

            create.setInt(1,address);
            create.setString(2,street);
            create.setString(3,city);
            create.setString(4,state);
            create.setFloat(5, price);
            create.setInt(6,squareFeet);

            ResultSet rs = create.executeQuery();

            while (rs.next()){
                homeId = rs.getInt("id");
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
        return homeId;
    }

    public Home gethome(int homeid){
        Connection conn = null;

        String getSQL = "SELECT * FROM home where id = ?;";

       Home home = new Home();

        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            PreparedStatement get = conn.prepareStatement(getSQL);

            get.setInt(1,homeid);

            ResultSet rs = get.executeQuery();

            if (rs.next()){

                home.setId(rs.getInt("id"));
                home.setAddress(rs.getInt("address"));
                home.setStreet(rs.getString("street"));
                home.setCity(rs.getString("city"));
                home.setState(rs.getString("state"));
                home.setPrice(rs.getInt("price"));
                home.setSquareFeet(rs.getInt("squarefeet"));

            } else {
                throw new HomeNotFound("Home Not Found");
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
        return home;
    }

    public void deleteHome(int homeid){
        Connection conn = null;

        String deleteSQL = "DELETE FROM home WHERE id = ? RETURNING id;";


        try{
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            gethome(homeid);

            PreparedStatement delete = conn.prepareStatement(deleteSQL);

            delete.setInt(1,homeid);

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
