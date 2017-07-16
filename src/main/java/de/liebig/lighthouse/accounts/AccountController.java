package de.liebig.lighthouse.accounts;

import java.util.Optional;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myjeeva.digitalocean.DigitalOcean;
import com.myjeeva.digitalocean.exception.DigitalOceanException;
import com.myjeeva.digitalocean.exception.RequestUnsuccessfulException;


import de.liebig.lighthouse.api.ApiManager;

@RestController
@RequestMapping(value = "/account", name = "AccountController")
public class AccountController {

	@Autowired
	AccountService accountService;
	
	@Autowired
    ApiManager apiManager;
	
	@RequestMapping(value = "/", name = "get",  method = RequestMethod.GET)
	public com.myjeeva.digitalocean.pojo.Account getAccount() throws DigitalOceanException, RequestUnsuccessfulException {
		
		Optional<DigitalOcean> digitalOcean = apiManager.getDigitalOcean();
		if (digitalOcean.isPresent()) {
			return digitalOcean.get().getAccountInfo();
		} else {
			return null;
		}
	}
	
	@RequestMapping(value = "/", name = "create",  method = RequestMethod.POST)
	public Account createAccount(@RequestParam("apikey") String apiKey) {
		
		if(apiManager.isDigitalOceanApiKeyValid(apiKey)) {
			Optional<Account> createdAccount = accountService.createAccount(apiKey);
			if(createdAccount.isPresent()) {
				apiManager.setDigitalOceanApiKey(apiKey);
				return createdAccount.get();
			}
		}
		return null;
	}
	
	
}
