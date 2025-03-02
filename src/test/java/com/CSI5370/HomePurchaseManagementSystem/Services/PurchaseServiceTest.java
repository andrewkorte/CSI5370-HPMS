package com.CSI5370.HomePurchaseManagementSystem.Services;

import com.CSI5370.HomePurchaseManagementSystem.Domain.Customer;
import com.CSI5370.HomePurchaseManagementSystem.Domain.Purchase;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.CustomerNotFound;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.PostgresUnavailableException;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.PurchaseNotFound;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.PurchaseNotPossibleException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PurchaseServiceTest {

    @Spy
    @InjectMocks
    PurchaseService purchaseService;

    @Test
    public void createPurchase_Success_ReturnsPurchaseId() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);
            doNothing().when(preparedStatement).setInt(2, 2);
            doNothing().when(preparedStatement).setInt(3, 3);
            doNothing().when(preparedStatement).setInt(4, 4);
            doNothing().when(preparedStatement).setInt(5, 5);
            doReturn(rs).when(preparedStatement).executeQuery();

            doReturn(true, false).when(rs).next();
            doReturn(1).when(rs).getInt("id");

            int result = purchaseService.createPurchase(1, 2, 3, 4, 5);

            assertThat(result).isEqualTo(1);
        }
    }

    @Test
    public void createPurchase_SQLExcpetionWithViollatingKey_ThrowsPurchaseNotPossibleException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);
            doNothing().when(preparedStatement).setInt(2, 2);
            doNothing().when(preparedStatement).setInt(3, 3);
            doNothing().when(preparedStatement).setInt(4, 4);
            doNothing().when(preparedStatement).setInt(5, 5);
            doThrow(new SQLException("violates foreign key constraint")).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> purchaseService.createPurchase(1, 2, 3, 4, 5)).isInstanceOf(PurchaseNotPossibleException.class);
        }
    }

    @Test
    public void createPurchase_SQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);
            doNothing().when(preparedStatement).setInt(2, 2);
            doNothing().when(preparedStatement).setInt(3, 3);
            doNothing().when(preparedStatement).setInt(4, 4);
            doNothing().when(preparedStatement).setInt(5, 5);
            doThrow(new SQLException("Service Unavailable")).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> purchaseService.createPurchase(1, 2, 3, 4, 5)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void createPurchase_ConnectionCloseSQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doThrow(new SQLException("Service Unavailable")).when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);
            doNothing().when(preparedStatement).setInt(2, 2);
            doNothing().when(preparedStatement).setInt(3, 3);
            doNothing().when(preparedStatement).setInt(4, 4);
            doNothing().when(preparedStatement).setInt(5, 5);


            assertThatThrownBy(() -> purchaseService.createPurchase(1, 2, 3, 4, 5)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void createPurchase_NullPointerExcpetion_ConnectionNotClosed() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(null);

            assertThatThrownBy(() -> purchaseService.createPurchase(1, 2, 3, 4, 5)).isInstanceOf(NullPointerException.class);

            verify(conn, times(0)).close();
        }
    }

    @Test
    public void getPurchase_Success_ReturnsPurchase() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);
            doReturn(rs).when(preparedStatement).executeQuery();

            doReturn(true).when(rs).next();
            doReturn(1).when(rs).getInt("id");
            doReturn(2).when(rs).getInt("customerid");
            doReturn(3).when(rs).getInt("realtorid");
            doReturn(4).when(rs).getInt("homeid");
            doReturn(5).when(rs).getInt("loan");
            doReturn(6).when(rs).getInt("downpayment");


            Purchase result = purchaseService.getPurchase(1);

            assertThat(result.getId()).isEqualTo(1);
            assertThat(result.getCustomerid()).isEqualTo(2);
            assertThat(result.getRealtorid()).isEqualTo(3);
            assertThat(result.getHomeid()).isEqualTo(4);
            assertThat(result.getLoan()).isEqualTo(5);
            assertThat(result.getDownpayment()).isEqualTo(6);
        }
    }

    @Test
    public void getPurchase_NotFoundException_ThrowsPurchaseNotFound() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);
            doReturn(rs).when(preparedStatement).executeQuery();

            doReturn(false).when(rs).next();

            assertThatThrownBy(() -> purchaseService.getPurchase(1)).isInstanceOf(PurchaseNotFound.class);
        }
    }

    @Test
    public void getPurchase_SQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);
            doThrow(new SQLException("Service Unavailable")).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> purchaseService.getPurchase(1)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void getPurchase_ConnectionCloseSQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doThrow(new SQLException("Service Unavailable")).when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);


            assertThatThrownBy(() -> purchaseService.getPurchase(1)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void getPurchase_NullPointerExcpetion_ConnectionNotClosed() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(null);

            assertThatThrownBy(() -> purchaseService.getPurchase(1)).isInstanceOf(NullPointerException.class);

            verify(conn, times(0)).close();
        }
    }

    @Test
    public void deletePurchase_Success_ReturnsVoid() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doReturn(new Purchase()).when(purchaseService).getPurchase(1);

            doNothing().when(preparedStatement).setInt(1, 1);
            doReturn(null).when(preparedStatement).executeQuery();


            purchaseService.deletePurchase(1);

            verify(purchaseService, times(1)).getPurchase(1);
        }
    }

    @Test
    public void deletePurchase_SQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doReturn(new Purchase()).when(purchaseService).getPurchase(1);

            doNothing().when(preparedStatement).setInt(1, 1);
            doThrow(new SQLException("Service Unavailable")).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> purchaseService.deletePurchase(1)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void deletePurchase_ConnectionCloseSQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doThrow(new SQLException("Service Unavailable")).when(conn).close();

            doReturn(new Purchase()).when(purchaseService).getPurchase(1);

            doNothing().when(preparedStatement).setInt(1, 1);
            doReturn(null).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> purchaseService.deletePurchase(1)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void deletePurcahse_NullPointerExcpetion_ConnectionNotClosed() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(null);

            assertThatThrownBy(() -> purchaseService.deletePurchase(1)).isInstanceOf(NullPointerException.class);

            verify(conn, times(0)).close();
        }
    }
}