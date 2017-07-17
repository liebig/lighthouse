package de.liebig.lighthouse.api;

import java.util.Optional;

import com.myjeeva.digitalocean.DigitalOcean;

import de.liebig.lighthouse.exceptions.ApiException;

public interface ApiManager {
	
	public DigitalOcean getDigitalOcean() throws ApiException;
	
	public Optional<DigitalOcean> setDigitalOceanApiKey(String apiKey);
	
	public boolean isDigitalOceanApiKeyValid(String apiKey);

}
