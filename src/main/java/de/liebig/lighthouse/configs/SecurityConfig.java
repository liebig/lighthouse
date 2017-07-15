package de.liebig.lighthouse.configs;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import de.liebig.lighthouse.users.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private Environment environment;

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity //
			.authorizeRequests() //
				.antMatchers("/register/**").permitAll() //
				.antMatchers("/img/**", "/js/**", "/css/**", "/bootstrap/**").permitAll() //
				.antMatchers("/admin/**").hasAuthority("ROLE_ADMIN") //
				.antMatchers("/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN") //
				.and() //
			.formLogin() //
				.loginPage("/login").permitAll() //
				.and() //
			.logout().permitAll().and() //
			.csrf() //
				.ignoringAntMatchers("/admin/console/**") //
				.and() //
			.headers() //
				.frameOptions().disable();

		// If current profile is dev, disable the header frameOptions and the CSRF
		// protection to enable the h2-console
		if (Arrays.stream(environment.getActiveProfiles())
				.anyMatch(env -> (StringUtils.equalsIgnoreCase(env, "dev")))) {

			httpSecurity //
				.headers() //
					.frameOptions().disable() //
					.and() //
				.csrf() //
					.ignoringAntMatchers("/admin/console/**");
		}

	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
	    DaoAuthenticationProvider authProvider
	      = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(userDetailsService);
	    authProvider.setPasswordEncoder(passwordEncoder());
	    return authProvider;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(8);
	}
}
