package com.CSI5370.HomePurchaseManagementSystem;


import com.CSI5370.HomePurchaseManagementSystem.Domain.*;
import com.CSI5370.HomePurchaseManagementSystem.ErrorResponses.BadRequestErrorSchema;
import com.CSI5370.HomePurchaseManagementSystem.ErrorResponses.NotFoundErrorSchema;
import com.CSI5370.HomePurchaseManagementSystem.ErrorResponses.ServiceUnavailableErrorSchema;
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
    @ApiResponse(responseCode = "200", description = "Resource Created", content = @Content(schema = @Schema(implementation = CustomerId.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(schema = @Schema(implementation = BadRequestErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ServiceUnavailableErrorSchema.class)))
    public ResponseEntity<CustomerId> createCustomer(@Valid @RequestParam @Pattern(regexp = "[a-zA-Z]+") @Schema(description = "First name containing only letters", pattern = "[a-zA-Z]+") String firstName,
                                                  @Valid @RequestParam @Pattern(regexp = "[a-zA-Z]+") @Schema(description = "Last name containing only letters", pattern = "[a-zA-Z]+") String lastName,
                                                  @Valid @RequestParam @Pattern(regexp = "[0-9]{2}-[0-9]{3}-[0-9]{4}") @Schema(description = "Social Security Number format XX-XXX-XXXX", pattern = "[0-9]{2}-[0-9]{3}-[0-9]{4}") String ssn,
                                                  @Valid @RequestParam @Schema(description = "Annual income in float format (min: 0, max: 1,0000,000)", minimum = "0", maximum = "10000000")
                                                  @DecimalMin(value = "0.0", message = "Income must be at least 1000")
                                                  @DecimalMax(value = "10000000.0", message = "Income must not exceed 1,0000,000")
                                                  float income) throws SQLException {
        CustomerId customer = new CustomerId(customerService.createCustomer(firstName, lastName, ssn, income));
        return ResponseEntity.ok(customer);
    }

    @GetMapping("customer/get/{customerid}")
    @Operation(summary = "Create a purchase record", description = "Create a purchase record")
    @ApiResponse(responseCode = "200", description = "Resource Created")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(schema = @Schema(implementation = BadRequestErrorSchema.class)))
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = NotFoundErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ServiceUnavailableErrorSchema.class)))
    public ResponseEntity<Customer> getCustomer(@Valid @PathVariable int customerid){

        Customer customer = customerService.getCustomer(customerid);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("customer/delete/{customerid}")
    @Operation(summary = "Delete a customer record", description = "Delete a customer record")
    @ApiResponse(responseCode = "204", description = "Resource Deleted")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(schema = @Schema(implementation = BadRequestErrorSchema.class)))
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = NotFoundErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ServiceUnavailableErrorSchema.class)))
    public ResponseEntity<Void> deleteCustomer(@Valid @PathVariable int customerid){

        customerService.deleteCustomer(customerid);

        return ResponseEntity.noContent().build();
    }


//---------Purchase Endpoints--------------------------------------------------------------------------------------------------------------------

    @PostMapping("purchase/create")
    @Operation(summary = "Create a purchase record", description = "Create a purchase record")
    @ApiResponse(responseCode = "200", description = "Resource Created", content = @Content(schema = @Schema(implementation = PurchaseId.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(schema = @Schema(implementation = BadRequestErrorSchema.class)))
    @ApiResponse(responseCode = "404", description = "Resource not possible", content = @Content(schema = @Schema(implementation = NotFoundErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ServiceUnavailableErrorSchema.class)))
    public ResponseEntity<PurchaseId> createPurchase(@Valid @RequestParam int customerId,
                                                  @Valid @RequestParam int realtorId,
                                                  @Valid @RequestParam int homeId,
                                                  @Valid @RequestParam int loan,
                                                  @Valid @RequestParam int downPayment){

        PurchaseId purchaseId = new PurchaseId(purchaseService.createPurchase(customerId, realtorId, homeId, loan, downPayment));
        return ResponseEntity.ok(purchaseId);
    }

    @GetMapping("purchase/get/{purchaseid}")
    @Operation(summary = "Create a purchase record", description = "Create a purchase record")
    @ApiResponse(responseCode = "200", description = "Resource Created")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(schema = @Schema(implementation = BadRequestErrorSchema.class)))
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = NotFoundErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ServiceUnavailableErrorSchema.class)))
    public ResponseEntity<Purchase> getPurchase(@PathVariable int purchaseid){

        Purchase purchase = purchaseService.getPurchase(purchaseid);
        return ResponseEntity.ok(purchase);
    }

    @DeleteMapping("purchase/delete/{purchaseid}")
    @Operation(summary = "Delete a purchase record", description = "Delete a purchase record")
    @ApiResponse(responseCode = "204", description = "Resource Deleted")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(schema = @Schema(implementation = BadRequestErrorSchema.class)))
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = NotFoundErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ServiceUnavailableErrorSchema.class)))
    public ResponseEntity<Void> deletePurchase(@PathVariable int purchaseid){

        purchaseService.deletePurchase(purchaseid);

        return ResponseEntity.noContent().build();
    }


//---------Realtor Endpoints--------------------------------------------------------------------------------------------------------------------

    @PostMapping("realtor/create")
    @Operation(summary = "Create a realtor record", description = "Create a realtor record")
    @ApiResponse(responseCode = "200", description = "Resource Created", content = @Content(schema = @Schema(implementation = RealtorId.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(schema = @Schema(implementation = BadRequestErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ServiceUnavailableErrorSchema.class)))
    public ResponseEntity<RealtorId> createRealtor(@Valid @RequestParam int employeenum,
                                                  @Valid @RequestParam @Pattern(regexp = "[a-zA-Z]+") @Schema(description = "First name containing only letters", pattern = "[a-zA-Z]+") String firstName,
                                                  @Valid @RequestParam @Pattern(regexp = "[a-zA-Z]+") @Schema(description = "Last name containing only letters", pattern = "[a-zA-Z]+") String lastName,
                                                 @Valid @RequestParam @Schema(description = "Commission Rate in float format (min: 0, max: 1)", minimum = "0.0", maximum = "1.0")
                                                 @DecimalMin(value = "0.0", message = "Commission rate cannot be negative")
                                                 @DecimalMax(value = "1.0", message = "Commission must not exceed 1,0000,000")
                                                 float commissionRate) throws SQLException {
        RealtorId realId = new RealtorId(realtorService.createRealtor( employeenum, firstName, lastName, commissionRate));
        return ResponseEntity.ok(realId);
    }

    @GetMapping("realtor/get/{realtorid}")
    @Operation(summary = "Get a realtor record", description = "Get a realtor record")
    @ApiResponse(responseCode = "200", description = "Resource Created")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(schema = @Schema(implementation = BadRequestErrorSchema.class)))
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = NotFoundErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ServiceUnavailableErrorSchema.class)))
    public ResponseEntity<Realtor> getRealtor(@Valid @PathVariable int realtorid){

       Realtor realtor = realtorService.getrealtor(realtorid);
        return ResponseEntity.ok(realtor);
    }

    @DeleteMapping("realtor/delete/{realtorid}")
    @Operation(summary = "Delete a realtor record", description = "Delete a realtor record")
    @ApiResponse(responseCode = "204", description = "Resource Deleted")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(schema = @Schema(implementation = BadRequestErrorSchema.class)))
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = NotFoundErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ServiceUnavailableErrorSchema.class)))
    public ResponseEntity<Void> deleteRealtor(@Valid @PathVariable int realtorid){

        realtorService.deleteRealtor(realtorid);

        return ResponseEntity.noContent().build();
    }


//---------Home Endpoints--------------------------------------------------------------------------------------------------------------------

    @PostMapping("home/create")
    @Operation(summary = "Create a home record", description = "Create a home record")
    @ApiResponse(responseCode = "200", description = "Resource Created", content = @Content(schema = @Schema(implementation = HomeId.class)))
    @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(schema = @Schema(implementation = BadRequestErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ServiceUnavailableErrorSchema.class)))
    public ResponseEntity<HomeId> createHome(@Valid @RequestParam int address,
                                              @Valid @RequestParam @Pattern(regexp = "[a-zA-Z]+") @Schema(description = "Street name containing only letters", pattern = "[a-zA-Z]+") String street,
                                              @Valid @RequestParam @Pattern(regexp = "[a-zA-Z]+") @Schema(description = "City name containing only letters", pattern = "[a-zA-Z]+") String city,
                                              @Valid @RequestParam @Pattern(regexp = "[a-zA-Z]{2,4}") @Schema(description = "State name containing only letters", pattern = "[a-zA-Z]{2}") String state,
                                              @DecimalMin(value = "1", message = "price must be at least 1")
                                              @DecimalMax(value = "9999999", message = "Price must not exceed 99,99,999")
                                              @Valid @RequestParam @Schema(description = "Price in integer format (min: 1, max: 9999999)", minimum = "1", maximum = "9999999") float price,
                                              @DecimalMin(value = "1", message = "sq feet cannot be negative")
                                              @Valid @RequestParam @Schema(description = "Square feet in integer format (min: 1)", minimum = "1") int squareFeet) throws SQLException {
        HomeId homeid = new HomeId(homeService.createHome(address, street,city,state,price,squareFeet));
        return ResponseEntity.ok(homeid);
    }

    @GetMapping("home/get/{homeid}")
    @Operation(summary = "Get a home record", description = "Get a home record")
    @ApiResponse(responseCode = "200", description = "Resource Created")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(schema = @Schema(implementation = BadRequestErrorSchema.class)))
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = NotFoundErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ServiceUnavailableErrorSchema.class)))
    public ResponseEntity<Home> getHome(@Valid @PathVariable int homeid){

        Home home = homeService.gethome(homeid);
        return ResponseEntity.ok(home);
    }

    @DeleteMapping("home/delete/{homeid}")
    @Operation(summary = "Delete a home record", description = "Delete a home record")
    @ApiResponse(responseCode = "204", description = "Resource Deleted")
    @ApiResponse(responseCode = "400", description = "Invalid request parameters", content = @Content(schema = @Schema(implementation = BadRequestErrorSchema.class)))
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = NotFoundErrorSchema.class)))
    @ApiResponse(responseCode = "503", description = "Service Unavailable", content = @Content(schema = @Schema(implementation = ServiceUnavailableErrorSchema.class)))
    public ResponseEntity<Void> deletehome(@Valid @PathVariable int homeid){

        homeService.deleteHome(homeid);

        return ResponseEntity.noContent().build();
    }

}
