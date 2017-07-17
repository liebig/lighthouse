package de.liebig.lighthouse.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.myjeeva.digitalocean.pojo.Droplet;
import com.myjeeva.digitalocean.pojo.Droplets;

import de.liebig.lighthouse.exceptions.ApiException;

@RestController
@RequestMapping(value = "/server", name = "ServerController")
public class ServerController {

	@Autowired
	private ServerService serverService;
	
	@RequestMapping(name = "get",  method = RequestMethod.GET)
	public Droplets getServers() throws ApiException {
		return serverService.getAll();
	}
	
	@RequestMapping(name = "create",  method = RequestMethod.POST)
	public Droplet createServer(@RequestBody ServerCreateDto server) throws ApiException {
		return serverService.createDroplet(server);
	}
	
	@RequestMapping(name = "delete",  method = RequestMethod.DELETE)
	public void deleteServer(@RequestBody ServerDeleteDto serverDeleteDto) throws ApiException {
		serverService.deleteDroplet(Integer.valueOf(serverDeleteDto.getId()));
	}
}
