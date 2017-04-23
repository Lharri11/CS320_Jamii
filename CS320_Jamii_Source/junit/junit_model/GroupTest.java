package junit_model;

import static org.junit.Assert.*;

import model.Account;
import java.util.ArrayList;

import org.junit.Test;

import model.Group;
import model.Post;

public class GroupTest {
	
	@Test
	public void test() {
		//does NOT test for requests, requests not complete yet.
		
		//testing variables
		Group group = new Group("a group", "0", 0);
		Account testAccount = new Account(null, null, 0, null, null, null);
		ArrayList<Account> userlist = new ArrayList<Account>(); 
		userlist.add(testAccount);
		Post testpost = new Post("a post");
		ArrayList<Post> testposts = new ArrayList<Post>();
		testposts.add(testpost);
		
		//actual tests
		org.junit.Assert.assertEquals(group.getDescription(), "a group");
		org.junit.Assert.assertEquals(group.getRating(), 0);
		group.setDescription("a new group");
		org.junit.Assert.assertEquals(group.getDescription(), "a new group");
		group.setRating(11);
		org.junit.Assert.assertEquals(group.getRating(), 11);
		group.addAccount(testAccount);
		org.junit.Assert.assertEquals(group.getMembers(), userlist);
		org.junit.Assert.assertFalse(group.userisMod(testAccount));
		group.addMod(testAccount);
		org.junit.Assert.assertTrue(group.userisMod(testAccount));
		org.junit.Assert.assertEquals(group.getModerators(), userlist);
		group.createPost("a post");
		org.junit.Assert.assertFalse(group.getPosts().isEmpty());
		
	}

}
