package br.com.OTime.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		
		return "usuario/formNovoUsuario";
	}
	
	@PostMapping("novo")	
	public String salvar(RequisicaoNovoUsuario requisicao, Model model) {
		
		Usuario usuario = requisicao.toUsuario();
		usuarioRepository.save(usuario);
		
		model.addAttribute("usuario", usuario);
		
		return "redirect:/usuario/listar";
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
		
		return "usuario/listarUsuarios";
		
	}
	
	@GetMapping("editar")
	public String editar (@RequestParam("chapa") String chapa, Model model, RequisicaoNovoUsuario requisicao) {
		
		Optional<Usuario> UsuarioBuscado = usuarioRepository.findById(chapa);
		
		if(!UsuarioBuscado.isPresent()) {
			return null;
		}
		
		Usuario usuario = UsuarioBuscado.get();
				
		model.addAttribute("usuario", usuario);
		model.addAttribute("listaPerfis", listaPerfis);
		
		return "usuario/formNovoUsuario";
	}
	
	@GetMapping("apagar")
	public String apagar (@RequestParam("chapa") String chapa) {
		
		Optional<Usuario> UsuarioBuscado = usuarioRepository.findById(chapa);
		
		if(!UsuarioBuscado.isPresent()) {
			return null;
		}
		
		Usuario Usuario = UsuarioBuscado.get();
		usuarioRepository.delete(Usuario);
		
		return "redirect:/usuario/listar";
	}
}
