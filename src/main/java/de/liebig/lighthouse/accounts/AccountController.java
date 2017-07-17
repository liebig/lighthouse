package de.liebig.lighthouse.accounts;

import java.util.Optional;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myjeeva.digitalocean.DigitalOcean;
import com.myjeeva.digitalocean.exception.DigitalOceanException;
import com.myjeeva.digitalocean.exception.RequestUnsuccessfulException;


import de.liebig.lighthouse.api.ApiManager;
import de.liebig.lighthouse.exceptions.ApiException;

@RestController
@RequestMapping(value = "/account", name = "AccountController")
public class AccountController {

	@Autowired
	AccountService accountService;
	
	@Autowired
    ApiManager apiManager;
	
	@RequestMapping(name = "get",  method = RequestMethod.GET)
	public com.myjeeva.digitalocean.pojo.Account getAccount() throws ApiException {
		
		DigitalOcean digitalOcean = apiManager.getDigitalOcean();
			try {
				return digitalOcean.getAccountInfo();
			} catch (DigitalOceanException | RequestUnsuccessfulException e) {
				throw new ApiException("Could not load account", e);
			}
	}
	
	@RequestMapping(name = "create",  method = RequestMethod.POST)
	public Account createAccount(@RequestBody AccountDto accountDto) {
		
		if(apiManager.isDigitalOceanApiKeyValid(accountDto.getApiKey())) {
			Optional<Account> createdAccount = accountService.createAccount(accountDto.getApiKey());
			if(createdAccount.isPresent()) {
				apiManager.setDigitalOceanApiKey(accountDto.getApiKey());
				return createdAccount.get();
			}
		}
		return null;
	}
	
	
}
