package mx.com.personal.springboot.app.controllers;

import java.security.Principal;

import org.springframework.ui.Model;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mx.com.personal.springboot.app.Constants;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String login(@RequestParam(name = "error", required = false) String error, 
			@RequestParam(name="logout", required=false) String logout, Model model, Principal principal,
			RedirectAttributes flash) {

		// Si ya esta firmado
		if (principal != null) {
			flash.addFlashAttribute(Constants.INFO, "Ya ha iniciado sesion anteriormente");
			return "redirect:/";
		}
		
		if(error != null) {
			model.addAttribute(Constants.ERROR, "Usuario o contraseña invalida");
		}
		
		if(logout != null) {
			model.addAttribute(Constants.SUCCESS, "La sesion se cerro con exito");
		}

		return "login";
	}
}
