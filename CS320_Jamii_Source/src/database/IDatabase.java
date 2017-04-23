package database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import model.Account;
import model.Group;
import model.GroupMember;

public interface IDatabase {
	public boolean createTables();
	public boolean dropTables();
	public Connection connect() throws SQLException;
	public void loadInitialData();
	public int queryForLoginIdByUsername(String username);
	public Account queryForUserAccountByUsername(String username);
	public boolean updateAccountByUsername(String username, Account account);
	public String queryForPasswordByUsername(String username);
	public List<Group> getGroupsByUser(String user);
	public boolean insertNewAccountIntoDatabase(Account newb);
}
