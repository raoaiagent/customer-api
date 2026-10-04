package com.bank.controllers;

import com.bank.model.Customer;
import com.bank.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }


    @GetMapping("/customer/{customerId}")
    public Customer getCustomer(@PathVariable("customerId") String customerId){
        // GET THE CUSTOMER INFO
        // IF CUSTOMER NOT FOUND THEN RETURN 404 EXCEPTION

        return customerService.getCustomer(customerId);
    }

    @PostMapping("/customer")
    public String addCustomer(@RequestBody  Customer customer){
        // add  THE CUSTOMER INFO
        // generate cust id otherwise throw exception
        var customerId=customerService.createCustomer(customer);
        return "customer created successfully with id : "+customerId;
    }


}
