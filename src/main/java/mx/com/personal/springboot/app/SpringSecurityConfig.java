package mx.com.personal.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import mx.com.personal.springboot.app.auth.handler.LoginSuccessHandler;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	private static final String ROLE_USER = "USER";
	private static final String ROLE_ADMIN = "ADMIN";
	
	@Autowired
	private LoginSuccessHandler successHandler;

	// Creación de usuarios
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder build) throws Exception {
		PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
		UserBuilder users = User.builder().passwordEncoder(encoder::encode);
		
		//Usuarios en memoria
		build.inMemoryAuthentication()
			.withUser(users.username("admin").password("12345").roles(ROLE_ADMIN, ROLE_USER))
			.withUser(users.username("chava").password("12345").roles(ROLE_USER));
	}
	
	//Autorizaciones
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Las rutas publicas hasta donde esta el permitAll
		http.authorizeRequests()
			.antMatchers("/", "/css/**", "/js/**", "/imgs/**", "/client/listar", "/client/list").permitAll()
			.antMatchers("/client/ver/**", "/client/uploads/**").hasAnyRole(ROLE_USER)
			.antMatchers("/client/form/**", "/client/eliminar/**", "/factura/**").hasAnyRole(ROLE_ADMIN)
			.anyRequest().authenticated()
			.and()
				.formLogin()
					.successHandler(successHandler)
					.loginPage("/login").permitAll()
			.and()
				.logout().permitAll()
			.and()
				.exceptionHandling().accessDeniedPage(Constants.ERROR_403);
	}
}
