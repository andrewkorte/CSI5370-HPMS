package com.CSI5370.HomePurchaseManagementSystem;


import com.CSI5370.HomePurchaseManagementSystem.Domain.Customer;
import com.CSI5370.HomePurchaseManagementSystem.Domain.Home;
import com.CSI5370.HomePurchaseManagementSystem.Domain.Realtor;
import com.CSI5370.HomePurchaseManagementSystem.ErrorResponses.Schema404;
import com.CSI5370.HomePurchaseManagementSystem.Services.CustomerService;
import com.CSI5370.HomePurchaseManagementSystem.Services.HomeService;
import com.CSI5370.HomePurchaseManagementSystem.Services.PurchaseService;
import com.CSI5370.HomePurchaseManagementSystem.Services.RealtorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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


    @PostMapping("customer/create")
    @Operation(summary = "Create a customer record", description = "Create a customer record")
    @ApiResponse(responseCode = "200", description = "Resource Created")
    public ResponseEntity<Integer> createCustomer(@RequestParam @Pattern(regexp = "[a-zA-Z]+") String firstName,
                                                  @RequestParam @Pattern(regexp = "[a-zA-Z]+") String lastName,
                                                  @RequestParam @Pattern(regexp = "[0-9]{2}-[0-9]{3}-[0-9]{4}") String ssn,
                                                  @RequestParam float income) throws SQLException {
        int custId = customerService.createCustomer(firstName, lastName, ssn, income);
        return ResponseEntity.ok(custId);
    }

    @GetMapping("customer/get/{customerid}")
    @Operation(summary = "Create a purchase record", description = "Create a purchase record")
    @ApiResponse(responseCode = "200", description = "Resource Created")
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = Schema404.class)))
    public ResponseEntity<Customer> createCustomer(@PathVariable int customerid){

        Customer customer = customerService.getCustomer(customerid);
        return ResponseEntity.ok(customer);
    }




    @PostMapping("purchase/create")
    @Operation(summary = "Create a purchase record", description = "Create a purchase record")
    @ApiResponse(responseCode = "200", description = "Resource Created")
    @ApiResponse(responseCode = "404", description = "Resource not possible", content = @Content(schema = @Schema(implementation = Schema404.class)))
    public ResponseEntity<Integer> createPurchase(@RequestParam int customerId,
                                                  @RequestParam int realtorId,
                                                  @RequestParam int homeId,
                                                  @RequestParam int loan,
                                                  @RequestParam int downPayment){

        int purchaseId = purchaseService.createPurchase(customerId, realtorId, homeId, loan, downPayment);
        return ResponseEntity.ok(purchaseId);
    }

    @PostMapping("realtor/create")
    public ResponseEntity<Integer> createRealtor(@RequestParam int employeenum,
                                                  @RequestParam @Pattern(regexp = "[a-zA-Z]+") String firstName,
                                                  @RequestParam @Pattern(regexp = "[a-zA-Z]+") String lastName,
                                                  @RequestParam float commissionRate) throws SQLException {
        int realId=realtorService.createRealtor( employeenum, firstName, lastName, commissionRate);
        return ResponseEntity.ok(realId);
    }

    @GetMapping("customer/get/{customerid}")
    @Operation(summary = "Create a purchase record", description = "Create a purchase record")
    @ApiResponse(responseCode = "200", description = "Resource Created")
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = Schema404.class)))
    public ResponseEntity<Realtor> getRealtor(@PathVariable int realtorid){

       Realtor realtor = realtorService.getrealtor(realtorid);
        return ResponseEntity.ok(realtor);
    }



    @PostMapping("home/create")
    public ResponseEntity<Integer> createHome(@RequestParam int streetNum,
                                                @RequestParam @Pattern(regexp = "[a-zA-Z]+") String city,
                                                 @RequestParam @Pattern(regexp = "[a-zA-Z]+") String state,
                                                 @RequestParam float price,
                                              @RequestParam int squareFeet) throws SQLException {
       int homeid =homeService.createHome(streetNum,city,state,price,squareFeet);
        return ResponseEntity.ok(homeid);
    }

    @GetMapping("customer/get/{customerid}")
    @Operation(summary = "Create a purchase record", description = "Create a purchase record")
    @ApiResponse(responseCode = "200", description = "Resource Created")
    @ApiResponse(responseCode = "404", description = "Resource not found", content = @Content(schema = @Schema(implementation = Schema404.class)))
    public ResponseEntity<Home> getHome(@PathVariable int homeid){

        Home home = homeService.gethome(homeid);
        return ResponseEntity.ok(home);
    }
}
