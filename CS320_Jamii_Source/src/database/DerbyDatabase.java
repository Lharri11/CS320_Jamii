package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import database.DBUtil;
import database.DerbyDatabase;
import database.InitialData;
import database.PersistenceException;
import database.DatabaseProvider;
import model.Account;
import model.Group;
import model.GroupMember;
import model.Post;

public class DerbyDatabase implements IDatabase {
	static {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (Exception e) {
			throw new IllegalStateException("Could not load Derby driver");
		}
	}
	
	private interface Transaction<ResultType> {
		public ResultType execute(Connection conn) throws SQLException;
	}

	private static final int MAX_ATTEMPTS = 10;
	
	private interface Query<ReturnType>{
		public ReturnType query(Connection conn) throws SQLException;
	}

	/* ----------------------------------------------Query Functions---------------------------------------------- */
	 
	public int queryForLoginIdByUsername(String username){
		try{
			return doQueryLoop(new Query<Integer>(){
				@Override
				public Integer query(Connection conn) throws SQLException{
					PreparedStatement stmt = null;
					ResultSet set = null;
					int loginId = -1;
					try{
						stmt = conn.prepareStatement(
								" SELECT login_id FROM accounts "
								+ " WHERE username = ?");
						stmt.setString(1, username);
						set = stmt.executeQuery();
						
						if(set.next()){
							loginId = set.getInt(1);
						}
					}finally{
						DBUtil.closeQuietly(stmt);
						DBUtil.closeQuietly(set);
					}
					return loginId;
				}
			});
		}catch(SQLException e){
			System.out.println("queryForLoginIdByUsername: "+e.getMessage());
			return -1;
		}
	}
	
	public boolean updateAccountByUsername(String username, Account account){
		try{
			return doQueryLoop(new Query<Boolean>(){
				@Override 
				public Boolean query(Connection conn)throws SQLException{
					if(verifyAccountExistsByUsername(conn,username))
						return updateAccountByUsername(conn, username,account);
					
					else 
						return false;
				}
			});
		}catch(SQLException e){
			System.out.println("updateAccountByUsername: "+e.getMessage());
			return false;
		}
	}
	
	public List<Group> getGroupsByUser(final String user){
		return executeTransaction(new Transaction<List<Group>>() {
			@Override
			public List<Group> execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				ResultSet set = null;
				
				try{
					
					stmt1 = conn.prepareStatement(
							"select groups.group_id, groups.name, groups.description, groups.rating from groups, accounts, groupMembers where accounts.username = ? and accounts.account_id = groupMembers.account_id and groupMembers.group_id = groups.group_id"
							);
					stmt1.setString(1, user);
					
					set = stmt1.executeQuery();
					
					List<Group> returnGroups = new ArrayList<Group>();
					Boolean found = false;

					while(set.next()) {
						found = true;
						Group group = new Group();
						
						loadGroup(group, set, 1);
						returnGroups.add(group);
					}

					if (!found) {
						System.out.println("<" + user + "> is not in the database");
					}
					return returnGroups;
				}	finally{
					DBUtil.closeQuietly(set);
					DBUtil.closeQuietly(stmt1);		
				}
			}
		});
	}

	public List<Group> getGroupsLikeKeyword(String keyword){
		return executeTransaction(new Transaction<List<Group>>() {
			@Override
			public List<Group> execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				ResultSet set = null;
				System.out.println("reached DB with keyword "+ keyword);
				try{
					
					stmt1 = conn.prepareStatement(
							"select groups.group_id, groups.name, groups.description, groups.rating from groups where groups.name like ? "
							);
					stmt1.setString(1, "%"+keyword+"%");
					
					set = stmt1.executeQuery();
					
					List<Group> returnGroups = new ArrayList<Group>();
					Boolean found = false;

					while(set.next()) {
						found = true;
						Group group = new Group();
						
						loadGroup(group, set, 1);
						returnGroups.add(group);
					}

					if (!found) {
						System.out.println("<" + keyword + "> not found as a valid group");
					}
					return returnGroups;
				}	finally{
					DBUtil.closeQuietly(set);
					DBUtil.closeQuietly(stmt1);		
				}
			}
		});
	}
	
	public String queryForPasswordByUsername(String username){
		try{
			return doQueryLoop(new Query<String>(){
				@Override 
				public String query(Connection conn) throws SQLException{
					String password = null;
					password = getPasswordByUsername(conn,username);
					return password;
				}
			});
		} catch(SQLException e){
			System.out.println("queryForPasswordByUsername: "+e.getMessage());
			return null;
		}
	}
	
	public Account queryForUserAccountByUsername(String username){
		try{
			return doQueryLoop(new Query<Account>(){
				@Override
				public Account query(Connection conn) throws SQLException{
					Account account = null;
					if(verifyAccountExistsByUsername(conn, username)){
						account = getAccountFromUsername(conn,username);
					}
					return account;
				}
			});
		}catch(SQLException e){
			System.out.println("queryForUserAccountByUsername: "+e.getMessage());
			return null;
		}
	}
	
	public boolean insertNewAccountIntoDatabase(Account account){
		try{
			return doQueryLoop(new Query<Boolean>(){
				@Override
				public Boolean query(Connection conn) throws SQLException{
					boolean success = false;
					if(!verifyAccountExistsByUsername(conn, account.getUsername())){
						if(insertAccountIntoAccounts(conn,account));
							success = true;
					}
					return success;
				}
			});
		}catch(SQLException e){
			System.out.println("insertNewAccountIntoDatabase: "+e.getMessage());
			return false;
		}
	}
	
	
	
	public void removeMemberFromGroup(Account account, Group group){
		
			//TODO : sql query remove row from group where account equals parameter
	}
	
	
	

	/*
	 * -----------------------HELPER METHODS FOR STREAMLINING SQL QUERIES----------------------------------------------------
	 */	
	
	private boolean updateAccountByUsername(Connection conn, String username, Account account) throws SQLException{
		boolean success = false;
		PreparedStatement stmt = null;
		PreparedStatement stmt2 = null;
		ResultSet set = null;
		try{
			stmt = conn.prepareStatement(
					"UPDATE accounts "
					+ " SET username = ?, password = ?, login_id = ?, name = ?, email = ?, phonenumber = ? "
					+ " WHERE username = ? ");
			stmt.setString(1, account.getUsername());
			stmt.setString(2, account.getPassword());
			stmt.setInt(3, account.getLoginId());
			stmt.setString(4, account.getName());
			stmt.setString(5, account.getEmail());
			stmt.setString(6, account.getPhoneNumber());
			stmt.setString(7, username);
			stmt.executeUpdate();
			
			//get user_id
			stmt2 = conn.prepareStatement(
					"SELECT account_id FROM accounts "
					+ " WHERE username = ?");
			stmt2.setString(1, account.getUsername());
			set = stmt2.executeQuery();
			
			
				success = true;
			
		}finally{
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(stmt2);
			DBUtil.closeQuietly(set);
		}
		return success;
	}
	
	private Account getAccountFromUsername(Connection conn, String username) throws SQLException{
		Account account = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		try{
			stmt = conn.prepareStatement(
					" SELECT * FROM accounts "
					+" WHERE username=?");
			stmt.setString(1, username);
			
		
			set = stmt.executeQuery();
			set.next();
			
			account = new Account(username, username, 0, username, username, username);
			loadAccount(account, set, 1);
			
		}finally{
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(set);
		}
		return account;
	}
	
	private Account getAccountFromUserId(Connection conn, int userId) throws SQLException{
		Account account = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		try{
			stmt = conn.prepareStatement(
					" SELECT * FROM accounts "
					+" WHERE user_id=?");
			stmt.setInt(1, userId);
			
			set = stmt.executeQuery();
			
		}finally{
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(set);
		}
		return account;
	}
	
	private String getPasswordByUsername(Connection conn,String username) throws SQLException{
		String password = null;
		PreparedStatement stmt = null;
		ResultSet set = null;
		try{
			stmt = conn.prepareStatement(
					" SELECT password FROM accounts WHERE username=? ");
			stmt.setString(1,username);
			set = stmt.executeQuery();
			
			if(set.next()){
				password = set.getString(1);
			}
		}finally{
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(set);
		}
		return password;
	}
	
	private boolean insertAccountIntoAccounts(Connection conn, Account account) throws SQLException{
		boolean success = false;
		PreparedStatement stmt1 = null;
		PreparedStatement stmt2 = null;
		ResultSet set = null;
		
		try{
			stmt1 = conn.prepareStatement(
					"INSERT INTO accounts (username, password, login_id, name, email, phone_number) "
					+ " VALUES(?,?,?,?,?,?,?)");
			stmt1.setString(1, account.getUsername());
			stmt1.setString(2, account.getPassword());
			stmt1.setInt(3, account.getLoginId());
			stmt1.setString(4, account.getName());
			stmt1.setString(5, account.getEmail());
			stmt1.setString(6, account.getPhoneNumber());
			
			stmt1.executeUpdate();
			
			stmt2 = conn.prepareStatement(
					"SELECT account_id FROM accounts "
					+ " WHERE username = ?");
			stmt2.setString(1, account.getUsername());
			set = stmt2.executeQuery();
			
		}finally{
			DBUtil.closeQuietly(stmt1);
			DBUtil.closeQuietly(stmt2);
			DBUtil.closeQuietly(set);
		}
		return success;
	}
	
	private boolean verifyAccountExistsByUsername(Connection conn, String username) throws SQLException{
		boolean registered = false;
		PreparedStatement stmt = null;
		ResultSet set = null;
		
		try{
			stmt = conn.prepareStatement(
					"SELECT * from accounts WHERE username=? ");
			stmt.setString(1, username);
			set = stmt.executeQuery();
			if(set.next()){
				registered = true;
			}
		}finally{
			DBUtil.closeQuietly(stmt);
			DBUtil.closeQuietly(set);
		}
		return registered;
	}

	private Account inflateAccount(ResultSet set, int index) throws SQLException{
		Account account = new Account(null, null, index, null, null, null);
		account.setUsername(set.getString(index++));
		account.setPassword(set.getString(index++));
		account.setLoginId(set.getInt(index++));
		account.setName(set.getString(index++));
		account.setEmail(set.getString(index++));
		account.setPhoneNumber(set.getString(index++));
		
		return account;
	}
	
	/* ---------------------------- ------------------------------*/
	
	private <ReturnType> ReturnType doQueryLoop(Query<ReturnType> query) throws SQLException{
		Connection conn = connect();

		ReturnType ret = null;
		int times = 0;
		boolean done = false;
		try{
			while(!done && times < MAX_ATTEMPTS){
				try{
					ret = query.query(conn);
					conn.commit();
					done = true;
				}catch(SQLException e){
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						times++;
					} else {
						throw e;
					}
				}
			}

			if (!done) {
				throw new SQLException("Query Failed, TIMEOUT. ");
			}
			return ret;
		}finally{
			DBUtil.closeQuietly(conn);
		}
	}
	
	public<ResultType> ResultType executeTransaction(Transaction<ResultType> txn) {
		try {
			return doExecuteTransaction(txn);
		} catch (SQLException e) {
			throw new PersistenceException("Transaction failed", e);
		}
	}
	
	public<ResultType> ResultType doExecuteTransaction(Transaction<ResultType> txn) throws SQLException {
		Connection conn = connect();
		
		try {
			int numAttempts = 0;
			boolean success = false;
			ResultType result = null;
			
			while (!success && numAttempts < MAX_ATTEMPTS) {
				try {
					result = txn.execute(conn);
					conn.commit();
					success = true;
				} catch (SQLException e) {
					if (e.getSQLState() != null && e.getSQLState().equals("41000")) {
						// Deadlock: retry (unless max retry count has been reached)
						numAttempts++;
					} else {
						// Some other kind of SQLException
						throw e;
					}
				}
			}
			
			if (!success) {
				throw new SQLException("Transaction failed (too many retries)");
			}
			
			// Success!
			return result;
		} finally {
			DBUtil.closeQuietly(conn);
		}
	}

	public Connection connect() throws SQLException {
		Connection conn = DriverManager.getConnection("jdbc:derby:test.db;create=true");
		
		// Set autocommit to false to allow execution of
		// multiple queries/statements as part of the same transaction.
		conn.setAutoCommit(false);
		
		return conn;
	}
	
	private void loadAccount(Account account, ResultSet resultSet, int index) throws SQLException {
		account.setUserId(resultSet.getInt(index++));
		account.setUsername(resultSet.getString(index++));
		account.setPassword(resultSet.getString(index++));
		account.setLoginId(resultSet.getInt(index++));
		account.setName(resultSet.getString(index++));
		account.setEmail(resultSet.getString(index++));
		account.setPhoneNumber(resultSet.getString(index++));
	}
	
	private void loadGroup(Group group, ResultSet resultSet, int index) throws SQLException {
		group.setGroupId(resultSet.getInt(index++));
		group.setName(resultSet.getString(index++));
		group.setDescription(resultSet.getString(index++));
		group.setRating(resultSet.getInt(index++));
				
	}
	
	private void loadGroupMember(GroupMember groupMember, ResultSet resultSet, int index) throws SQLException {
		groupMember.setMemberId(resultSet.getInt(index++));
		groupMember.setGroupId(resultSet.getInt(index++));
		groupMember.setAccountId(resultSet.getInt(index++));		
	}
	
	public boolean createTables() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
				
				try {
					stmt1 = conn.prepareStatement(
						"create table accounts (" +
						"	account_id integer primary key " +
						"		generated always as identity (start with 1, increment by 1), " +									
						"	username varchar(40)," +
						"	password varchar(40)," +
						"   login_id integer," +
						"	name varchar(20)," +
						"	email varchar(50)," +
						"	phonenumber varchar(25)" +
						")"
					);	
					
					stmt1.executeUpdate();
					
					stmt2 = conn.prepareStatement(
							"create table groups (" +
							"	group_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	name varchar(70)," +
							"	description varchar(150)," +
							"   rating integer " +
							")"
					);
					stmt2.executeUpdate();
					
					stmt3 = conn.prepareStatement(
							"create table groupMembers (" +
							"	member_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	group_id integer constraint group_id references groups, " +
							"	account_id integer constraint account_id references accounts " +
							")"
					);
					stmt3.executeUpdate();
					
					stmt4 = conn.prepareStatement(
							"create table posts (" +
							"	post_id integer primary key " +
							"		generated always as identity (start with 1, increment by 1), " +
							"	account_id integer constraint post_account_id references accounts, " +
							"   text varchar(500) " +
							")"
					);
					stmt4.executeUpdate();
					
					return true;
				} finally {
					DBUtil.closeQuietly(stmt1);
					DBUtil.closeQuietly(stmt2);
					DBUtil.closeQuietly(stmt3);
					DBUtil.closeQuietly(stmt4);
				}
			}
		});
		return true;
	}
	
	public boolean dropTables(){
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				PreparedStatement stmt1 = null;
				PreparedStatement stmt2 = null;
				PreparedStatement stmt3 = null;
				PreparedStatement stmt4 = null;
		

				try{
					stmt1 = conn.prepareStatement("DROP TABLE groupMembers");
					stmt2 = conn.prepareStatement("DROP TABLE posts");
					stmt3 = conn.prepareStatement("DROP TABLE accounts");
					stmt4 = conn.prepareStatement("DROP TABLE groups");
					
					
			
					stmt1.executeUpdate();
					stmt2.executeUpdate();
					stmt3.executeUpdate();
					stmt4.executeUpdate();
						
					conn.commit();
				}catch(SQLException e){
					System.out.println(e.getMessage());
					return false;
				}finally{
					DBUtil.closeQuietly(stmt1);
				}
				return true;
			}
		});
		return true;
	}

	
	public void loadInitialData() {
		executeTransaction(new Transaction<Boolean>() {
			@Override
			public Boolean execute(Connection conn) throws SQLException {
				List<Account> accountList;
				List<Group> groupList;
				List<GroupMember> groupMemberList;
				List<Post> postList;
				
				try {
					accountList = InitialData.getAccounts();
					groupList = InitialData.getGroups();
					groupMemberList = InitialData.getGroupMembers();
					postList = InitialData.getPosts();
				} catch (IOException e) {
					throw new SQLException("Couldn't read initial data", e);
				}

				PreparedStatement insertAccount = null;
				PreparedStatement insertGroup   = null;
				PreparedStatement insertGroupMember   = null;
				PreparedStatement insertPost   = null;

				try {
					// populate accounts table (accounts first, since account_id is foreign key in groupMembers table)
					insertAccount = conn.prepareStatement("insert into accounts (username, password, login_id, name, email, phonenumber) values (?, ?, ?, ?, ?, ?)");
					for (Account account : accountList) {
//						insertAccount.setInt(1, account.getUserId());	// auto-generated primary key, don't insert this
						insertAccount.setString(1, account.getUsername());
						insertAccount.setString(2, account.getPassword());
						insertAccount.setInt(3, account.getLoginId());
						insertAccount.setString(4, account.getName());
						insertAccount.setString(5, account.getEmail());
						insertAccount.setString(6, account.getPhoneNumber());
						insertAccount.addBatch();
					}
					insertAccount.executeBatch();
					
					insertGroup = conn.prepareStatement("insert into groups (name, description, rating) values (?, ?, ?)");
					for (Group group : groupList) {
//						insertGroup.setInt(1, group.getGroupId());		// auto-generated primary key, don't insert this
						insertGroup.setString(1, group.getName());
						insertGroup.setString(2, group.getDescription());
						insertGroup.setInt(3, group.getRating());
						insertGroup.addBatch();
					}
					insertGroup.executeBatch();
					
					insertGroupMember = conn.prepareStatement("insert into groupMembers (group_id, account_id) values (?, ?)");
					for (GroupMember groupMember : groupMemberList) {
//						insertGroupMember.setInt(1, groupMember.getMemberId());		// auto-generated primary key, don't insert this
						insertGroupMember.setInt(1, groupMember.getGroupId());
						insertGroupMember.setInt(2, groupMember.getAccountId());
						insertGroupMember.addBatch();
					}
					insertGroupMember.executeBatch();
					
					insertPost = conn.prepareStatement("insert into posts (account_id, text) values (?, ?)");
					for (Post post : postList) {
//						insertPost.setInt(1, post.getPostId());		// auto-generated primary key, don't insert this
						insertPost.setInt(1, post.getAccountId());
						insertPost.setString(2, post.getText());
						insertPost.addBatch();
					}
					insertPost.executeBatch();
					
					return true;
				} finally {
					DBUtil.closeQuietly(insertAccount);
					DBUtil.closeQuietly(insertGroup);
					DBUtil.closeQuietly(insertGroupMember);
					DBUtil.closeQuietly(insertPost);
				}
			}
		});
	}
	
	/*
	// The main method creates the database tables and loads the initial data.
		public static void main(String[] args) throws IOException {
			System.out.println("Creating tables...");
			DerbyDatabase db = new DerbyDatabase();
			db.createTables();
			
			System.out.println("Loading initial data...");
			db.loadInitialData();
			
			System.out.println("Success!");
		}
	*/
		
	
	public static void main(String[] args) throws SQLException {
		System.out.println("----Loading Database Driver---- ");
		DatabaseProvider.setInstance(new DerbyDatabase());
		IDatabase db = DatabaseProvider.getInstance();

		System.out.println("----Connecting to Database---- ");
		Connection conn = db.connect();

		System.out.println("(C)reate table or (D)rop tables: ");
		Scanner in = new Scanner(System.in);
		
		if(in.nextLine().toUpperCase().equals("C")){
			System.out.println("----Creating Tables---- ");
			if(db.createTables()){
				System.out.println("Loading initial data...");
				db.loadInitialData();
				System.out.println("----Successfully Created Tables---- ");
			}

			else{
				System.out.println("----Failed to Create Tables---- ");
			}
		}
		else{
			System.out.println("----Preparing to Drop Tables---- ");
			if(db.dropTables()){
				System.out.println("----Successfully Dropped Tables---- ");
			}
			else{
				System.out.println("----Failed To Drop Table---- ");
			}
		}
		in.close();
		DBUtil.closeQuietly(conn);
	}
	
}
