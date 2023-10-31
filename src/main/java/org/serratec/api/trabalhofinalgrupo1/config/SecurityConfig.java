package org.serratec.api.trabalhofinalgrupo1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter { //classe do spring para gerar configurações relacionadas ao token - não é uma erro, só msg de notificação que outras versões podem não usar ele
	
	@Override //sobrescreve o método do pai (WebSecurityConfigurerAdapter)
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {	
		auth.inMemoryAuthentication() //criar um usuário em memória...
		.withUser("administrador") //... usuário...
		.password("{noop}123456") //...e senha - noop = no operation (não vai codificar nem criptografar a senha). A senha será compada com a senha que o usuário digitar no Postman.
		.roles("ADMIN"); //tipo de perfil
	}	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception { //vai personalizar o acesso aos endpoints
		http.authorizeHttpRequests()
		.anyRequest().authenticated() //para qualquer endpoint tem que estar autenticado
		.and()
		.httpBasic()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //não vai mais gravar cookie, para cada requisição ele vai fazer uma nova verificação
		.and()
		.csrf().disable();
	}
}