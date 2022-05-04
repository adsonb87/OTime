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

import br.com.OTime.dto.RequisicaoNovaHoraExtra;
import br.com.OTime.model.HoraExtra;
import br.com.OTime.model.Usuario;
import br.com.OTime.repository.HoraExtraRepository;
import br.com.OTime.repository.UsuarioRepository;

@Controller
@RequestMapping("horaextra")
public class HoraExtraController {
	
	static List<String> listaTipoHoras = null;
	
	
	static {
		listaTipoHoras = new ArrayList<>();
		listaTipoHoras.add("COMPENSACAO");
		listaTipoHoras.add("HORAEXTRA");
	}
	
	
	
	@Autowired
	private HoraExtraRepository horaExtraRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	
	@GetMapping("formulario")
	public String novo(RequisicaoNovaHoraExtra requisicaoNovaHoraExtra, Model model) {
		
		model.addAttribute("listaTipoHoras", listaTipoHoras);
		
		return null;
		
	}
	
	
	@PostMapping("novo")	
	public String salvar(RequisicaoNovaHoraExtra requisicao, Model model) {
		
		Optional<Usuario> usuarioBuscao = usuarioRepository.findById("0100086");
		
		if(!usuarioBuscao.isPresent()) {
			return null;
		}
		
		Usuario usuario = usuarioBuscao.get();
		
		HoraExtra horaExtra = requisicao.toHoraExtra();	
		horaExtra.setUsuario(usuario);
		
		horaExtraRepository.save(horaExtra);
		
		List<HoraExtra> horasExtras = horaExtraRepository.findAll();
		
		model.addAttribute("horasExtras", horasExtras);
		
		return null;
		
	}
	
	@GetMapping("listar")
	public String listar(Model model) {
		
		List<HoraExtra> horasExtras = horaExtraRepository.findAll();
		
		model.addAttribute("horasExtras", horasExtras);
		
		return "hora_extra/homeHoraExtra";
		
	}
	
	@PutMapping("editar")
	public String editar (@RequestParam("id") String id, Model model) {
		
		Optional<HoraExtra> horaExtraBuscada = horaExtraRepository.findById(Long.parseLong(id));
		
		if(!horaExtraBuscada.isPresent()) {
			return null;
		}
		
		HoraExtra horaExtra = horaExtraBuscada.get();
		horaExtraRepository.save(horaExtra);
		
		model.addAttribute("horaExtra", horaExtra);
		
		
		return null;
	}
	
	@GetMapping("apagar")
	public String apagar (@RequestParam("id") String id) {
		
		Optional<HoraExtra> horaExtraBuscada = horaExtraRepository.findById(Long.parseLong(id));
		
		if(!horaExtraBuscada.isPresent()) {
			return null;
		}
				
		HoraExtra horaExtra = horaExtraBuscada.get();
		
		
		
		horaExtraRepository.delete(horaExtra);
		
		return "hora_extra/homeHoraExtra";
	}
	
	
	@GetMapping("buscar")
	public String buscar (@RequestParam("id") String id, Model model) {
		
		Optional<HoraExtra> horaExtraBuscada = horaExtraRepository.findById(Long.parseLong(id));
		
		if(!horaExtraBuscada.isPresent()) {
			return null;
		}
		
		HoraExtra horaExtra = horaExtraBuscada.get();
		
		model.addAttribute("horaExtra", horaExtra);
		
		
		return null;
	}
	
	@PutMapping("autorizar")
	public String buscar2 (@RequestParam("id") String id, Model model) {
		
		Optional<HoraExtra> horaExtraBuscada = horaExtraRepository.findById(Long.parseLong(id));
		
		if(!horaExtraBuscada.isPresent()) {
			return null;
		}
		
		HoraExtra horaExtra = horaExtraBuscada.get();
		
		model.addAttribute("horaExtra", horaExtra);
		
		
		return null;
	}
	
}
