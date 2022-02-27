package com.training.project.zero.models;

public class CustomerDetails {
	
	private String firstName;
	private String lastName;
	private CustomerBankAccount account;
	
	public CustomerDetails(String firstName, String lastName, CustomerBankAccount account) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.account = account;
	}
	
	@Override
	public String toString() {
		return "\nCustomer Information\n" +
				"First Name: " + firstName + "\n" +
				"Last Name: " + lastName + "\n" +
				account;
	}
	
	public String basicInfo() {
		return "First Name: " + firstName +
				" Last Name: " + lastName +
				" Account Number: " + account.getAccountNumber();
	}
	
	public CustomerBankAccount getAccount() {
		return account;
	}
}
