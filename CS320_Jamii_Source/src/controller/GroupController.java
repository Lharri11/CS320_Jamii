package controller;

import java.sql.SQLException;
import java.util.ArrayList;
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

	public List<Post> getpostsbyUserandGroups(String user){
		return null;
	}

	public List<Group> getUsersGroups(String user) throws SQLException {


		List<Group> groups = database.getGroupsByUser(user);


		if (groups.isEmpty()) {
			System.out.println("No groups in database for that user");
			return null;
		}
		else {
			return groups;
		}			

	}










	public List<Group> getGroupbyID(int ID) throws SQLException {
		List<Group> groups = database.getGroupbyGroupID(ID);
		if(groups.isEmpty()){
			System.out.println("No groups in databse for that ID");
			return null;
		}
		else{
			return groups;
		}
	}
	public List<Post> getPostsbyGroupID(int ID) {
		List<Post> posts = database.getPostsbyGroupID(ID);
		if(posts.isEmpty()){
			System.out.println("No posts for gorup or invalid group");
			return null;
		}
		else{
			return posts;
		}
		
		
	}

}
