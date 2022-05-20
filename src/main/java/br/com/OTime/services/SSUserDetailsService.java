package br.com.OTime.services;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Transient;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.OTime.model.Usuario;
import br.com.OTime.repository.UsuarioRepository;

@Transactional
@Service
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
			
			return 	new UserDetails() {
				
				@Override
				public boolean isEnabled() {
					return true;
				}
				
				@Override
				public boolean isCredentialsNonExpired() {
					// TODO Auto-generated method stub
					return true;
				}
				
				@Override
				public boolean isAccountNonLocked() {
					// TODO Auto-generated method stub
					return true;
				}
				
				@Override
				public boolean isAccountNonExpired() {
					// TODO Auto-generated method stub
					return true;
				}
				
				@Override
				public String getUsername() {
					// TODO Auto-generated method stub
					return usuario.getChapa();
				}
				
				@Override
				public String getPassword() {
					// TODO Auto-generated method stub
					return usuario.getSenha();
				}
				
				@Override
				public Collection<? extends GrantedAuthority> getAuthorities() {
					// TODO Auto-generated method stub
					return getAuthories(usuario);
				}
			};
		
			
		} catch (Exception e) {
		 throw new UsernameNotFoundException("Usuario não encontrado");
		}
		
	}
		
	private Set<GrantedAuthority> getAuthories(Usuario usuario){
		
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		
		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(usuario.getPerfil().toString());
		
		authorities.add(grantedAuthority);
		
		return authorities;
		
	}	
		

}
