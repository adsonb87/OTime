package br.com.OTime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.OTime.dto.RequisicaoNovoUsuario;
import br.com.OTime.model.Usuario;
import br.com.OTime.repository.UsuarioRepository;

@Controller
@RequestMapping("usuario")
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("formulario")
	public String formulario(RequisicaoNovoUsuario requisicao) {
		return "usuario/formulario";
	}
	
	@PostMapping("novo")	
	public String novo(RequisicaoNovoUsuario requisicao) {
		
		Usuario usuario = requisicao.toUsuario();
		usuarioRepository.save(usuario);
		
		return "console.log('teste')";
	}
}
