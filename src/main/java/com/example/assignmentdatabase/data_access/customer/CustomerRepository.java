package com.example.assignmentdatabase.data_access.customer;
import com.example.assignmentdatabase.models.Customer;
import com.example.assignmentdatabase.models.CustomerCountry;
import com.example.assignmentdatabase.models.CustomerGenre;
import com.example.assignmentdatabase.models.CustomerSpender;

import java.util.ArrayList;

//Customer repository interface

public interface CustomerRepository {
    public ArrayList <Customer> selectAllCustomers();
    public Customer selectSpecificCustomerById(int customerId);
    public Customer selectCustomerByName(String firstName);
    public ArrayList <Customer> selectPageOfCustomers(String limit, String offset);
    public Boolean addCustomer(Customer customer);
    public Boolean updateCustomer(Customer customer);
    public ArrayList<CustomerCountry> returnCustomerCountry();
    public ArrayList<CustomerSpender> returnCustomerSpender();
    public ArrayList<CustomerGenre> getCustomerGenre();


}
