package controller;

import database.DatabaseProvider;
import database.DerbyDatabase;
import database.IDatabase;
import model.Account;

public class SignupController {
	
	private IDatabase database = null;

	public SignupController(){
		DatabaseProvider.setInstance(new DerbyDatabase());
		database = DatabaseProvider.getInstance();
	}
	
	public boolean createAccount(Account account){
		return this.database.insertNewAccountIntoDatabase(account);
	}
}
