package com.CSI5370.HomePurchaseManagementSystem;

import com.CSI5370.HomePurchaseManagementSystem.Domain.*;
import com.CSI5370.HomePurchaseManagementSystem.Services.CustomerService;
import com.CSI5370.HomePurchaseManagementSystem.Services.HomeService;
import com.CSI5370.HomePurchaseManagementSystem.Services.PurchaseService;
import com.CSI5370.HomePurchaseManagementSystem.Services.RealtorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HPMSEndpointsTest {

    @InjectMocks
    HPMSEndpoints hpmsEndpoints;

    @Mock
    CustomerService customerService;

     @Mock
    HomeService homeService;

     @Mock
    RealtorService realtorService;

     @Mock
    PurchaseService purchaseService;

    @Test
    public void createCustomer_Returns200WithId() throws SQLException {

        when(customerService.createCustomer("firstname", "lastname", "22-222-2222", 100.00F)).thenReturn(1);

        ResponseEntity<CustomerId> result = hpmsEndpoints.createCustomer("firstname", "lastname", "22-222-2222", 100.00F);

        assertThat(Objects.requireNonNull(result.getBody()).customerid).isEqualTo(1);
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

  /* ----------------------------------------------------------------------------------------------------------*/


    @Test
    public void createHome_Returns200WithId() throws SQLException {

        when(homeService.createHome(5046, "Crazy", "Hell", "Michigan", 40000.00F,2600)).thenReturn(1);

        ResponseEntity<HomeId> result = hpmsEndpoints.createHome(5046, "Crazy", "Hell", "Michigan", 40000.00F,2600);

        assertThat(Objects.requireNonNull(result.getBody()).homeid).isEqualTo(1);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getHome_Returns200WithHome() {
        Home home = new Home(1,5046, "Crazy", "Hell", "Michigan", 40000.00F,2600);

        when(homeService.gethome(1)).thenReturn(home);

        ResponseEntity<Home> result = hpmsEndpoints.getHome(1);

        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getAddress()).isEqualTo(5046);
        assertThat(result.getBody().getStreet()).isEqualTo("Crazy");
        assertThat(result.getBody().getCity()).isEqualTo("Hell");
        assertThat(result.getBody().getState()).isEqualTo("Michigan");
        assertThat(result.getBody().getPrice()).isEqualTo(40000.00F);
        assertThat(result.getBody().getSquareFeet()).isEqualTo(2600);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteHome_Returns204() {

        doNothing().when(homeService).deleteHome(1);

        ResponseEntity<Void> result = hpmsEndpoints.deletehome(1);

        verify(homeService, times(1)).deleteHome(1);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    /* ----------------------------------------------------------------------------------------------------------*/

    @Test
    public void createRealtor_Returns200WithId() throws SQLException {

        when(realtorService.createRealtor(1, "Ken", "Butcher", 400.00F)).thenReturn(1);

        ResponseEntity<RealtorId> result = hpmsEndpoints.createRealtor(1, "Ken", "Butcher", 400.00F);

        assertThat(Objects.requireNonNull(result.getBody()).realtorid).isEqualTo(1);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getRealtor_Returns200WithRealtor() {
        Realtor realtor = new Realtor(1,1, "Ken", "Butcher", 400.00F);

        when(realtorService.getrealtor(1)).thenReturn(realtor);

        ResponseEntity<Realtor> result = hpmsEndpoints.getRealtor(1);

        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getEmployeenum()).isEqualTo(1);
        assertThat(result.getBody().getFirstName()).isEqualTo("Ken");
        assertThat(result.getBody().getLastName()).isEqualTo("Butcher");
        assertThat(result.getBody().getCommissionRate()).isEqualTo(400.00F);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deleteRealtor_Returns204() {

        doNothing().when(realtorService).deleteRealtor(1);

        ResponseEntity<Void> result = hpmsEndpoints.deleteRealtor(1);

        verify(realtorService, times(1)).deleteRealtor(1);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    /* ----------------------------------------------------------------------------------------------------------*/

    @Test
    public void createPurchase_Returns200WithId() throws SQLException {

        when(purchaseService.createPurchase(1, 1, 1, 500000,500)).thenReturn(1);

        ResponseEntity<PurchaseId> result = hpmsEndpoints.createPurchase(1, 1, 1, 500000,500);

        assertThat(result.getBody().purchaseid).isEqualTo(1);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getPurchase_Returns200WithPurchase() {
       Purchase purchase = new Purchase(1,1, 1, 1, 500000,500);

        when(purchaseService.getPurchase(1)).thenReturn(purchase);

        ResponseEntity<Purchase> result = hpmsEndpoints.getPurchase(1);

        assertThat(result.getBody().getId()).isEqualTo(1);
        assertThat(result.getBody().getCustomerid()).isEqualTo(1);
        assertThat(result.getBody().getRealtorid()).isEqualTo(1);
        assertThat(result.getBody().getHomeid()).isEqualTo(1);
        assertThat(result.getBody().getLoan()).isEqualTo(500000);
        assertThat(result.getBody().getDownpayment()).isEqualTo(500);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void deletePurchase_Returns204() {

        doNothing().when(purchaseService).deletePurchase(1);

        ResponseEntity<Void> result = hpmsEndpoints.deletePurchase(1);

        verify(purchaseService, times(1)).deletePurchase(1);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

}