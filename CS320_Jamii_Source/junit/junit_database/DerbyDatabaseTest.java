package junit_database;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import database.DatabaseProvider;
import database.DerbyDatabase;
import database.IDatabase;
import model.Account;
import model.Group;

public class DerbyDatabaseTest {

	private IDatabase db = null;
	
	@Before
	public void setUp() throws Exception {
		
		DatabaseProvider.setInstance(new DerbyDatabase());
		db = DatabaseProvider.getInstance();		
	}
	
	
	@Test
	public void testqueryForLoginIdByUsername() {
		System.out.println("Testing: queryforloginidbyusername");
		int id = -1;
		int test = db.queryForLoginIdByUsername("admin1");
		assertEquals(id, test);
	}
	
	@Test //need to add data so that a group member has more than one group	//This test also calls loadGroup method
	public void testgetGroupsByUser() throws SQLException{
		System.out.println("Testing: getGroupsByUser");
		List<Group> groups = new ArrayList<Group>();
		groups = db.getGroupsByUser("admin2");
		assertEquals(groups.get(0).getName(), "Group2");
	}

	@Test //Need to fix commas after phone number	//This test also calls loadaccount method
	public void testqeueryForUserAccountsByUsername(){
		System.out.println("Testing: queryForUserAccountByUsername");
		Account admin1 = new Account("admin1", "password1", -1, "a_name1", "admin1@ycp.edu", "717-123-4567,,,,");
		Account test = db.queryForUserAccountByUsername("admin1");
		assertEquals(admin1.getName(), test.getName());
		assertEquals(admin1.getEmail(), test.getEmail());
		assertEquals(admin1.getLoginId(), test.getLoginId());
		assertEquals(admin1.getPassword(), test.getPassword());
		assertEquals(admin1.getPhoneNumber(), test.getPhoneNumber());
		assertEquals(admin1.getUsername(), test.getUsername());
	}
	
	@Test
	public void testqueryForPasswordByUsername(){
		System.out.println("Testing: queryForPasswordByUsername");
		String pass = "password2";
		String test = db.queryForPasswordByUsername("admin2");
		assertEquals(pass, test);
		
	}
	
	@Test	//if false - then verifyAccountExistsByUsername is covered
	public void testupdateAccountbyusername(){
		try{
		System.out.println("Testing: updateAccountbyusername");
		Account admin1s = new Account("admin1s", "password1s", -1,"a_name1", "admin1@ycp.edu", "717-123-4567");
		boolean passed = db.updateAccountByUsername("admin1", admin1s);
		boolean failed = db.updateAccountByUsername("asdf", admin1s);
		assertEquals(passed, true);
		assertEquals(failed, false);
		}
		finally{
			Account reset = new Account("admin1", "password1", -1, "a_name1", "admin1@ycp.edu", "717-123-4567,,,,");
			db.updateAccountByUsername("admin1s", reset);
			System.out.println("Passsed");
		}
	}
	
	
	@Test
	public void testinsertNewAccountIntoDatabase(){
		Random rand = new Random();
		int r = rand.nextInt(1000000);
		Account newb = new Account("ASDF", "ASDF", r, "Adolph", "derfurer@germany.gov", "666-666-6666");
		boolean passed = db.insertNewAccountIntoDatabase(newb);
		assertEquals(passed, true);
		
		
	}
	
	@Test
	public void testGetGroupsLikeKeyword(){
		
		System.out.println("Testing: getGroupsLikeKeyword");
		String keyword = "Group2";
		String test = db.getGroupsLikeKeyword(keyword).get(0).getName();
		assertEquals(keyword, test);
		
	}
	
	
	
	
	
	//Full code coverage on the private methods still need additional testing
	//Need to test inflate account and it needs to be called in the derbydatabase
	//Need to test loadGroupMember and it need to be called in the derbydatabase
	//Need to test getAccountFromUserId and it need to be called in the derbydatabase
	
	//Should probably have testing on 
	//createtables
	//droptables
	//load initial data
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}


