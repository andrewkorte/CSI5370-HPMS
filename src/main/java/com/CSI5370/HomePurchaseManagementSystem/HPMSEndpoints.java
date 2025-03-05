package com.CSI5370.HomePurchaseManagementSystem;


import com.CSI5370.HomePurchaseManagementSystem.Domain.Customer;
import com.CSI5370.HomePurchaseManagementSystem.Domain.Home;
import com.CSI5370.HomePurchaseManagementSystem.Domain.Purchase;
import com.CSI5370.HomePurchaseManagementSystem.Domain.Realtor;
import com.CSI5370.HomePurchaseManagementSystem.ErrorResponses.ErrorSchema;
import com.CSI5370.HomePurchaseManagementSystem.Services.CustomerService;
import com.CSI5370.HomePurchaseManagementSystem.Services.HomeService;
import com.CSI5370.HomePurchaseManagementSystem.Services.PurchaseService;
import com.CSI5370.HomePurchaseManagementSystem.Services.RealtorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/")
public class HPMSEndpoints {

    @Autowired
    CustomerService customerService;

    @Autowired
    HomeService homeService;

    @Autowired
    RealtorService realtorService;

    @Autowired
    PurchaseService purchaseService;


//---------Customer Endpoints--------------------------------------------------------------------------------------------------------------------

    @PostMapping("customer/create")
    @Operation(summary = "Create a customer record", description = "Create a customer record")
    @ApiResponse(responseCode = "200", description = "Resource Created")
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    public ResponseEntity<Integer> createCustomer(@Valid @RequestParam @Pattern(regexp = "[a-zA-Z]+") @Schema(description = "First name containing only letters", pattern = "[a-zA-Z]+") String firstName,
                                                  @Valid @RequestParam @Pattern(regexp = "[a-zA-Z]+") @Schema(description = "Last name containing only letters", pattern = "[a-zA-Z]+") String lastName,
                                                  @Valid @RequestParam @Pattern(regexp = "[0-9]{2}-[0-9]{3}-[0-9]{4}") @Schema(description = "Social Security Number format XX-XXX-XXXX", pattern = "[0-9]{2}-[0-9]{3}-[0-9]{4}") String ssn,
                                                  @Valid @RequestParam @Schema(description = "Annual income in float format (min: 0, max: 1,0000,000)", minimum = "0", maximum = "10000000")
                                                  @DecimalMin(value = "0.0", message = "Income must be at least 1000")
                                                  @DecimalMax(value = "10000000.0", message = "Income must not exceed 1,0000,000")
                                                  float income) throws SQLException {
        int custId = customerService.createCustomer(firstName, lastName, ssn, income);
        return ResponseEntity.ok(custId);
    }

    @GetMapping("customer/get/{customerid}")
    @Operation(summary = "Create a purchase record", description = "Create a purchase record")
    @ApiResponse(responseCode = "200", description = "Resource Created")
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    public ResponseEntity<Customer> getCustomer(@Valid @PathVariable int customerid){

        Customer customer = customerService.getCustomer(customerid);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("customer/delete/{customerid}")
    @Operation(summary = "Delete a customer record", description = "Delete a customer record")
    @ApiResponse(responseCode = "204", description = "Resource Deleted")
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    public ResponseEntity<Void> deleteCustomer(@Valid @PathVariable int customerid){

        customerService.deleteCustomer(customerid);

        return ResponseEntity.noContent().build();
    }


//---------Purchase Endpoints--------------------------------------------------------------------------------------------------------------------

    @PostMapping("purchase/create")
    @Operation(summary = "Create a purchase record", description = "Create a purchase record")
    @ApiResponse(responseCode = "200", description = "Resource Created")
    @ApiResponse(responseCode = "404", description = "Resource not possible", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    public ResponseEntity<Integer> createPurchase(@RequestParam int customerId,
                                                  @RequestParam int realtorId,
                                                  @RequestParam int homeId,
                                                  @RequestParam int loan,
                                                  @RequestParam int downPayment){

        int purchaseId = purchaseService.createPurchase(customerId, realtorId, homeId, loan, downPayment);
        return ResponseEntity.ok(purchaseId);
    }

    @GetMapping("purchase/get/{purchaseid}")
    @Operation(summary = "Create a purchase record", description = "Create a purchase record")
    @ApiResponse(responseCode = "200", description = "Resource Created")
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    public ResponseEntity<Purchase> getPurchase(@PathVariable int purchaseid){

        Purchase purchase = purchaseService.getPurchase(purchaseid);
        return ResponseEntity.ok(purchase);
    }

    @DeleteMapping("purchase/delete/{purchaseid}")
    @Operation(summary = "Delete a purchase record", description = "Delete a purchase record")
    @ApiResponse(responseCode = "204", description = "Resource Deleted")
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    public ResponseEntity<Void> deletePurchase(@PathVariable int purchaseid){

        purchaseService.deletePurchase(purchaseid);

        return ResponseEntity.noContent().build();
    }


//---------Realtor Endpoints--------------------------------------------------------------------------------------------------------------------

    @PostMapping("realtor/create")
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    public ResponseEntity<Integer> createRealtor(@RequestParam int employeenum,
                                                  @RequestParam @Pattern(regexp = "[a-zA-Z]+") String firstName,
                                                  @RequestParam @Pattern(regexp = "[a-zA-Z]+") String lastName,
                                                 @DecimalMin(value = "0.0", message = "Commission rate cannot be negative")
                                                     @DecimalMax(value = "10000000.0", message = "Commission must not exceed 1,0000,000")
                                                  @RequestParam float commissionRate) throws SQLException {
        int realId=realtorService.createRealtor( employeenum, firstName, lastName, commissionRate);
        return ResponseEntity.ok(realId);
    }

    @GetMapping("realtor/get/{realtorid}")
    @Operation(summary = "Get a realtor record", description = "Get a realtor record")
    @ApiResponse(responseCode = "200", description = "Resource Created")
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    public ResponseEntity<Realtor> getRealtor(@Valid @PathVariable int realtorid){

       Realtor realtor = realtorService.getrealtor(realtorid);
        return ResponseEntity.ok(realtor);
    }

    @DeleteMapping("realtor/delete/{realtorid}")
    @Operation(summary = "Delete a realtor record", description = "Delete a realtor record")
    @ApiResponse(responseCode = "204", description = "Resource Deleted")
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    public ResponseEntity<Void> deleteRealtor(@Valid @PathVariable int realtorid){

        realtorService.deleteRealtor(realtorid);

        return ResponseEntity.noContent().build();
    }


//---------Home Endpoints--------------------------------------------------------------------------------------------------------------------

    @PostMapping("home/create")
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    public ResponseEntity<Integer> createHome(@RequestParam int address,
                                              @RequestParam String street,
                                              @RequestParam @Pattern(regexp = "[a-zA-Z]+") String city,
                                              @RequestParam @Pattern(regexp = "[a-zA-Z]+") String state,
                                              @DecimalMin(value = "1.0", message = "price must be at least 1")
                                                  @DecimalMax(value = "10000000.0", message = "Price must not exceed 1,0000,000")
                                              @RequestParam float price,
                                              @DecimalMin(value = "0.0", message = "sq feet cannot be negative")
                                                  @DecimalMax(value = "10000000.0", message = "size limit of 1,0000,000")
                                              @RequestParam int squareFeet) throws SQLException {
       int homeid =homeService.createHome(address, street,city,state,price,squareFeet);
        return ResponseEntity.ok(homeid);
    }

    @GetMapping("home/get/{homeid}")
    @Operation(summary = "Create a purchase record", description = "Create a purchase record")
    @ApiResponse(responseCode = "200", description = "Resource Created")
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    public ResponseEntity<Home> getHome(@Valid @PathVariable int homeid){

        Home home = homeService.gethome(homeid);
        return ResponseEntity.ok(home);
    }

    @DeleteMapping("home/delete/{homeid}")
    @Operation(summary = "Delete a home record", description = "Delete a home record")
    @ApiResponse(responseCode = "204", description = "Resource Deleted")
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ErrorSchema.class)))
    public ResponseEntity<Void> deletehome(@Valid @PathVariable int homeid){

        homeService.deleteHome(homeid);

        return ResponseEntity.noContent().build();
    }

}
