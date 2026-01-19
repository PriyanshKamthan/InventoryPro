package com.kamthan.InventoryPro.controller;

import com.kamthan.InventoryPro.dto.ApiResponse;
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
    public ApiResponse<Customer> addCustomer(@RequestBody Customer customer) {
        return new ApiResponse<>(
                true,
                "Customer added successfully",
                customerService.addCustomer(customer)
        );
    }

    @GetMapping
    public ApiResponse<List<Customer>> getAllCustomers() {
        return new ApiResponse<>(
                true,
                "Customers fetched successfully",
                customerService.getAllCustomers()
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return new ApiResponse<>(
                true,
                "Customer updated successfully",
                customerService.updateCustomer(id, customer)
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<Customer> getCustomerById(@PathVariable Long id) {
        return new ApiResponse<>(
                true,
                "Customer details fetched successfully",
                customerService.getCustomerById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return new ApiResponse<>(
                true,
                "Customer deleted successfully",
                null
        );
    }

    @GetMapping("/search")
    public ApiResponse<List<Customer>> searchCustomers(
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
