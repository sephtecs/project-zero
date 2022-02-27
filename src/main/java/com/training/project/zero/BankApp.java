package com.training.project.zero;

import java.util.ArrayList;

import com.training.project.zero.models.CustomerDetails;

public class BankApp {
	ArrayList<CustomerDetails> customers = new ArrayList<CustomerDetails>();
	
	public void addCustomer(CustomerDetails customer) {
		customers.add(customer);
	}
	
	CustomerDetails getCustomer(int account) {
		return customers.get(account);
	}
	
	ArrayList<CustomerDetails> getCustomers(){
		return customers;
	}

}
