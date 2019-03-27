package com.ates.moneytransfer.service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import com.ates.moneytransfer.model.Account;

public class AccountService {

	
	public final static Map<Integer, Account> accounts = new LinkedHashMap();
	static
	{
		 Account account = new Account("ibrahim", BigDecimal.valueOf(1460));
	     accounts.put(0, account);
	     Account account2 = new Account("halil", BigDecimal.valueOf(2560));
	     accounts.put(1, account2);
	     Account account3 = new Account("ates", BigDecimal.valueOf(6964));
	     accounts.put(2, account3);
	}

 
    public Account findById(int id) {
    	
        return accounts.get(id);
    }
 
    public Account addAccount(Account account) {
        accounts.put(account.getAccountId(), account);
        return account;
    }
 
    public Account updateAccount(int id, Account account) {

        accounts.put(id, account);
 
        return account;
 
    }

    public void delete(int id) {
        accounts.remove(id);
    }
 
    public List findAll() {
        return new ArrayList<>(accounts.values());
    }



}
