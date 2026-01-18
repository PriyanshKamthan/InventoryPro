package com.kamthan.InventoryPro.service.impl;

import com.kamthan.InventoryPro.exception.ResourceNotFoundException;
import com.kamthan.InventoryPro.model.Customer;
import com.kamthan.InventoryPro.repository.CustomerRepository;
import com.kamthan.InventoryPro.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Customer not found with id: " + id));
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existing = getCustomerById(id);

        if (existing != null) {
            existing.setName(updatedCustomer.getName());
            existing.setAddress(updatedCustomer.getAddress());
            existing.setEmail(updatedCustomer.getEmail());
            existing.setGstNumber(updatedCustomer.getGstNumber());
            existing.setPhone(updatedCustomer.getPhone());

            return customerRepository.save(existing);
        } else {
            return null;
        }

    }

    public void deleteCustomer(Long id) {
        getCustomerById(id);
        customerRepository.deleteById(id);
    }
}
