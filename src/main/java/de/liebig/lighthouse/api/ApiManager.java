package de.liebig.lighthouse.api;

import java.util.Optional;

import com.myjeeva.digitalocean.DigitalOcean;

public interface ApiManager {
	
	public Optional<DigitalOcean> getDigitalOcean();
	
	public Optional<DigitalOcean> setDigitalOceanApiKey(String apiKey);
	
	public boolean isDigitalOceanApiKeyValid(String apiKey);

}
