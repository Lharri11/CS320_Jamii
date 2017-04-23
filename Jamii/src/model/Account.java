package model;

import java.util.Random;

public class Account {
	private String username = null;
	private String password = null;
	private int loginId = -1;
	private String name = null;
	private String email = null;
	private String phoneNumber = null;
	private boolean locked = false;

	
	
	public Account(String user, String pass, int id, String name, String email, String phone, boolean lock){
		this.username = user;
		this.password = pass;
		this.loginId = id;
		this.name = name;
		this.email = email;
		this.phoneNumber = phone;
		this.locked = lock;
	}
	
	/*
	 * ------------------------GETTERS AND SETTERS---------------------------------
	 */
	
	public String getUsername(){
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public int getLoginId(){
		return this.loginId;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public String getPhoneNumber(){
		return this.phoneNumber;
	}

	public boolean isLocked(){
		return this.locked;
	}
	
	public boolean setUsername(String user){
		boolean valid = this.validateUsername(user);
		if(valid)
			this.username = user;
		
		return valid;
	}
	
	public boolean setPassword(String pass){
		boolean valid = this.validatePassword(pass);
		if(valid)
			this.password = pass;
		
		return valid;
	}
	
	public void setLoginId(int id){
		this.loginId = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public boolean setEmail(String email){
		boolean valid = this.validateEmail(email);
		
		if(valid)
			this.email = email;
		
		return valid;
	}
	
	public boolean setPhoneNumber(String phone){
		boolean valid = this.validatePhoneNumber(phone);
		
		if(valid)
			this.phoneNumber = phone;
		
		return valid;
	}
	
	public void lock(){
		this.locked = true;
	}
	
	public void unlock(){
		this.locked = false;
	}
	
	/*
	 * -------------------------HELPER METHODS----------------------------
	 */
		
	public int createLoginId() {
		Random r = new Random();
		this.loginId = r.nextInt(99999999);
		return this.loginId;
	}
	
	public int resetLoginId(){
		this.loginId = -1;
		return this.loginId;
	}
	
	/*
	 * -----------------------VALIDATION METHODS----------------------------
	 */
	
	//TODO
	private boolean validatePhoneNumber(String phone){
		return true;
	}
	
	//TODO
	private boolean validateEmail(String email){
		return true;
	}
	
	//TODO
	private boolean validatePassword(String pass){
		return true;
	}
	
	//TODO
	private boolean validateUsername(String user){
		return true;
	}
}
