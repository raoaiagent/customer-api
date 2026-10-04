package com.bank.service;

import com.bank.entities.CustomerEntity;
import com.bank.model.Customer;
import com.bank.repositroy.CustomerRepository;
import com.bank.util.CustomerIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerIdGenerator customerIdGenerator;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, CustomerIdGenerator customerIdGenerator) {
        this.customerRepository = customerRepository;
        this.customerIdGenerator = customerIdGenerator;
    }
    public Customer getCustomer(String customerId){
        // fetch customer info from repo
        //  convert entity obj into model obj
        var customerEntity= customerRepository.findByCustomerId(customerId);
        return ObjectUtils.isEmpty(customerEntity)?null: CustomerConverter.convertEntityToModel(customerEntity);
    }

    public String createCustomer(Customer customer){
        // convert customer obj into entity obj
        // generate customer id and verify customer id already present in db if present re-generate
        // store object into db

        var CustomerEntity=CustomerConverter.convertModelToEntity(customer);
        CustomerEntity.setCustomerId(getCustomerId());
        customerRepository.save(CustomerEntity);
        return CustomerEntity.getCustomerId();
    }

    private String getCustomerId(){
        String customerId=null;

        while(!StringUtils.hasText(customerId)){
            customerId=customerIdGenerator.generateCustomerId();
            if(!ObjectUtils.isEmpty(getCustomer(customerId))) {
                customerId=null;
            }
        }
       return customerId;
    }


    public interface CustomerConverter{

        public static  Customer convertEntityToModel(CustomerEntity entity){
            return  new Customer(entity.getCustomerId(), entity.getFirstName(), entity.getLastName());
        }

        public static  CustomerEntity convertModelToEntity(Customer customer){
            return new CustomerEntity(customer.customerId(), customer.firstName(), customer.lastName());
        }
    }
}
