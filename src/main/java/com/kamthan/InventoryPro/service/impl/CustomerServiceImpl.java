package com.kamthan.InventoryPro.service.impl;

import com.kamthan.InventoryPro.dto.CustomerResponseDTO;
import com.kamthan.InventoryPro.exception.ResourceNotFoundException;
import com.kamthan.InventoryPro.mapper.CustomerMapper;
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
    @Autowired
    private CustomerMapper customerMapper;

    public CustomerResponseDTO addCustomer(Customer customer) {
        return customerMapper.toResponseDTO(customerRepository.save(customer));
    }

    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toResponseDTO)
                .toList();
    }

    public CustomerResponseDTO getCustomerById(Long id) {
        return customerMapper.toResponseDTO(customerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Customer not found with id: " + id)));
    }

    public CustomerResponseDTO updateCustomer(Long id, Customer updatedCustomer) {
        Customer existing = customerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Customer not found with id: " + id));

        if (existing != null) {
            existing.setName(updatedCustomer.getName());
            existing.setAddress(updatedCustomer.getAddress());
            existing.setEmail(updatedCustomer.getEmail());
            existing.setGstNumber(updatedCustomer.getGstNumber());
            existing.setPhone(updatedCustomer.getPhone());
            return customerMapper.toResponseDTO(customerRepository.save(existing));
        }
        return null;
    }

    public void deleteCustomer(Long id) {
        getCustomerById(id);
        customerRepository.deleteById(id);
    }

    @Override
    public List<CustomerResponseDTO> searchCustomers(String name, String phone, String email, String gstNumber) {
        if (name != null && !name.isBlank()) {
            return customerRepository.findByNameContainingIgnoreCase(name)
                    .stream()
                    .map(customerMapper::toResponseDTO)
                    .toList();
        }
        if (email != null && !email.isBlank()) {
            return customerRepository.findByEmailContainingIgnoreCase(email)
                    .stream()
                    .map(customerMapper::toResponseDTO)
                    .toList();
        }
        if (gstNumber != null && !gstNumber.isBlank()) {
            return customerRepository.findByGstNumberContainingIgnoreCase(gstNumber)
                    .stream()
                    .map(customerMapper::toResponseDTO)
                    .toList();
        }
        if (phone != null && !phone.isBlank()) {
            return customerRepository.findByPhoneContaining(phone)
                    .stream()
                    .map(customerMapper::toResponseDTO)
                    .toList();
        }
        return getAllCustomers();
    }
}
