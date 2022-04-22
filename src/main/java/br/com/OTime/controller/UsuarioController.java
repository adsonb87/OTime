package br.com.OTime.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.com.OTime.dto.RequisicaoNovoUsuario;
import br.com.OTime.model.Usuario;
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
	public String salvar(RequisicaoNovoUsuario requisicao, Model model) {
		
		Usuario usuario = requisicao.toUsuario();
		usuarioRepository.save(usuario);
		
		model.addAttribute("usuario", usuario);
		
		System.out.println(usuario.toString());
		
		return null;
	}
	
	
	@PutMapping("buscar")
	public String buscar (@RequestParam("id") String id, Model model) {
		
		Optional<Usuario> usuarioBuscado = usuarioRepository.findById(id);
		
		if(!usuarioBuscado.isPresent()) {
			return null;
		}
		
		Usuario usuario = usuarioBuscado.get();
		
		model.addAttribute("usuario", usuario);
		
		
		return null;
	}
	
	@GetMapping("listar")
	public String listar(Model model) {
		
		List<Usuario> usuarios = usuarioRepository.findAll();
		
		model.addAttribute("usuarios", usuarios);
		
		return null;
		
	}
	
	@PutMapping("editar")
	public String editar (@RequestParam("id") String id, Model model) {
		
		Optional<Usuario> UsuarioBuscado = usuarioRepository.findById(id);
		
		if(!UsuarioBuscado.isPresent()) {
			return null;
		}
		
		Usuario Usuario = UsuarioBuscado.get();
		usuarioRepository.save(Usuario);
		
		model.addAttribute("Usuario", Usuario);
		
		
		return null;
	}
	
	@DeleteMapping("apagar")
	public String apagar (@RequestParam("id") String id) {
		
		Optional<Usuario> UsuarioBuscado = usuarioRepository.findById(id);
		
		if(!UsuarioBuscado.isPresent()) {
			return null;
		}
		
		Usuario Usuario = UsuarioBuscado.get();
		usuarioRepository.delete(Usuario);
		
		return null;
	}
}
