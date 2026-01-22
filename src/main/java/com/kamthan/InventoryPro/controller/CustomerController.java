package com.kamthan.InventoryPro.controller;

import com.kamthan.InventoryPro.dto.ApiResponse;
import com.kamthan.InventoryPro.dto.CustomerResponseDTO;
import com.kamthan.InventoryPro.model.Customer;
import com.kamthan.InventoryPro.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ApiResponse<CustomerResponseDTO> addCustomer(@RequestBody Customer customer) {
        return new ApiResponse<>(
                true,
                "Customer added successfully",
                customerService.addCustomer(customer)
        );
    }

    @GetMapping
    public ApiResponse<List<CustomerResponseDTO>> getAllCustomers() {
        return new ApiResponse<>(
                true,
                "Customers fetched successfully",
                customerService.getAllCustomers()
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<CustomerResponseDTO> updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
        return new ApiResponse<>(
                true,
                "Customer updated successfully",
                customerService.updateCustomer(id, customer)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<CustomerResponseDTO> getCustomerById(@PathVariable("id") Long id) {
        return new ApiResponse<>(
                true,
                "Customer details fetched successfully",
                customerService.getCustomerById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
        return new ApiResponse<>(
                true,
                "Customer deleted successfully",
                null
        );
    }

    @PutMapping("/{id}/restore")
    public ApiResponse<Void> restoreCustomer(@PathVariable Long id) {
        customerService.restoreCustomer(id);
        return new ApiResponse(
                true,
                "Customer restored successfully",
                null);
    }

    @GetMapping("/search")
    public ApiResponse<List<CustomerResponseDTO>> searchCustomers(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "gstNumber", required = false) String gstNumber) {

        return new ApiResponse<>(
                true,
                "Customers fetched successfully",
                customerService.searchCustomers(name, phone, email, gstNumber)
        );
    }
}
