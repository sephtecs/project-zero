package com.training.project.zero.models;

public class CustomerDetails {
	
	private String firstName;
	private String lastName;
	private CustomerBankAccount account;
	private String password;
	
	public CustomerDetails(String firstName, String lastName, String password, CustomerBankAccount account) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.account = account;
	}
	
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setAccount(CustomerBankAccount account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
