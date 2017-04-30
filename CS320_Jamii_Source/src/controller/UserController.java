package controller;
import model.Group;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import database.DatabaseProvider;
import database.DerbyDatabase;
import database.IDatabase;
import model.Account;
import model.ObjectHandler;
 
public class UserController {

	private IDatabase database = null;

	public UserController() {

		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		database = DatabaseProvider.getInstance();		
	}

	public Account returnAccountForUsername(String username){
		return this.database.queryForUserAccountByUsername(username);
	}
	
	public List<Group> getUsersGroups(String user) throws SQLException {

		
		// get the list of (Author, Book) pairs from DB
		List<Group> groups = database.getGroupsByUser(user);
		System.out.println("Users Groups:");
		for(int x = 0; x < groups.size(); x++){
			System.out.println(groups.get(x).getName());
		}

		if (groups.isEmpty()) {
			System.out.println("No groups in database for that user");
			return null;
		}
		else {
			return groups;
		}			

	}
	
	public int getGroupIDbyGroupname(String name) throws SQLException{
		List<Group> group = database.getGroupbyGroupName(name);
		int groupID = 0;
		groupID = group.get(0).getGroupId();
		System.out.println("Redirecting to "+groupID);
		return groupID;
		
	}
	
}


