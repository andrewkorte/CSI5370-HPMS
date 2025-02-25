package com.CSI5370.HomePurchaseManagementSystem;


import com.CSI5370.HomePurchaseManagementSystem.Services.CustomerService;
import com.CSI5370.HomePurchaseManagementSystem.Services.HomeService;
import com.CSI5370.HomePurchaseManagementSystem.Services.PurchaseService;
import com.CSI5370.HomePurchaseManagementSystem.Services.RealtorService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
public class HPMSEndpoints {

    @Autowired
    CustomerService customerService;

    @Autowired
    HomeService homeService;

    @Autowired
    RealtorService realtorService;

    @Autowired
    PurchaseService purchaseService;

    //localhost:8080/search

    @PostMapping("/customer/create")
    public ResponseEntity<Integer> createCustomer(@RequestParam @Pattern(regexp = "[a-zA-Z]+") String firstName,
                                                  @RequestParam @Pattern(regexp = "[a-zA-Z]+") String lastName,
                                                  @RequestParam @Pattern(regexp = "[0-9]{2}-[0-9]{3}-[0-9]{4}") String ssn,
                                                  @RequestParam float income) throws SQLException {
        int custId = customerService.createCustomer(firstName, lastName, ssn, income);
        return ResponseEntity.ok(custId);
    }

    @PostMapping("/purchase/create")
    public ResponseEntity<Integer> createPurchase(@RequestParam int customerId,
                                                  @RequestParam int realtorId,
                                                  @RequestParam int homeId,
                                                  @RequestParam int loan,
                                                  @RequestParam int downPayment){

        int purchaseId = purchaseService.createPurchase(customerId, realtorId, homeId, loan, downPayment);
        return ResponseEntity.ok(purchaseId);
    }

    @PostMapping("/realtor/create")
    public ResponseEntity<Integer> createRealtor(@RequestParam int employeenum,
                                                  @RequestParam @Pattern(regexp = "[a-zA-Z]+") String firstName,
                                                  @RequestParam @Pattern(regexp = "[a-zA-Z]+") String lastName,
                                                  @RequestParam float commissionRate) throws SQLException {
        int realId=realtorService.createRealtor( employeenum, firstName, lastName, commissionRate);
        return ResponseEntity.ok(realId);
    }

    @PostMapping("/home/create")
    public ResponseEntity<Integer> createHome(@RequestParam int streetNum,
                                                @RequestParam @Pattern(regexp = "[a-zA-Z]+") String city,
                                                 @RequestParam @Pattern(regexp = "[a-zA-Z]+") String state,
                                                 @RequestParam float price,
                                              @RequestParam int squareFeet) throws SQLException {
       int homeid =homeService.createHome(streetNum,city,state,price,squareFeet);
        return ResponseEntity.ok(homeid);
    }


}
