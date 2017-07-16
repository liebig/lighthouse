package de.liebig.lighthouse.configs;

import java.util.Optional;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.myjeeva.digitalocean.DigitalOcean;
import com.myjeeva.digitalocean.impl.DigitalOceanClient;

import de.liebig.lighthouse.accounts.Account;
import de.liebig.lighthouse.accounts.AccountService;
import de.liebig.lighthouse.api.ApiManager;
import de.liebig.lighthouse.api.ApiManagerImpl;

@Configuration
public class MvcConfig {

	@Autowired
	private AccountService accountService;

	@Autowired
	BeanFactory beanFactory;

	@Bean
	public ApiManager apiManager() {
		
		Optional<Account> account = accountService.getAccount();
		
		if (account.isPresent()) {
			return new ApiManagerImpl(account.get().getApiKey());
		} else {
			return new ApiManagerImpl();
		}
	}

}
