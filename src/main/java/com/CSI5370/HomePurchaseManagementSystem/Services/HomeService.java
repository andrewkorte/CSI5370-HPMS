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
                    e.printStackTrace();
                }
            }
        }
    }
}
