package br.com.OTime.controller;

import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
import br.com.OTime.model.Status;
import br.com.OTime.model.Tipo;
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
	public String novo(RequisicaoNovaHoraExtra requisicao, Model model) {
		
		String chapa= SecurityContextHolder.getContext().getAuthentication().getName();

		Optional<Usuario> usuarioBuscado = usuarioRepository.findById(chapa);
				
		if(!usuarioBuscado.isPresent()) {
			return null;
		}
			
		Usuario usuario = usuarioBuscado.get();

		model.addAttribute("usuario", usuario);
		
		model.addAttribute("listaTipoHoras", listaTipoHoras);
		
		return "hora_extra/formNovaHoraExtra";
		
	}
	
	
	@PostMapping("novo")	
	public String salvar(RequisicaoNovaHoraExtra requisicao, Model model) {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		String chapa = SecurityContextHolder.getContext().getAuthentication().getName();
		
		
		Optional<Usuario> usuarioBuscao = usuarioRepository.findById(chapa);
		
		if(!usuarioBuscao.isPresent()) {
			return null;
		}
		
		Usuario usuario = usuarioBuscao.get();
		
		HoraExtra horaExtra = requisicao.toHoraExtra();
		horaExtra.setUsuario(usuario);
		
		horaExtraRepository.save(horaExtra);
		
		List<HoraExtra> horasExtras = horaExtraRepository.findAll();
		
		model.addAttribute("horasExtras", horasExtras);
		
		return "redirect:/horaextra/listarUsuario";
		
	}
	
	@GetMapping("listar")
	public String listar(Model model) {
		
		String chapa= SecurityContextHolder.getContext().getAuthentication().getName();

		Optional<Usuario> usuarioBuscado = usuarioRepository.findById(chapa);
				
		if(!usuarioBuscado.isPresent()) {
			return null;
		}
			
		Usuario usuario = usuarioBuscado.get();

		model.addAttribute("usuario", usuario);
		
		List<HoraExtra> horasExtras = horaExtraRepository.findAll();
		
		model.addAttribute("horasExtras", horasExtras);
		
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		
		
		return "hora_extra/listarHoraExtra";
		
	}
	
	
	@GetMapping("listarUsuario")
	public String listarUsuario(Model model) {
		
		String chapa = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Optional<Usuario> usuarioBuscado = usuarioRepository.findById(chapa);
				
		if(!usuarioBuscado.isPresent()) {
			return null;
		}
			
		Usuario usuario = usuarioBuscado.get();

		model.addAttribute("usuario", usuario);
				
		List<HoraExtra> horasExtras = horaExtraRepository.findAllByUsuario(chapa);
		
		model.addAttribute("horasExtras", horasExtras);
				
		return "hora_extra/listarHoraExtra";
		
	}
	
	@GetMapping("editar")
	public String editar (@RequestParam("id") String id, Model model, RequisicaoNovaHoraExtra requisicao) {
		
		Optional<HoraExtra> horaExtraBuscada = horaExtraRepository.findById(Long.parseLong(id));
		
		if(!horaExtraBuscada.isPresent()) {
			return null;
		}
		
		HoraExtra horaExtra = horaExtraBuscada.get();
				
		model.addAttribute("listaTipoHoras", listaTipoHoras);
		model.addAttribute("horaExtra", horaExtra);
		
		
		return "hora_extra/formNovaHoraExtra";
	}
	
	@GetMapping("apagar")
	public String apagar (@RequestParam("id") String id) {
		
		Optional<HoraExtra> horaExtraBuscada = horaExtraRepository.findById(Long.parseLong(id));
		
		if(!horaExtraBuscada.isPresent()) {
			return null;
		}
				
		HoraExtra horaExtra = horaExtraBuscada.get();
		
		
		
		horaExtraRepository.delete(horaExtra);
		
		return "redirect:/horaextra/listarUsuario";
	}
	
		
	@GetMapping("autorizacao")
	public String autorizacao(Model model) {
		
		String chapa= SecurityContextHolder.getContext().getAuthentication().getName();

		Optional<Usuario> usuarioBuscado = usuarioRepository.findById(chapa);
				
		if(!usuarioBuscado.isPresent()) {
			return null;
		}
			
		Usuario usuario = usuarioBuscado.get();

		model.addAttribute("usuario", usuario);
		
		List<HoraExtra> horasExtras = horaExtraRepository.findAll();
		
		model.addAttribute("horasExtras", horasExtras);
		
		return "hora_extra/autorizarHoraExtra";
		
	}
	
	
	@GetMapping("autorizar")
	public String autorizar (@RequestParam("id") String id, Model model) {
		
		Optional<HoraExtra> horaExtraBuscada = horaExtraRepository.findById(Long.parseLong(id));
		
		if(!horaExtraBuscada.isPresent()) {
			return null;
		}
		
		HoraExtra horaExtra = horaExtraBuscada.get();
		horaExtra.setStatus(Status.APROVADA);
		horaExtraRepository.save(horaExtra);
				
		model.addAttribute("horaExtra", horaExtra);
		
		
		return "redirect:/horaextra/autorizacao";
	}
	
	@GetMapping("negar")
	public String negar(@RequestParam("id") String id, Model model) {
		
		Optional<HoraExtra> horaExtraBuscada = horaExtraRepository.findById(Long.parseLong(id));
		
		if(!horaExtraBuscada.isPresent()) {
			return null;
		}
		
		HoraExtra horaExtra = horaExtraBuscada.get();
		horaExtra.setStatus(Status.NEGADA);
		horaExtraRepository.save(horaExtra);
		
		model.addAttribute("horaExtra", horaExtra);
		
		
		return "redirect:/horaextra/autorizacao";
	}
	
}
