package junit_model;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import model.Account;


public class AccountTest {
	Account a1, a2, a3;
	
    @Before
	public void setUp(){
    	//---------Same setup as BooksForSaleTest (all components needed)---------------------------------------------------------------
       
	    this.a1 = new Account("LoganH24", "abc123", 1, "LogeyBear", "Logan.Harris1995@gmail.com", "555-555-5555");
	    this.a2 = new Account("Amcdevitt97", "def456", 2, "Ally", "amcdevitt97@ycp.edu", "555-555-5555");
	    this.a3 = new Account("GRuths", "ghi789", 3, "Garry", "gruths@ycp.edu", "555-555-5555");
	    
	
		//---------Same setup as BooksForSaleTest (all components needed)----------------------------------------------------------------
	}
	
	@Test
	public void getUsernameTest(){
		assertEquals(a1.getUsername(),"LoganH24");
		assertEquals(a2.getUsername(),"Amcdevitt97");
		assertEquals(a3.getUsername(),"GRuths");
	}
	
	@Test
	public void getPasswordTest(){
		assertEquals(a1.getPassword(),"abc123");
		assertEquals(a2.getPassword(),"def456");
		assertEquals(a3.getPassword(),"ghi789");

	}
	
	@Test
	public void getLoginIdTest(){
		assertEquals(a1.getLoginId(), 1);
		assertEquals(a2.getLoginId(), 2);
		assertEquals(a3.getLoginId(), 3);
	}
	
	@Test
	public void getNameTest(){
		assertEquals(a1.getName(),"LogeyBear");
		assertEquals(a2.getName(),"Ally");
		assertEquals(a3.getName(),"Garry");
	}
	
	@Test
	public void getEmailTest(){
		assertEquals(a1.getEmail(),"Logan.Harris1995@gmail.com");
		assertEquals(a2.getEmail(),"amcdevitt97@ycp.edu");
		assertEquals(a3.getEmail(),"gruths@ycp.edu");
	}
	
}