package de.liebig.lighthouse.accounts;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myjeeva.digitalocean.DigitalOcean;
import com.myjeeva.digitalocean.impl.DigitalOceanClient;

@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
	
	public boolean exists() {
		return accountRepository.count() > 0;
	}
	
	public Optional<Account> getAccount() {
		List<Account> accounts = accountRepository.findAll();
		if (accounts.size() > 1) {
			throw new IllegalStateException("There must be only one account in the database");
		}
		
		if (accounts.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(accounts.get(0));
		}
	}
	
	
	public Optional<Account> createAccount(String apiKey) {
		
		if(this.exists()) {
			return Optional.empty();
		} else {
			Account newAccount = new Account();
			newAccount.setApiKey(apiKey);
			return Optional.ofNullable(accountRepository.save(newAccount));
		}
	}
	
}
