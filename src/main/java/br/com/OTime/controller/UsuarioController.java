package br.com.OTime.controller;

import java.util.ArrayList;
import java.util.List;

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
	
	static List<String> listaPerfis = null;
	
	static {
		listaPerfis = new ArrayList<>();
		listaPerfis.add("ADMIN");
		listaPerfis.add("USER");
	}
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("formulario")
	public String formulario(RequisicaoNovoUsuario requisicao, Model model) {
		
		model.addAttribute("listaPerfis", listaPerfis);
		
		return "usuario/FormNovoUsuario";
	}
	
	@PostMapping("novo")	
	public String novo(RequisicaoNovoUsuario requisicao, Model model) {
		
		Usuario usuario = requisicao.toUsuario();
		usuarioRepository.save(usuario);
		
		model.addAttribute("usuario", usuario);
		
		System.out.println(usuario.toString());
		
		return null;
	}
}
