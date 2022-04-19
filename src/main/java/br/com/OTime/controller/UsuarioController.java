package br.com.OTime.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		return "usuario/FormNovoUsuario";
	}
	
	@PostMapping("novo")	
	public String novo(RequisicaoNovoUsuario requisicao, Model model) {
		
		Usuario usuario = requisicao.toUsuario();
		usuarioRepository.save(usuario);
		
		model.addAttribute("usuario", usuario);
		
		System.out.println(usuario.toString());
		
		return "redirect:/home";
	}
}
