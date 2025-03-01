package com.CSI5370.HomePurchaseManagementSystem.Services;

import com.CSI5370.HomePurchaseManagementSystem.Domain.Customer;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.CustomerNotFound;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.PostgresUnavailableException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Spy
    @InjectMocks
    CustomerService customerService;

    @Test
    public void createCustomer_Success_ReturnsCustId() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doNothing().when(preparedStatement).setString(1, "firstname");
            doNothing().when(preparedStatement).setString(2, "lastname");
            doNothing().when(preparedStatement).setString(3, "22-222-2222");
            doNothing().when(preparedStatement).setFloat(4, 100.0F);
            doReturn(rs).when(preparedStatement).executeQuery();

            doReturn(true, false).when(rs).next();
            doReturn(1).when(rs).getInt("id");

            int result = customerService.createCustomer("firstname", "lastname", "22-222-2222", 100.0F);

            assertThat(result).isEqualTo(1);
        }
    }

    @Test
    public void createCustomer_SQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doNothing().when(preparedStatement).setString(1, "firstname");
            doNothing().when(preparedStatement).setString(2, "lastname");
            doNothing().when(preparedStatement).setString(3, "22-222-2222");
            doNothing().when(preparedStatement).setFloat(4, 100.0F);
            doThrow(new SQLException("Service Unavailable")).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> customerService.createCustomer("firstname", "lastname", "22-222-2222", 100.0F)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void createCustomer_ConnectionCloseSQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doThrow(new SQLException("Service Unavailable")).when(conn).close();

            doNothing().when(preparedStatement).setString(1, "firstname");
            doNothing().when(preparedStatement).setString(2, "lastname");
            doNothing().when(preparedStatement).setString(3, "22-222-2222");
            doNothing().when(preparedStatement).setFloat(4, 100.0F);


            assertThatThrownBy(() -> customerService.createCustomer("firstname", "lastname", "22-222-2222", 100.0F)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void createCustomer_NullPointerExcpetion_ConnectionNotClosed() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(null);

            assertThatThrownBy(() -> customerService.createCustomer("firstname", "lastname", "22-222-2222", 100.0F)).isInstanceOf(NullPointerException.class);

            verify(conn, times(0)).close();
        }
    }

    @Test
    public void getCustomer_Success_ReturnsCustomer() throws SQLException {
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
            doReturn("firstname").when(rs).getString("firstname");
            doReturn("lastname").when(rs).getString("lastname");
            doReturn("ssn").when(rs).getString("ssn");
            doReturn(100.0F).when(rs).getFloat("income");

            Customer result = customerService.getCustomer(1);

            assertThat(result.getId()).isEqualTo(1);
            assertThat(result.getFirstName()).isEqualTo("firstname");
            assertThat(result.getLastName()).isEqualTo("lastname");
            assertThat(result.getSsn()).isEqualTo("ssn");
            assertThat(result.getIncome()).isEqualTo(100.0F);
        }
    }

    @Test
    public void getCustomer_NotFoundException_ThrowCustomerNotFound() throws SQLException {
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

            assertThatThrownBy(() -> customerService.getCustomer(1)).isInstanceOf(CustomerNotFound.class);
        }
    }

    @Test
    public void getCustomer_SQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);
            doThrow(new SQLException("Service Unavailable")).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> customerService.getCustomer(1)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void getCustomer_ConnectionCloseSQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doThrow(new SQLException("Service Unavailable")).when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);


            assertThatThrownBy(() -> customerService.getCustomer(1)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void getCustomer_NullPointerExcpetion_ConnectionNotClosed() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(null);

            assertThatThrownBy(() -> customerService.getCustomer(1)).isInstanceOf(NullPointerException.class);

            verify(conn, times(0)).close();
        }
    }

    @Test
    public void deleteCustomer_Success_ReturnsVoid() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doReturn(new Customer()).when(customerService).getCustomer(1);

            doNothing().when(preparedStatement).setInt(1, 1);
            doReturn(null).when(preparedStatement).executeQuery();


            customerService.deleteCustomer(1);

            verify(customerService, times(1)).getCustomer(1);
        }
    }

    @Test
    public void deleteCustomer_SQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doReturn(new Customer()).when(customerService).getCustomer(1);

            doNothing().when(preparedStatement).setInt(1, 1);
            doThrow(new SQLException("Service Unavailable")).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> customerService.deleteCustomer(1)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void deleteCustomer_ConnectionCloseSQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doThrow(new SQLException("Service Unavailable")).when(conn).close();

            doReturn(new Customer()).when(customerService).getCustomer(1);

            doNothing().when(preparedStatement).setInt(1, 1);
            doReturn(null).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> customerService.deleteCustomer(1)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void deleteCustomer_NullPointerExcpetion_ConnectionNotClosed() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(null);

            assertThatThrownBy(() -> customerService.deleteCustomer(1)).isInstanceOf(NullPointerException.class);

            verify(conn, times(0)).close();
        }
    }

}