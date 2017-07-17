package de.liebig.lighthouse.api;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.myjeeva.digitalocean.DigitalOcean;
import com.myjeeva.digitalocean.exception.DigitalOceanException;
import com.myjeeva.digitalocean.exception.RequestUnsuccessfulException;
import com.myjeeva.digitalocean.impl.DigitalOceanClient;

import de.liebig.lighthouse.exceptions.ApiException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ApiManagerImpl implements ApiManager {

	private static DigitalOcean digitalOcean;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	public ApiManagerImpl(String apiKey) {
		this.setDigitalOceanApiKey(apiKey);
	}
	
	@Override
	public DigitalOcean getDigitalOcean() throws ApiException {
		if (ApiManagerImpl.digitalOcean != null) {
				return ApiManagerImpl.digitalOcean;
		} else {
			throw new ApiException("DigitalOcean API is not set");
		}
	}

	@Override
	public synchronized Optional<DigitalOcean> setDigitalOceanApiKey(String apiKey) {

		if (this.isDigitalOceanApiKeyValid(apiKey)) {
			DigitalOcean newDigitalOcean = new DigitalOceanClient(apiKey);
			ApiManagerImpl.digitalOcean = newDigitalOcean;
			return Optional.of(newDigitalOcean);
		} else {
			return Optional.empty();
		}
	}

	@Override
	public boolean isDigitalOceanApiKeyValid(String apiKey) {
		DigitalOcean digitalOcean = new DigitalOceanClient(apiKey);
		try {
			return digitalOcean.getAccountInfo() != null;
		} catch (DigitalOceanException | RequestUnsuccessfulException e) {
			log.debug("API Key not valid", e);
		}
		return false;
	}

}
