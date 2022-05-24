package br.com.OTime.controller;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.OTime.model.HoraExtra;
import br.com.OTime.model.Usuario;
import br.com.OTime.repository.HoraExtraRepository;
import br.com.OTime.repository.UsuarioRepository;

@Controller
@RequestMapping("home")
public class HomeController {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private HoraExtraRepository horaExtraRepository;
	
	
	@GetMapping
	private String home(Model model) {
		
		String chapa = SecurityContextHolder.getContext().getAuthentication().getName();
		
		Optional<Usuario> usuarioBuscado = usuarioRepository.findById(chapa);
		
		if(!usuarioBuscado.isPresent()) {
			return null;
		}
		
		Usuario usuario = usuarioBuscado.get();
		
		List<HoraExtra> horaExtraBuscada = horaExtraRepository.findAllByUsuario(chapa);
		
		GregorianCalendar gcComp = new GregorianCalendar();
		gcComp.set(Calendar.SECOND, 0);
		gcComp.set(Calendar.MINUTE, 0);
		gcComp.set(Calendar.HOUR, 0);
		
		GregorianCalendar gc = new GregorianCalendar();
		gc.set(Calendar.SECOND, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.HOUR, 0);
		
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		
		
		
		for (HoraExtra horaExtra : horaExtraBuscada) {
			
			if(horaExtra.getTipo().toString().equals("COMPENSACAO")) {
				
				gcComp.add(Calendar.SECOND,horaExtra.getHoras().getSeconds());
				gcComp.add(Calendar.MINUTE,horaExtra.getHoras().getMinutes());
				gcComp.add(Calendar.HOUR,horaExtra.getHoras().getHours());
				
				
				
			}if(horaExtra.getTipo().toString().equals("HORAEXTRA")) {
											

				gc.add(Calendar.SECOND,horaExtra.getHoras().getSeconds());
				gc.add(Calendar.MINUTE,horaExtra.getHoras().getMinutes());
				gc.add(Calendar.HOUR,horaExtra.getHoras().getHours());
				
				
			}
				
		}
		
		
		System.out.println("A compensar: " + sdf.format(gcComp.getTime()));
		 
		System.out.println("Horas Extras: " + sdf.format(gc.getTime()));
		
		model.addAttribute("usuario", usuario);
		
		return "home";
	}
	
	
}



