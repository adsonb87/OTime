package br.com.OTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.OTime.model.Perfil;
import br.com.OTime.repository.UsuarioRepository;
import br.com.OTime.services.SSUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private SSUserDetailsService userDetailsSevice;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetailsService userDetailsService() throws Exception{
		return new SSUserDetailsService(usuarioRepository);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http
		.authorizeRequests()
			.antMatchers("/**")
			.hasAnyAuthority(Perfil.ADMIN.toString(), Perfil.USER.toString())
			.anyRequest().authenticated()
		.and()
			.formLogin(form -> form
				.loginPage("/login")
				.defaultSuccessUrl("/home", true)
				.permitAll()
			)
			.logout(logout -> logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/login")
					.permitAll()
			)
			.csrf().disable();	
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.userDetailsService(userDetailsService())
			.passwordEncoder(passwordEncoder());
		
		
		/*
		auth.inMemoryAuthentication()
			.withUser("user")
			.password("12345")
			.authorities("USER");
		*/
	}
}
