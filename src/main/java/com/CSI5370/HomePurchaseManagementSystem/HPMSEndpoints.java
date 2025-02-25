package com.CSI5370.HomePurchaseManagementSystem;


import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class HPMSEndpoints {

    //localhost:8080/search

    @PostMapping("/customer/create")
    public ResponseEntity<Integer> createCustomer(@RequestBody @Pattern(regexp = "readf") String firstName,
                                                  @RequestBody @Pattern(regexp = "readfds") String lastName,
                                                  @RequestBody @Pattern(regexp = "fdasgd") String ssn,
                                                  @RequestBody @Pattern(regexp = "fdasfa") float income){

        return null;
    }

    @PostMapping("/purchase/create")
    public ResponseEntity<Integer> createPurchase(@RequestBody @Pattern(regexp = "readf") String firstName,
                                                  @RequestBody @Pattern(regexp = "readfds") String lastName,
                                                  @RequestBody @Pattern(regexp = "fdasgd") String ssn,
                                                  @RequestBody @Pattern(regexp = "fdasfa") int income){

        return null;
    }

    @PostMapping("/realtor/create")
    public ResponseEntity<Integer> createRealtor(@RequestBody @Pattern(regexp = "fdasgd") int employeenum,
                                                  @RequestBody @Pattern(regexp = "readf") String firstName,
                                                  @RequestBody @Pattern(regexp = "readfds") String lastName,
                                                  @RequestBody @Pattern(regexp = "fdasfa") float commissionRate){

        return null;
    }

    @PostMapping("/home/create")
    public ResponseEntity<Integer> createHome(@RequestBody @Pattern(regexp = "fdasgd") int streetNum,
                                                @RequestBody @Pattern(regexp = "readf") String city,
                                                 @RequestBody @Pattern(regexp = "readfds") String state,
                                                 @RequestBody @Pattern(regexp = "fdasfa") float price,
                                              @RequestBody @Pattern(regexp = "fdasfe") int squareFeet){

        return null;
    }


}
