package com.training.project.zero.dao;

import com.training.project.zero.BankAccountType;
import com.training.project.zero.models.CustomerDetails;

public interface BankAccountDAO {
	
	//CRUD OPERATIONS:
	
	//1. Create
	int AddAccount (String firstName, String lastName, String password, BankAccountType accountType, Double balance);
	
	//2. Read
	CustomerDetails GetAccount(int accountId);
	
	//3. Update
	boolean UpdateAccount(int accountId, double balance);
	
	//4. Delete
	boolean DeleteAccount(int accountId);
}
