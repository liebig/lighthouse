package de.liebig.lighthouse;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import de.liebig.lighthouse.exceptions.ResourceNotFoundException;
import de.liebig.lighthouse.roles.Role;
import de.liebig.lighthouse.roles.RoleService;
import de.liebig.lighthouse.users.User;
import de.liebig.lighthouse.users.UserRepository;
import de.liebig.lighthouse.users.UserService;

@RestController
@RequestMapping(value = "/", name = "ApplicationController")
public class ApplicationController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "/login", name = "login")
	public ModelAndView login(
		@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout,
		HttpServletResponse response) throws IOException {

		if(!userService.exists()) {
			String registerUrl = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(ApplicationController.class).register()).build().getPath();
			response.sendRedirect(registerUrl);
			return null;
		} else {
			return new ModelAndView("login");
		}
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET, name = "register")
	public ModelAndView register() throws IOException {
		
		if(userService.exists()) {
			throw new ResourceNotFoundException();
		}
		
		return new ModelAndView("register");
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, name = "doregister")
	public ModelAndView doRegister(
		@RequestParam("username") String username,
		@RequestParam("password") char[] password,
		HttpServletResponse response) throws IOException {
		
		if (StringUtils.isEmpty(username) || password.length < 8) {
			ModelAndView mav = new ModelAndView("register");
			
			if(StringUtils.isEmpty(username)) {
				mav.addObject("errorusername", true);
			} else {
				mav.addObject("errorpassword", true);
			}
			return mav;	
		} else {
			userService.createUser(username, password);
			
			String indexUrl = MvcUriComponentsBuilder.fromMethodCall(MvcUriComponentsBuilder.on(ApplicationController.class).index()).build().getPath();
			response.sendRedirect(indexUrl);
			return null;
		}
	}
	
	@RequestMapping(value = "/", name = "index")
	public ModelAndView index(){

		
		ModelAndView model = new ModelAndView();
		model.setViewName("index");

		return model;

	}
	
}
