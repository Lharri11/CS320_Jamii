package controller;
import model.Group;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;


import database.DatabaseProvider;
import database.DerbyDatabase;
import database.IDatabase;
 
public class SearchController {

	private IDatabase database = null;

	public SearchController() {

		// creating DB instance here
		DatabaseProvider.setInstance(new DerbyDatabase());
		database = DatabaseProvider.getInstance();		
	}

	
	public List<Group> getGroupsLike(String keyword) throws SQLException {
		System.out.println("reached controller with keyword "+keyword);
		// get the list of (Author, Book) pairs from DB
		List<Group> groups = database.getGroupsLikeKeyword(keyword);
		for(int x = 0; x < groups.size(); x++){
			System.out.println(groups.get(x).getName());
		}

		if (groups.isEmpty()) {
			System.out.println("No groups in database like that keyword");
			return null;
		}
		else {
			return groups;
		}			

	}
}


