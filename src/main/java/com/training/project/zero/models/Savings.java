package com.training.project.zero.models;

public class Savings extends CustomerBankAccount {
	private static String accountType = "Savings";

	public Savings(double initialDeposit) {
		super();
		this.setBalance(initialDeposit);
	}
	
	@Override
	public String toString() {
		return "Account Type: " + accountType + " Account \n" + 
				"Account Number: " + this.getAccountNumber() + "\n" +
				"Balance: " + this.getBalance() + "\n";
				
	}

}
