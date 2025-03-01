package com.CSI5370.HomePurchaseManagementSystem;

import com.CSI5370.HomePurchaseManagementSystem.Domain.Customer;
import com.CSI5370.HomePurchaseManagementSystem.Services.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HPMSEndpointsTest {

    @InjectMocks
    HPMSEndpoints hpmsEndpoints;

    @Mock
    CustomerService customerService;

    @Test
    public void createCustomer_Returns200WithId() throws SQLException {

        when(customerService.createCustomer("firstname", "lastname", "22-222-2222", 100.00F)).thenReturn(1);

        ResponseEntity<Integer> result = hpmsEndpoints.createCustomer("firstname", "lastname", "22-222-2222", 100.00F);

        assertThat(result.getBody()).isEqualTo(1);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getCustomer_Returns200WithCustomer() {
        Customer customer = new Customer(1, "firstname", "lastname", "22-222-2222", 100.0F);

        when(customerService.getCustomer(1)).thenReturn(customer);

        ResponseEntity<Customer> result = hpmsEndpoints.getCustomer(1);

        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getFirstName()).isEqualTo("firstname");
        assertThat(result.getBody().getLastName()).isEqualTo("lastname");
        assertThat(result.getBody().getSsn()).isEqualTo("22-222-2222");
        assertThat(result.getBody().getIncome()).isEqualTo(100.0F);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteCustomer_Returns204() {

        doNothing().when(customerService).deleteCustomer(1);

        ResponseEntity<Void> result = hpmsEndpoints.deleteCustomer(1);

        verify(customerService, times(1)).deleteCustomer(1);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}