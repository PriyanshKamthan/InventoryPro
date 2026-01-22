package com.kamthan.InventoryPro.service;

import com.kamthan.InventoryPro.dto.CustomerResponseDTO;
import com.kamthan.InventoryPro.model.Customer;

import java.util.List;

public interface CustomerService {
    public CustomerResponseDTO addCustomer(Customer customer);

    public List<CustomerResponseDTO> getAllCustomers();

    public CustomerResponseDTO updateCustomer(Long id, Customer updatedCustomer);

    public CustomerResponseDTO getCustomerById(Long id);

    public void deleteCustomer(Long id);

    public void restoreCustomer(Long id);

    public List<CustomerResponseDTO> searchCustomers(String name, String phone, String email, String gstNumber);
}
