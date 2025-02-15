package com.CSI5370.HomePurchaseManagementSystem;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HPMSEndpoints {

    //localhost:8080/search

    @GetMapping("/search")
    public ResponseEntity<Integer> someMethod(){
        //do stuff
    }
}
