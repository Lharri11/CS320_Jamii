package model;


import java.util.ArrayList;

import model.Account;

public class Group {
	
	private int groupId;
	private String  name, description;
	private int rating;
	private ArrayList<Account> members;
	private ArrayList<Account> moderators;
	private ArrayList<Post> posts;
	
	public Group(String name, String description, int rating){
		this.description = description;
		this.name = name;
		this.rating = rating;
		this.members = new ArrayList<Account>();
		this.moderators = new ArrayList<Account>();
		this.posts = new ArrayList<Post>();
	}
	
	public Group(){
		this.description = "";
		this.name = "";
		this.rating = 0;
		this.members = new ArrayList<Account>();
		this.moderators = new ArrayList<Account>();
		this.posts = new ArrayList<Post>();
	}
	
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setRating(int rating){
		this.rating = rating;
	}
	

	public int getGroupId() {
		return groupId;
	} 
	
	public String getName(){
		return this.name;
	}
	
	public String getDescription(){
		return this.description;
	}

	public int getRating(){
		return this.rating;
	}
	
	public ArrayList<Account> getMembers(){
		return this.members;
	}
	
	public ArrayList<Account> getModerators(){
		return this.moderators;
	}
	
	public ArrayList<Post> getPosts(){
		return this.posts;
	}
	
	public void createPost(String text){
		this.posts.add(new Post(text));
	}
	
	public void addAccount(Account account){
		this.members.add(account);
	}
	
	public void addMod(Account account){
		this.moderators.add(account);
	}
	
	public Boolean userisMod(Account account){
		if(this.moderators.contains(account)){
			return true;
		}
		else
			return false;
	}

	
	// need discussion on how to remove posts, edit posts, remove user, accept user
	
	
	
	
	
	
}
