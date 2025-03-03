package com.CSI5370.HomePurchaseManagementSystem.Services;

import com.CSI5370.HomePurchaseManagementSystem.Domain.Customer;
import com.CSI5370.HomePurchaseManagementSystem.Domain.Realtor;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.CustomerNotFound;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.PostgresUnavailableException;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.RealtorNotFound;
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
class RealtorServiceTest {

    @Spy
    @InjectMocks
    RealtorService realtorService;

    @Test
    public void createRealtor_Success_ReturnsCustId() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);
            doNothing().when(preparedStatement).setString(2, "Ken");
            doNothing().when(preparedStatement).setString(3, "Butcher");
            doNothing().when(preparedStatement).setFloat(4, 400.00F);

            doReturn(rs).when(preparedStatement).executeQuery();

            doReturn(true, false).when(rs).next();
            doReturn(1).when(rs).getInt("id");

            int result = realtorService.createRealtor(1, "Ken", "Butcher", 400.00F);

            assertThat(result).isEqualTo(1);
        }
    }

    @Test
    public void createRealtor_SQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);
            doNothing().when(preparedStatement).setString(2, "Ken");
            doNothing().when(preparedStatement).setString(3, "Butcher");
            doNothing().when(preparedStatement).setFloat(4, 400.00F);

            doThrow(new SQLException("Service Unavailable")).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> realtorService.createRealtor(1, "Ken", "Butcher", 400.00F)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void createRealtor_ConnectionCloseSQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doThrow(new SQLException("Service Unavailable")).when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);
            doNothing().when(preparedStatement).setString(2, "Ken");
            doNothing().when(preparedStatement).setString(3, "Butcher");
            doNothing().when(preparedStatement).setFloat(4, 400.00F);


            assertThatThrownBy(() -> realtorService.createRealtor(1, "Ken", "Butcher", 400.00F)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void createRealtor_NullPointerExcpetion_ConnectionNotClosed() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(null);

            assertThatThrownBy(() -> realtorService.createRealtor(1, "Ken", "Butcher", 400.00F)).isInstanceOf(NullPointerException.class);

            verify(conn, times(0)).close();
        }
    }

    @Test
    public void getRealtor_Success_ReturnsRealtor() throws SQLException {
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
            doReturn(1).when(rs).getInt("employeenum");
            doReturn("Ken").when(rs).getString("firstname");
            doReturn("Butcher").when(rs).getString("lastname");
            doReturn(400.00F).when(rs).getFloat("commissionrate");

            Realtor result = realtorService.getrealtor(1);

            assertThat(result.getId()).isEqualTo(1);
            assertThat(result.getEmployeenum()).isEqualTo(1);
            assertThat(result.getFirstName()).isEqualTo("Ken");
            assertThat(result.getLastName()).isEqualTo("Butcher");
            assertThat(result.getCommissionRate()).isEqualTo(400.00F);
        }
    }

    @Test
    public void getRealtor_NotFoundException_ThrowRealtorNotFound() throws SQLException {
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

            assertThatThrownBy(() -> realtorService.getrealtor(1)).isInstanceOf(RealtorNotFound.class);
        }
    }

    @Test
    public void getRealtor_SQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);
            doThrow(new SQLException("Service Unavailable")).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> realtorService.getrealtor(1)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void getRealtor_ConnectionCloseSQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doThrow(new SQLException("Service Unavailable")).when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);


            assertThatThrownBy(() -> realtorService.getrealtor(1)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void getRealtor_NullPointerExcpetion_ConnectionNotClosed() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(null);

            assertThatThrownBy(() -> realtorService.getrealtor(1)).isInstanceOf(NullPointerException.class);

            verify(conn, times(0)).close();
        }
    }

    @Test
    public void deleteRealtor_Success_ReturnsVoid() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doReturn(new Realtor()).when(realtorService).getrealtor(1);

            doNothing().when(preparedStatement).setInt(1, 1);
            doReturn(null).when(preparedStatement).executeQuery();


            realtorService.deleteRealtor(1);

            verify(realtorService, times(1)).getrealtor(1);
        }
    }

    @Test
    public void deleteRealtor_SQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doReturn(new Realtor()).when(realtorService).getrealtor(1);

            doNothing().when(preparedStatement).setInt(1, 1);
            doThrow(new SQLException("Service Unavailable")).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> realtorService.deleteRealtor(1)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void deleteRealtor_ConnectionCloseSQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doThrow(new SQLException("Service Unavailable")).when(conn).close();

            doReturn(new Realtor()).when(realtorService).getrealtor(1);

            doNothing().when(preparedStatement).setInt(1, 1);
            doReturn(null).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> realtorService.deleteRealtor(1)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void deleteRealtor_NullPointerExcpetion_ConnectionNotClosed() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(null);

            assertThatThrownBy(() -> realtorService.getrealtor(1)).isInstanceOf(NullPointerException.class);

            verify(conn, times(0)).close();
        }
    }

}