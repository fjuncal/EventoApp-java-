package com.eventosapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.eventosapp.models.Usuario;
import com.eventosapp.repository.UsuarioRepository;

@Controller
public class UsuarioController {
	
	@Autowired
	private UsuarioRepository ur;
	
	
	
	@RequestMapping(value = "/cadastro", method = RequestMethod.POST)
	public String logar(@Valid Usuario usuario, BindingResult result, RedirectAttributes attributes ) {
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Campo(s) inválido(s)");
			return "redirect:/";	
		}
		ur.save(usuario);
		attributes.addFlashAttribute("mensagem", "usuario cadastrado com sucesso");
		return "logar";
	}
	
	@RequestMapping(value = "/logarUsu", method = RequestMethod.POST)
	public String usuLogado(@RequestParam String email, @RequestParam String senha) {
		
		Usuario usr = ur.findByEmail(email);
		if(usr.autentica(senha)) {
			return "index";
		}
		//TODO
		return "senhaIncorreta";
	}
	
	@RequestMapping(value = "/alterarSenha")
	public String alterarSenha() {
		return "formAlterarSenha";
	}
	
	@RequestMapping(value = "/senhaAtualizada", method = RequestMethod.POST)
	public String senhaAlterada(@RequestParam String email, @RequestParam String senha) {
		Usuario usr = ur.findByEmail(email);
		usr.setSenha(senha);
		ur.save(usr);

		return "logar";
		
		
	}

}
