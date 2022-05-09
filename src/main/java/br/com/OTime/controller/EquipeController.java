package br.com.OTime.controller;

import java.util.ArrayList;
import java.util.Iterator;
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
		
		return "equipe/formNovaEquipe";
	}
	
	@PostMapping("novo")	
	public String salvar(RequisicaoNovaEquipe requisicao, Model model) {
		
		Equipe equipe = requisicao.toEquipe();
		equipeRepository.save(equipe);
		
		model.addAttribute("equipe", equipe);
				
		return "redirect:/equipe/listar";
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
		
		return "equipe/ListarEquipe";
		
	}
	
	@GetMapping("listarUsuarios")
	public String listarUsuarios(@RequestParam("id") String id, Model model, RequisicaoNovaEquipe requisicao) {
		
		List<Equipe> equipes = equipeRepository.findAllByEquipe(Long.parseLong(id));
		List<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario usuario = new Usuario();
		
		for (int i = 0; i < equipes.size(); i++) {
			usuario = equipes.get(i).getUsuarios().get(i);
			usuarios.add(usuario);
		}
		
		
		
		model.addAttribute("descricaoEquipe", equipes.get(0).getDescricao());
		model.addAttribute("idEquipe", equipes.get(0).getId());
		model.addAttribute("usuarios", usuarios);
		
		return "equipe/listarUsuarioEquipe";
		
	}
	
	@GetMapping("apagarUsuarios")
	public String apagarUsuarios (@RequestParam("id") String id, @RequestParam("chapa") String chapa) {
		
		List<Equipe> equipes = equipeRepository.findAllByEquipe(Long.parseLong(id));
		List<Usuario> usuarios = new ArrayList<Usuario>();
		Usuario usuario = new Usuario();
		
		for (int i = 0; i < equipes.size(); i++) {
			if(equipes.get(i).getUsuarios().get(i).getChapa() == chapa) {
				usuario = equipes.get(i).getUsuarios().get(i);
				equipes.remove(usuario);
			}
		}
		
		equipeRepository.save(equipes.get(0));
		
		return "redirect:/equipe/listarUsuarios";
	}
	
	
	@GetMapping("editar")
	public String editar (@RequestParam("id") String id, Model model, RequisicaoNovaEquipe requisicao) {
		
		Optional<Equipe> equipeBuscado = equipeRepository.findById(Long.parseLong(id));
		
		if(!equipeBuscado.isPresent()) {
			return null;
		}
		
		Equipe equipe = equipeBuscado.get();
		equipeRepository.save(equipe);
		
		model.addAttribute("equipe", equipe);
		
		
		return "equipe/formNovaEquipe";
	}
	
	@GetMapping("apagar")
	public String apagar (@RequestParam("id") String id) {
		
		Optional<Equipe> equipeBuscado = equipeRepository.findById(Long.parseLong(id));
		
		if(!equipeBuscado.isPresent()) {
			return null;
		}
		
		Equipe equipe = equipeBuscado.get();
		equipeRepository.delete(equipe);
		
		return "redirect:/equipe/listar";
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
