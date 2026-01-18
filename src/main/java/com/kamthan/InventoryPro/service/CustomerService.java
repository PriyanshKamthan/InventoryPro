package com.kamthan.InventoryPro.service;

import com.kamthan.InventoryPro.model.Customer;
import com.kamthan.InventoryPro.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {
    public Customer addCustomer(Customer customer);

    public List<Customer> getAllCustomers();

    public Customer updateCustomer(Long id, Customer updatedCustomer);

    public Customer getCustomerById(Long id);

    public void deleteCustomer(Long id);
}
