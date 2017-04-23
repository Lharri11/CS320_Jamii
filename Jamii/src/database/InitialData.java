package database;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import model.Account;

public class InitialData {
	public static List<Account> getAccounts() throws IOException {
		List<Account> authorList = new ArrayList<Account>();
		ReadCSV readAccounts = new ReadCSV("accounts.csv");
		try {
			// auto-generated primary key for authors table
			Integer authorId = 1;
			while (true) {
				List<String> tuple = readAccounts.next();
				if (tuple == null) {
					break;
				}
				Iterator<String> i = tuple.iterator();
				Account account = new Account(null, null, authorId, null, null, null, false);
				account.setUsername(i.next());
				account.setPassword(i.next());
				account.setLoginId(Integer.parseInt(i.next()));
				account.setName(i.next());
				account.setEmail(i.next());			
				
				authorList.add(account);
			}
			return authorList;
		} finally {
			readAccounts.close();
		}
	}
	
}
