package com.training.project.zero;

import java.util.ArrayList;
import java.util.Scanner;

import com.training.project.zero.models.Checking;
import com.training.project.zero.models.CustomerBankAccount;
import com.training.project.zero.models.CustomerDetails;
import com.training.project.zero.models.Savings;

public class BankMenu {
	
	Scanner scanner = new Scanner(System.in);
	BankApp bank = new BankApp();
	boolean exit;

	public static void main(String[] args) {
		BankMenu menu = new BankMenu();
		menu.runCustomerMenu();
	}
	
	// While the app has not been exited, show menu for the user:
	public void runCustomerMenu() {
		printHeader();
		while (!exit) {
			printCustomerMenu();
			int choice = getCustomerInput();
			performAction(choice);
		}
		
	}
	
	// ------------------------------- CUSTOMER MENU FUNCTIONS: ------------------------------
	
	private void printHeader() {
		System.out.println("======================================");
		System.out.println("          THE MAVEN BANK APP          ");
		System.out.println("======================================");
	}
	
	private void printCustomerMenu() {
		System.out.println("Please make a selection: ");
		System.out.println("1. Create a new account");
		System.out.println("2. Make a deposit");
		System.out.println("3. Make a withdrawal");
		System.out.println("4. Wire a transfer");
		System.out.println("5. View account balances");
		System.out.println("6. Exit");
	}
	
	private int getCustomerInput() {
		int choice = -1;
		do {
			System.out.print("Please enter your choice: ");
			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Invalid selection. Numbers only.");
			}
			if (choice < 1 || choice > 6) {
				System.out.println("Choice outside of range. Please choose again");
			}
		} while (choice < 1 || choice > 6);
		
		return choice;
	}
	
	private void performAction(int choice) {
		switch (choice) {
		
		case 1:
			createAnAccount();
			break;
		case 2:
			makeADeposit();
			break;
		case 3:
			makeAWithdrawal();
			break;
		case 4:
			wireATransfer();
			break;
		case 5:
			viewBalances();
			break;
		case 6:
			System.out.println("For all your banking needs, trust in Maven. Please come again, thank you.");
			System.exit(6);
			break;
		default:
			System.out.println("An error has occured.");
		}
	}

	private void createAnAccount() {
		String firstName;
		String lastName;
		String accountType = "";
		double initialDeposit = 0;
		boolean valid = false;
		
		while (!valid) {
			System.out.print("Please enter an account type (checking or savings): ");
			accountType = scanner.nextLine();
			if (accountType.equalsIgnoreCase("checking") || accountType.equalsIgnoreCase("savings")) {
				valid = true;
			} else {
				System.out.println("Invalid account type. Please enter checking or savings.");
			}	
		}
		
		System.out.print("Please enter your first name: ");
		firstName = scanner.nextLine();
		
		System.out.print("Please enter your last name: ");
		lastName = scanner.nextLine();
		
		valid = false;
		while (!valid) {
			System.out.print("Please enter an initial deposit: ");
			
			try {
				initialDeposit = Double.parseDouble(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Must be a number.");
			}
			
			if (accountType.equalsIgnoreCase("checking")) {
				if (initialDeposit < 100) {
					System.out.println("Checking accounts require a minimum of $100 to open.");
				} else {
					valid = true;
				}
			} else if (accountType.equalsIgnoreCase("savings")) {
				if (initialDeposit < 50) {
					System.out.println("Savings accounts require a minimum of $50 to open.");
				} else {
					valid = true;
				}
			}
		}
		
		CustomerBankAccount account;
		if (accountType.equalsIgnoreCase("checking")) {
			account = new Checking(initialDeposit);
		} else {
			account = new Savings(initialDeposit);
		}
		CustomerDetails customer = new CustomerDetails(firstName, lastName, account);
		bank.addCustomer(customer);
	}

	private void makeADeposit() {
		int account = selectAccount();
		if (account >= 0) {
			System.out.println("How much money would you like to deposit today?: ");
			double amount = 0;
			try {
				amount = Double.parseDouble(scanner.nextLine());
			} catch (NumberFormatException e) {
				amount = 0;
			}
			bank.getCustomer(account).getAccount().deposit(amount);
		}
	}

	private int selectAccount() {
		ArrayList<CustomerDetails> customers = bank.getCustomers();
		if(customers.size() <= 0) {
			System.out.println("No customers at your bank.");
			return -1;
		}
		System.out.print("Select an account: ");
		for (int i = 0; i < customers.size(); i++) {
			System.out.println((i + 1) + ") " + customers.get(i).basicInfo());
		}
		int account = 0;
		System.out.print("Please enter your selection: ");
		try {
			account = Integer.parseInt(scanner.nextLine()) - 1;
		} catch (NumberFormatException e) {
			account = -1;
		}
		if(account < 0 || account > customers.size()) {
			System.out.println("Invalid account selected.");
			account = -1;
		}
		return account;
	}

	private void makeAWithdrawal() {
		int account = selectAccount();
		if (account >= 0) {
			System.out.println("How much would you like to withdraw?: ");
			double amount = 0;
			try {
				amount = Double.parseDouble(scanner.nextLine());
			} catch (NumberFormatException e) {
				amount = 0;
			}
			bank.getCustomer(account).getAccount().withdraw(amount);
		}
	}

	private void wireATransfer() {
		// TODO Auto-generated method stub
		
	}

	private void viewBalances() {
		int account = selectAccount();
		if (account >= 0) {
			System.out.println(bank.getCustomer(account).getAccount());
		}
	}

}









































