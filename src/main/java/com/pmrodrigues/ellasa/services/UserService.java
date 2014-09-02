package com.pmrodrigues.ellasa.services;

import static java.lang.String.format;

import java.util.Collections;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.repositories.UsuarioRepository;

@Service("userService")
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;
	
	private static final Logger logging = Logger.getLogger(UserService.class);
	
	@Override
	public UserDetails loadUserByUsername(final String username)
			throws UsernameNotFoundException {
		
		logging.debug(format("Pesquisando usuario com email %s",username));
		
		final Usuario usuario = repository.findByEmail(username);
		if( usuario != null ) {
			return new User(username,usuario.getPassword(),usuario.isBloqueado(),true,true,true,Collections.EMPTY_LIST);
		}
		
		throw new UsernameNotFoundException(format("Usuario %s não encontrado ou senha invalida",username));
	}

}
