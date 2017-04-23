package model;

public class GroupMember {
	private int memberId;
	private int accountId;
	private int groupId;
	
	public GroupMember(){
		
	}
	
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	
	public void setGroupId(int groupId){
		this.groupId = groupId;
	}
	
	public void setAccountId(int accountId){
		this.accountId = accountId;
	}
	
	public int getMemberId() {
		return memberId;
	}
	
	public int getGroupId(){
		return this.groupId;
	}

	public int getAccountId(){
		return this.accountId;
	}
}
