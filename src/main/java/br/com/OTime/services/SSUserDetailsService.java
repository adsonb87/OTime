package br.com.OTime.services;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.OTime.model.Usuario;
import br.com.OTime.repository.UsuarioRepository;

public class SSUserDetailsService implements UserDetailsService {
	
	
	private UsuarioRepository usuarioRepository;
	
	public SSUserDetailsService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String chapa) throws UsernameNotFoundException {
		
		try {
			Optional<Usuario> usuarioBuscado = usuarioRepository.findById(chapa);
			
			
			if(!usuarioBuscado.isPresent()) {
				return null;
			}
			
			Usuario usuario = usuarioBuscado.get();
			
			return org.springframework.security.core.userdetails.User(usuario.getChapa(), usuario.getSenha(), usuario.getPerfil().toString());
		
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
		
		
		
		return null;
	}

}
