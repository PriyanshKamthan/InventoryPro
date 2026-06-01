package com.kamthan.InventoryPro.service.impl;

import com.kamthan.InventoryPro.dto.CustomerResponseDTO;
import com.kamthan.InventoryPro.dto.PageResponse;
import com.kamthan.InventoryPro.exception.InvalidRequestException;
import com.kamthan.InventoryPro.exception.ResourceNotFoundException;
import com.kamthan.InventoryPro.mapper.CustomerMapper;
import com.kamthan.InventoryPro.model.Customer;
import com.kamthan.InventoryPro.repository.CustomerRepository;
import com.kamthan.InventoryPro.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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

    @Transactional
    public void deleteCustomer(Long id) {
        getCustomerById(id);
        customerRepository.deleteById(id);
        log.info("Customer soft deleted | id={}", id);
    }

    @Transactional
    public void restoreCustomer(Long id) {
        Customer customer = customerRepository.findByIdIncludingDeleted(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        if (customer.getActive()) {
            throw new InvalidRequestException("Customer is already active");
        }

        customer.setActive(true);
        customer.setDeletedAt(null);

        customerRepository.save(customer);
        log.info("Customer restored | id={}", id);
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

    @Override
    public PageResponse<CustomerResponseDTO> getCustomers(int page, int size) {
        log.info("Fetching customers: page={}, size={}", page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAll(pageable);

        List<CustomerResponseDTO> content = customerPage.getContent()
                        .stream()
                        .map(customerMapper::toResponseDTO)
                        .toList();

        return new PageResponse<>(
                content,
                customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.getNumber()
        );
    }
    @Override
    public PageResponse<CustomerResponseDTO> searchCustomersPaged(String keyword, int page, int size) {
        log.info("Searching customers | keyword={} | page={} | size={}", keyword, page, size);

        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage;

        if (keyword == null || keyword.isBlank()) {
            customerPage = customerRepository.findAll(pageable);
        } else {
            customerPage = customerRepository
                            .findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrGstNumberContainingIgnoreCaseOrPhoneContaining(
                                    keyword,
                                    keyword,
                                    keyword,
                                    keyword,
                                    pageable
                            );
        }

        List<CustomerResponseDTO> content = customerPage.getContent()
                        .stream()
                        .map(customerMapper::toResponseDTO)
                        .toList();

        return new PageResponse<>(
                content,
                customerPage.getTotalElements(),
                customerPage.getTotalPages(),
                customerPage.getNumber()
        );
    }
}
