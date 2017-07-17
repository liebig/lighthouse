package de.liebig.lighthouse.server;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.myjeeva.digitalocean.DigitalOcean;
import com.myjeeva.digitalocean.exception.DigitalOceanException;
import com.myjeeva.digitalocean.exception.RequestUnsuccessfulException;
import com.myjeeva.digitalocean.pojo.Droplet;
import com.myjeeva.digitalocean.pojo.Droplets;
import com.myjeeva.digitalocean.pojo.Image;
import com.myjeeva.digitalocean.pojo.Region;

import de.liebig.lighthouse.api.ApiManager;
import de.liebig.lighthouse.exceptions.ApiException;
import de.liebig.lighthouse.software.SoftwareService;

@Service
public class ServerService {

	@Autowired
	private ApiManager apiManager;
	
	@Autowired
	private SoftwareService softwareService;
	
	@Value("${lighthouse.image}")
	private String defaultImage;

	public Droplets getAll() throws ApiException {
		DigitalOcean digitalOcean = apiManager.getDigitalOcean();

		try {
			return digitalOcean.getAvailableDroplets(0, 100);
		} catch (DigitalOceanException | RequestUnsuccessfulException e) {
			throw new ApiException("Could not fetch droplets", e);
		}

	}

	public Droplet createDroplet(ServerCreateDto server) throws ApiException {
		DigitalOcean digitalOcean = apiManager.getDigitalOcean();
		Droplet newDroplet = new Droplet();
		newDroplet.setName("lh." + server.getSoftwareEnum().name() + "." + Instant.now().toEpochMilli());
		newDroplet.setRegion(new Region(server.getRegion()));
		newDroplet.setSize(server.getSize());
		newDroplet.setImage(new Image(defaultImage));
		newDroplet.setUserData(softwareService.getUserData(server.getSoftwareEnum()));
		
		try {
			return digitalOcean.createDroplet(newDroplet);
		} catch (DigitalOceanException | RequestUnsuccessfulException e) {
			throw new ApiException("Could not create droplet", e);
		}
	}

	public void deleteDroplet(int dropletId) throws ApiException {
		DigitalOcean digitalOcean = apiManager.getDigitalOcean();
		try {
			digitalOcean.deleteDroplet(dropletId);
		} catch (DigitalOceanException | RequestUnsuccessfulException e) {
			throw new ApiException("Could not delete droplet with id " + dropletId, e);
		}
	}
	
}
