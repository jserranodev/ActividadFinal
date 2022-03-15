package com.nttdata.activididadfinal.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService usuarioService;
	private String[] resources = new String[] { "/include/**", "/js/**", "/css/**", "/api/**" };

	public BCryptPasswordEncoder myPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService).passwordEncoder(myPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers(resources).permitAll() // Permite acceso rutas en "resources"
			.and().authorizeRequests().antMatchers("/console/**").permitAll() // Hace funcionar /h2-console
			.and().authorizeRequests().anyRequest().authenticated() // resto peticiones tiene estar autenticadas
			.and().formLogin() // página login web app
				.failureUrl("/login?error=true") // ruta redirección en caso login erroneo
				.defaultSuccessUrl("/") // ruta redirección en caso login corrector
			.and().logout().logoutSuccessUrl("/login?logout?true").permitAll().and().rememberMe()
			.key("uniqueAndSecret"); // Recordar autenticación (!)
		
		http.csrf().disable();
		http.headers().frameOptions().disable();
	}
	
}
