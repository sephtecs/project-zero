package com.training.project.zero.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.training.project.zero.BankAccountType;
import com.training.project.zero.models.Checking;
import com.training.project.zero.models.CustomerBankAccount;
import com.training.project.zero.models.CustomerDetails;
import com.training.project.zero.models.Savings;
import com.training.project.zero.utility.DBConnection;

public class BankAccountDAOImpl implements BankAccountDAO {
	
	Connection connection = DBConnection.getConnection();

	public int AddAccount(String firstName, String lastName, String password, BankAccountType accountType, Double balance) {
		int userId = -1;
		int accountId = -1;
		
		try {
			connection.setAutoCommit(false);
			//Add User
			String addUserSQL = "insert into users(FirstName, LastName, Password) values (?,?,?)";
			PreparedStatement addUser = connection.prepareStatement(addUserSQL, Statement.RETURN_GENERATED_KEYS);
			addUser.setString(1, firstName);
			addUser.setString(2, lastName);
			addUser.setString(3, password);
			addUser.executeUpdate();
			
			ResultSet addUserResults = addUser.getGeneratedKeys();
			if(addUserResults.next()) {
				userId = addUserResults.getInt(1);
			}
			
			//Add Account
			String addAccountSql = "insert into accounts(Type, Balance) values (?,?)";
			PreparedStatement addAccount = connection.prepareStatement(addAccountSql, Statement.RETURN_GENERATED_KEYS);
			addAccount.setString(1, accountType.name());
			addAccount.setDouble(2, balance);
			addAccount.executeUpdate();
			
			ResultSet addAccountResults = addAccount.getGeneratedKeys();
			if (addAccountResults.next()){
				accountId = addAccountResults.getInt(1);
			}
			
			//Link User to the Account
			if(userId > 0 && accountId > 0) {
				String linkAccountSql = "insert into mappings(UserId, AccountId) values (?,?)";
				PreparedStatement linkAccount = connection.prepareStatement(linkAccountSql);
				linkAccount.setInt(1, userId);
				linkAccount.setInt(2, accountId);
				linkAccount.executeUpdate();
				connection.commit();
			} else {
				connection.rollback();
			}
			connection.close();
		} catch (SQLException e) {
			System.err.println("An error has occured: " + e.getMessage());
			e.printStackTrace();
		}
		
		
		return accountId;
	}

	public CustomerDetails GetAccount(int accountId) {
		CustomerDetails customer = null;
		Connection connection = DBConnection.getConnection();
		try { 
			String findUserSql = "select FirstName, LastName, Password, Type, Balance "
					+ "from Users a join Mappings b on a.ID = b.UserId "
					+ "join Accounts c on c.ID = b.AccountId "
					+ "where c.ID = ?";
			PreparedStatement findUser = connection.prepareStatement(findUserSql);
			findUser.setInt(1, accountId);
			ResultSet findUserResults = findUser.executeQuery();
			if(findUserResults.next()) {
				String firstName = findUserResults.getString("FirstName");
				String lastName = findUserResults.getString("LastName");
				String password = findUserResults.getString("Password");
				BankAccountType accountType = BankAccountType.valueOf(findUserResults.getString("Type"));
				double balance = findUserResults.getDouble("Balance");
				
				CustomerBankAccount account;
				if(accountType == BankAccountType.Checking) {
					account = new Checking(accountId, balance);
				} else if(accountType == BankAccountType.Savings) {
					account = new Savings(accountId, balance);
				} else {
					throw new Exception("Unknown account type");
				}
				customer = new CustomerDetails(firstName, lastName, password, account);
			} else {
				System.err.println("No user account was found for ID " + accountId);
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return customer;
	}

	public boolean UpdateAccount(int accountId, double balance) {
		boolean success = false;
		Connection connection = DBConnection.getConnection();
		try {
			String updateSql = "UPDATE Accounts SET Balance = ? WHERE ID = ?";
			PreparedStatement updateBalance = connection.prepareStatement(updateSql);
			updateBalance.setDouble(1, balance);
			updateBalance.setInt(2, accountId);
			updateBalance.executeUpdate();
			success = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return success;
	}

	public boolean DeleteAccount(int accountId) {
		boolean success = false;
		Connection connection = DBConnection.getConnection();
		try {
			String deleteSql = "DELETE Users, Accounts "
					+ "from Users a join Mappings b on a.ID = b.UserId "
					+ "join Accounts c on c.ID = b.AccountId "
					+ "where c.ID = ?";
			PreparedStatement deleteAccount = connection.prepareStatement(deleteSql);
			deleteAccount.setInt(1, accountId);
			deleteAccount.executeUpdate();
			success = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		
		return success;
	}
	
	//Get All Accounts
	ArrayList<CustomerBankAccount> GetAllAccount(){
		ArrayList<CustomerBankAccount> customers = new ArrayList<CustomerBankAccount>();
		Connection connection = DBConnection.getConnection();
		String findAllUsersSql = "select FirstName, LastName, Password, Type, Balance "
				+ "from Users a join Mappings b on a.ID = b.UserId "
				+ "join Accounts c on c.ID = b.AccountId ";
		PreparedStatement findAllUsers = connection.prepareStatement(findAllUsersSql);
		ResultSet findUserResults = findAllUsers.executeQuery();
			while (findUserResults.next()) {
				String firstName = findUserResults.getString("FirstName");
				String lastName = findUserResults.getString("LastName");
				String password = findUserResults.getString("Password");
				BankAccountType accountType = BankAccountType.valueOf(findUserResults.getString("Type"));
				double balance = findUserResults.getDouble("Balance");
				
				CustomerBankAccount account;
				if(accountType == BankAccountType.Checking) {
					account = new Checking(accountId, balance);
				} else if(accountType == BankAccountType.Savings) {
					account = new Savings(accountId, balance);
				} else {
					throw new Exception("Unknown account type");
				}
				customers.add(new Customer)addnew CustomerDetails(firstName, lastName, password, account);
			}
		return customers;
	}
}



























