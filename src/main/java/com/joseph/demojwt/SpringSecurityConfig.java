package com.joseph.demojwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.joseph.demojwt.auth.filter.JWTAuthenticationFilter;
import com.joseph.demojwt.auth.filter.JWTAuthorizationFilter;
import com.joseph.demojwt.auth.handler.LoginSuccessHandler;
import com.joseph.demojwt.auth.service.JWTService;
import com.joseph.demojwt.models.service.JpaUserDetailsService;


@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private LoginSuccessHandler successHandler;

	@Autowired
	private JpaUserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private JWTService jwtService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar**", "/locale").permitAll()
		.anyRequest().authenticated()


		 .and()
		    .formLogin()
		        .successHandler(successHandler)
		        .loginPage("/login")
		    .permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
		/*

		.and()
		.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService))
		.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService))


		.csrf().disable()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 */
	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception
	{
		build.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
	}
}
