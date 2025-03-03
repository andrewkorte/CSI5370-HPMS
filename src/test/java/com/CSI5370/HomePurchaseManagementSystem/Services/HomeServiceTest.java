package com.CSI5370.HomePurchaseManagementSystem.Services;

import com.CSI5370.HomePurchaseManagementSystem.Domain.Customer;
import com.CSI5370.HomePurchaseManagementSystem.Domain.Home;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.CustomerNotFound;
import com.CSI5370.HomePurchaseManagementSystem.Exceptions.HomeNotFound;
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
class HomeServiceTest {

    @Spy
    @InjectMocks
    HomeService homeService;

    @Test
    public void createHome_Success_ReturnsCustId() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 5046);
            doNothing().when(preparedStatement).setString(2, "Crazy");
            doNothing().when(preparedStatement).setString(3, "Hell");
            doNothing().when(preparedStatement).setString(4, "Michigan");
            doNothing().when(preparedStatement).setFloat(5, 40000.00F);
            doNothing().when(preparedStatement).setInt(6,2600);
            doReturn(rs).when(preparedStatement).executeQuery();

            doReturn(true, false).when(rs).next();
            doReturn(1).when(rs).getInt("id");

            int result = homeService.createHome(5046, "Crazy", "Hell", "Michigan", 40000.00f,2600);

            assertThat(result).isEqualTo(1);
        }
    }

    @Test
    public void createHome_SQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 5046);
            doNothing().when(preparedStatement).setString(2, "Crazy");
            doNothing().when(preparedStatement).setString(3, "Hell");
            doNothing().when(preparedStatement).setString(4, "Michigan");
            doNothing().when(preparedStatement).setFloat(5, 40000.00F);
            doNothing().when(preparedStatement).setInt(6,2600);
            doThrow(new SQLException("Service Unavailable")).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> homeService.createHome(5046, "Crazy", "Hell", "Michigan", 40000.00f,2600)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void createHome_ConnectionCloseSQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doThrow(new SQLException("Service Unavailable")).when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 5046);
            doNothing().when(preparedStatement).setString(2, "Crazy");
            doNothing().when(preparedStatement).setString(3, "Hell");
            doNothing().when(preparedStatement).setString(4, "Michigan");
            doNothing().when(preparedStatement).setFloat(5, 40000.00F);
            doNothing().when(preparedStatement).setInt(6,2600);


            assertThatThrownBy(() -> homeService.createHome(5046, "Crazy", "Hell", "Michigan", 40000.00f,2600)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void createHome_NullPointerExcpetion_ConnectionNotClosed() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(null);

            assertThatThrownBy(() -> homeService.createHome(5046, "Crazy", "Hell", "Michigan", 40000.00f,2600)).isInstanceOf(NullPointerException.class);

            verify(conn, times(0)).close();
        }
    }

    @Test
    public void getHome_Success_ReturnsHome() throws SQLException {
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
            doReturn(5046).when(rs).getInt("address");
            doReturn("Crazy").when(rs).getString("street");
            doReturn("Hell").when(rs).getString("city");
            doReturn("Michigan").when(rs).getString("state");
            doReturn(40000).when(rs).getInt("price");
            doReturn(2600).when(rs).getInt("squarefeet");

            Home result = homeService.gethome(1);

            assertThat(result.getId()).isEqualTo(1);
            assertThat(result.getAddress()).isEqualTo(5046);
            assertThat(result.getStreet()).isEqualTo("Crazy");
            assertThat(result.getCity()).isEqualTo("Hell");
            assertThat(result.getState()).isEqualTo("Michigan");
            assertThat(result.getPrice()).isEqualTo(40000.00F);
            assertThat(result.getSquareFeet()).isEqualTo(2600);
        }
    }

    @Test
    public void getHome_NotFoundException_ThrowHomeNotFound() throws SQLException {
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

            assertThatThrownBy(() -> homeService.gethome(1)).isInstanceOf(HomeNotFound.class);
        }
    }

    @Test
    public void getHome_SQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);
            doThrow(new SQLException("Service Unavailable")).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> homeService.gethome(1)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void getHome_ConnectionCloseSQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doThrow(new SQLException("Service Unavailable")).when(conn).close();

            doNothing().when(preparedStatement).setInt(1, 1);


            assertThatThrownBy(() -> homeService.gethome(1)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void getHome_NullPointerExcpetion_ConnectionNotClosed() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(null);

            assertThatThrownBy(() -> homeService.gethome(1)).isInstanceOf(NullPointerException.class);

            verify(conn, times(0)).close();
        }
    }

    @Test
    public void deleteHome_Success_ReturnsVoid() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doReturn(new Home()).when(homeService).gethome(1);

            doNothing().when(preparedStatement).setInt(1, 1);
            doReturn(null).when(preparedStatement).executeQuery();


            homeService.deleteHome(1);

            verify(homeService, times(1)).gethome(1);
        }
    }

    @Test
    public void deleteHome_SQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doNothing().when(conn).close();

            doReturn(new Home()).when(homeService).gethome(1);

            doNothing().when(preparedStatement).setInt(1, 1);
            doThrow(new SQLException("Service Unavailable")).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> homeService.deleteHome(1)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void deletehome_ConnectionCloseSQLExcpetion_ThrowsPostgresUnavailableException() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(conn);

            doReturn(preparedStatement).when(conn).prepareStatement(any());
            doThrow(new SQLException("Service Unavailable")).when(conn).close();

            doReturn(new Home()).when(homeService).gethome(1);

            doNothing().when(preparedStatement).setInt(1, 1);
            doReturn(null).when(preparedStatement).executeQuery();

            assertThatThrownBy(() -> homeService.deleteHome(1)).isInstanceOf(PostgresUnavailableException.class);
        }
    }

    @Test
    public void deleteHome_NullPointerExcpetion_ConnectionNotClosed() throws SQLException {
        Connection conn = mock(Connection.class);
        PreparedStatement preparedStatement = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try(MockedStatic<DriverManager> mockedStatic = Mockito.mockStatic(DriverManager.class)) {
            mockedStatic.when(() -> DriverManager.getConnection("jdbc:postgresql://localhost:5432/HMPS", "csi5370", "mypassword")).thenReturn(null);

            assertThatThrownBy(() -> homeService.deleteHome(1)).isInstanceOf(NullPointerException.class);

            verify(conn, times(0)).close();
        }
    }

}