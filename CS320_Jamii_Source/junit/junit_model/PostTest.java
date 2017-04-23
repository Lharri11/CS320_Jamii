package junit_model;

import static org.junit.Assert.*;

import model.Account;
import org.junit.Test;

import model.Post;

public class PostTest {

	@Test
	public void test() {
		//testing variables
		String text = "this is a post";
		Post post = new Post(text);
		org.junit.Assert.assertEquals(post.getText(), "this is a post");
		post.setText("new post");
		org.junit.Assert.assertEquals(post.getText(), "new post");	
	}
}
