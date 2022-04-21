package br.com.OTime.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	public String formulario(RequisicaoNovaHoraExtra requisicaoNovaHoraExtra, Model model) {
		
		model.addAttribute("listaTipoHoras", listaTipoHoras);
		
		return null;
		
	}
	
	
	@PostMapping("novo")	
	public String novaHoraExtra(RequisicaoNovaHoraExtra requisicao, Model model) {
		
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
}
