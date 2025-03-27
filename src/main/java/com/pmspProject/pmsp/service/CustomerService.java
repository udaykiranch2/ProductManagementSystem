package com.pmspProject.pmsp.service;

import java.util.Optional;

import com.pmspProject.pmsp.dto.CustomerRequest;
import com.pmspProject.pmsp.dto.CustomerResponse;

public interface CustomerService {
   
    CustomerResponse registerCustomer(CustomerRequest customer) ;
    Optional<CustomerResponse> getCustomerById(Long id);
    CustomerResponse updateCustomer(Long id, CustomerRequest customer);
}
