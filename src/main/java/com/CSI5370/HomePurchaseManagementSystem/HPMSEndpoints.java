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
                                                  @RequestBody @Pattern(regexp = "fdasfa") int income){

        return null;
    }


//    @GetMapping("/search/{accountNumber}")
//    public ResponseEntity<Integer> someMethod(@PathVariable @Pattern(regexp = "fdasdf") String accountNumber,
//                                              @RequestParam @Pattern(regexp = "reasdg") Integer heresAnInt,
//                                              @RequestBody @Pattern(regexp = "fdafsa") Boolean heresABool){
//        //do stuff
//    }
}
