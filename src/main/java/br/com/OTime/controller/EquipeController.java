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

import br.com.OTime.dto.RequisicaoNovaEquipe;
import br.com.OTime.dto.RequisicaoNovaEquipe;
import br.com.OTime.model.Equipe;
import br.com.OTime.model.Usuario;
import br.com.OTime.model.Equipe;
import br.com.OTime.repository.EquipeRepository;
import br.com.OTime.repository.UsuarioRepository;
import net.bytebuddy.agent.builder.AgentBuilder.InjectionStrategy.UsingInstrumentation;
import br.com.OTime.repository.EquipeRepository;

@Controller
@RequestMapping("equipe")
public class EquipeController {
		
	@Autowired
	private EquipeRepository equipeRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@GetMapping("formulario")
	public String formulario(RequisicaoNovaEquipe requisicao, Model model) {
				
		return null;
	}
	
	@PostMapping("novo")	
	public String salvar(RequisicaoNovaEquipe requisicao, Model model) {
		
		Equipe equipe = requisicao.toEquipe();
		equipeRepository.save(equipe);
		
		model.addAttribute("equipe", equipe);
				
		return null;
	}
	
	
	@PutMapping("buscar")
	public String buscar (@RequestParam("id") String id, Model model) {
		
		Optional<Equipe> equipeBuscado = equipeRepository.findById(Long.parseLong(id));
		
		if(!equipeBuscado.isPresent()) {
			return null;
		}
		
		Equipe equipe = equipeBuscado.get();
		
		model.addAttribute("equipe", equipe);
		
		
		return null;
	}
	
	@GetMapping("listar")
	public String listar(Model model) {
		
		List<Equipe> equipes = equipeRepository.findAll();
		
		model.addAttribute("equipes", equipes);
		
		return null;
		
	}
	
	@PutMapping("editar")
	public String editar (@RequestParam("id") String id, Model model) {
		
		Optional<Equipe> equipeBuscado = equipeRepository.findById(Long.parseLong(id));
		
		if(!equipeBuscado.isPresent()) {
			return null;
		}
		
		Equipe equipe = equipeBuscado.get();
		equipeRepository.save(equipe);
		
		model.addAttribute("equipe", equipe);
		
		
		return null;
	}
	
	@DeleteMapping("apagar")
	public String apagar (@RequestParam("id") String id) {
		
		Optional<Equipe> equipeBuscado = equipeRepository.findById(Long.parseLong(id));
		
		if(!equipeBuscado.isPresent()) {
			return null;
		}
		
		Equipe equipe = equipeBuscado.get();
		equipeRepository.delete(equipe);
		
		return null;
	}
	
	@PutMapping("cadastrarUsuario")
	public String cadastrarUsuario(@RequestParam("idUsuario") String idUsuario, @RequestParam("id") String idEquipe, Model model) {
		
		Optional<Usuario> usuarioBuscado = usuarioRepository.findById(idUsuario);
		Optional<Equipe> equipeBuscada = equipeRepository.findById(Long.parseLong(idEquipe));
		
		Usuario usuario = usuarioBuscado.get();
		Equipe equipe = equipeBuscada.get();
		
		List<Usuario> usuariosEquipe = equipe.getUsuarios();
		usuariosEquipe.add(usuario);
		
		equipe.setUsuarios(usuariosEquipe);
		
		equipeRepository.save(equipe);
		
		model.addAttribute("usuarios", usuariosEquipe);
		model.addAttribute("equipe", equipe);
		
		
		return null;
	}
	
	@DeleteMapping("removeUsuario")
	public String removerUsuario(@RequestParam("idUsuario") String idUsuario, @RequestParam("id") String idEquipe, Model model) {
		
		Optional<Usuario> usuarioBuscado = usuarioRepository.findById(idUsuario);
		Optional<Equipe> equipeBuscada = equipeRepository.findById(Long.parseLong(idEquipe));
		
		Usuario usuario = usuarioBuscado.get();
		Equipe equipe = equipeBuscada.get();
		
		List<Usuario> usuariosEquipe = equipe.getUsuarios();
		usuariosEquipe.remove(usuario);
		
		equipe.setUsuarios(usuariosEquipe);
		
		equipeRepository.save(equipe);
		
		model.addAttribute("usuarios", usuariosEquipe);
		model.addAttribute("equipe", equipe);
		
		
		return null;
	}
}
