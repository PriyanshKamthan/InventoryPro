# InventoryPro Backend

InventoryPro Backend is a production-oriented Inventory Management System built using Spring Boot. It provides secure REST APIs for managing products, customers, suppliers, purchases, sales, invoices, stock tracking, dashboard analytics, and user authentication.

## Features

### Authentication & Security

* JWT Authentication
* Role-Based Access Control (ADMIN, EMPLOYEE)
* BCrypt Password Encryption
* Protected REST APIs
* Spring Security Integration

### Product Management

* Create Product
* Update Product
* Soft Delete Product
* Restore Product
* Product Listing

### Customer Management

* Create Customer
* Update Customer
* Soft Delete Customer
* Restore Customer

### Supplier Management

* Create Supplier
* Update Supplier
* Soft Delete Supplier
* Restore Supplier

### Purchase Management

* Purchase Entry
* Purchase History
* Purchase Reversal
* Automatic Stock Updates

### Sales Management

* Sales Entry
* Sales History
* Sales Reversal
* Automatic Stock Updates

### Inventory Tracking

* Stock Movement Tracking
* Purchase Stock In
* Sales Stock Out
* Reversal Tracking
* Audit-Friendly History

### Invoice Management

* PDF Invoice Generation
* Company Branding Support
* Company Logo Support
* Downloadable Invoices

### Dashboard Analytics

* Stock Summary
* Sales Summary
* Purchase Summary
* Top Selling Products
* Recent Stock Movements

## Tech Stack

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* MySQL
* JWT
* Maven
* iText PDF

## Architecture

The application follows a layered architecture:

Controller Layer
↓
Service Layer
↓
Repository Layer
↓
Database

## Security

Authentication is implemented using JWT tokens.

Roles:

* ADMIN
* EMPLOYEE

Only ADMIN users can:

* Delete Products
* Restore Products
* Reverse Sales
* Reverse Purchases
* Create Users

## Future Enhancements

* Multi-Tenant SaaS Architecture
* Advanced Reporting
* Audit Logs
* Search & Filtering
* Pagination
* Cloud Deployment
* Email Notifications

## Author

Priyansh Kamthan
Java Spring Boot Developer
