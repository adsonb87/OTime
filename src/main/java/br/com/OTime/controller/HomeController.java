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
		
		
		GregorianCalendar gcSaldo = new GregorianCalendar();
		gcSaldo.set(Calendar.SECOND, 0);
		gcSaldo.set(Calendar.MINUTE, 0);
		gcSaldo.set(Calendar.HOUR, 0);
		
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		
		Integer he = 0;
		Integer me = 0;
		Integer se = 0;
		Integer hc = 0;
		Integer mc = 0;
		Integer sc = 0;
		Integer saldoh = 0;
		Integer saldom = 0;
		Integer saldos = 0;
		
		for (HoraExtra horaExtra : horaExtraBuscada) {
			
			if(horaExtra.getTipo().toString().equals("COMPENSACAO")) {
				
				hc = hc + horaExtra.getHoras().getHours();
				mc = mc + horaExtra.getHoras().getMinutes();
				sc = sc + horaExtra.getHoras().getSeconds();
				
				
			}else if(horaExtra.getTipo().toString().equals("HORAEXTRA")) {									
				
				he = he + horaExtra.getHoras().getHours();
				me = me + horaExtra.getHoras().getMinutes();
				se = se + horaExtra.getHoras().getSeconds();
				
			}
				
		}
		
		
		saldoh = he - hc;
		saldom = me - mc;
		saldos = se - sc;
		
		gcSaldo.set(Calendar.SECOND, saldos);
		gcSaldo.set(Calendar.MINUTE, saldom);
		gcSaldo.set(Calendar.HOUR, saldoh);
		
		gcComp.set(Calendar.SECOND, sc);
		gcComp.set(Calendar.MINUTE, mc);
		gcComp.set(Calendar.HOUR, hc);
		
		gc.set(Calendar.SECOND, se);
		gc.set(Calendar.MINUTE, me);
		gc.set(Calendar.HOUR, he);
		
		
		System.out.println("A compensar: " + sdf.format(gcComp.getTime()));
		System.out.println("Horas Extras: " + sdf.format(gc.getTime()));
		
		System.out.println(saldoh +"-"+ saldom +"-"+ saldos);
		System.out.println("Saldo: " + sdf.format(gcSaldo.getTime()));
		
		
		model.addAttribute("usuario", usuario);
		model.addAttribute("horasExtras", sdf.format(gc.getTime()));
		model.addAttribute("horasCompensadas", sdf.format(gcComp.getTime()));
		model.addAttribute("saldo", sdf.format(gcSaldo.getTime()));
		
		return "home";
	}
	
	
}



