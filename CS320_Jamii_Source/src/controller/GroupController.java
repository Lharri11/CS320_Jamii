package controller;

import java.sql.SQLException;
import java.util.List;

import database.DatabaseProvider;
import database.DerbyDatabase;
import database.IDatabase;
import model.Account;
import model.Group;
import model.Post;

public class GroupController {

	private IDatabase database = null;
	
	public GroupController() {

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
		//for(int x = 0; x < groups.size(); x++){
		//	System.out.println(groups.get(x).getName());
		//}

		if (groups.isEmpty()) {
			System.out.println("No groups in database for that user");
			return null;
		}
		else {
			return groups;
		}			

	}
	
	public List<Post> getpostsbyUserandGroups(String user){
		return null;
	}
	
}
