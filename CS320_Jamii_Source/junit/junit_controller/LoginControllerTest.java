package junit_controller;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import controller.GroupController;
import database.DatabaseProvider;
import database.DerbyDatabase;
import database.IDatabase;
import model.Account;
import model.Group;

public class LoginControllerTest {

	private IDatabase db = null;
	GroupController thiscontroller = new GroupController();
	
	
	@Before
	public void setUp() throws Exception {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();	
		db.dropTables();
		db.createTables();
		db.loadInitialData();
	}
	
	@Test
	public void testController(){
		assertEquals(1,1);
	}
	
	
	
	
	
	
}


