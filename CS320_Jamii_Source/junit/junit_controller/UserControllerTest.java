package junit_controller;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import controller.UserController;
import database.DatabaseProvider;
import database.DerbyDatabase;
import database.IDatabase;
import model.Account;
import model.Group;

public class UserControllerTest {

	private IDatabase db = null;
	private UserController thiscontroller = new UserController();
	
	
	@Before
	public void setUp() throws Exception {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();	
		db.dropTables();
		db.createTables();
		db.loadInitialData();
		
	}
	
	
	
	@Test
	public void testreturnaccountfromUsername(){
		Account admin1 = new Account("admin1", "password1", -1, "a_name1", "admin1@ycp.edu", "717-123-4567");
		Account test = thiscontroller.returnAccountForUsername("admin1");
		assertEquals(admin1.getName(), test.getName());
		assertEquals(admin1.getEmail(), test.getEmail());
		assertEquals(admin1.getLoginId(), test.getLoginId());
		assertEquals(admin1.getPassword(), test.getPassword());
		assertEquals(admin1.getPhoneNumber(), test.getPhoneNumber());
		assertEquals(admin1.getUsername(), test.getUsername());
	}
	
	
	@Test
	public void testgetusersgroups() throws SQLException{
		List<Group> groups = new ArrayList<Group>();
		groups = thiscontroller.getUsersGroups("admin2");
		assertEquals(groups.get(0).getName(), "Group2");
		List<Group> testgroups = new ArrayList<Group>();
		testgroups = thiscontroller.getUsersGroups("admin1");
		assertEquals(testgroups.get(0).getName(), "Group1");
		assertEquals(testgroups.get(1).getName(), "Group2");
	}
	
	
	@Test
	public void testgetgroupbygroupname() throws SQLException{
		Group test = new Group("Group1", "Description1", 1);
		test.setGroupId(1);
		int get = thiscontroller.getGroupIDbyGroupname("Group1");
		assertEquals(test.getGroupId(), get);
	}
	
	
	
	
	
	
}


