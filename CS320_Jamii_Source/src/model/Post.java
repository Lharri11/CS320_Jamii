package model;

public class Post {

	private int postId;
	private int accountId;
	private String text;
	private int groupID;
	// date time at later date
	// image or picture
	
	public Post(String text){
		this.text = text;
	}
	
	public Post(){
		this.text = "";
	}
	
	public void setPostId(int postId) {
		this.postId = postId;
	}
	
	public void setAccountId(int accountId){
		this.accountId = accountId;
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public void setGroupID(int ID){
		this.groupID = ID;
	}
	
	public int getPostId() {
		return postId;
	}
	
	public int getAccountId(){
		return this.accountId;
	}
	
	//getters
	public String getText(){
		return this.text;
	}
	
	public int getgroupID(){
		return this.groupID;
	}
	
}
