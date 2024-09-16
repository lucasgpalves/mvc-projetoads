package com.projeto.ads.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.ads.model.Usuario;
import com.projeto.ads.repository.RoleRepository;

@Controller
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("usuario", new Usuario());
        mv.setViewName("Login/login");
        return mv;
    }

    @GetMapping("/dashboard")
    public ModelAndView dashBoard(Authentication authentication) {
        ModelAndView mv = new ModelAndView();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        String username = userDetails.getUsername();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        int atIndex = username.indexOf("@");
        String nameUser = atIndex != -1 ? username.substring(0, atIndex): username;
        mv.addObject("usuario", nameUser);
        mv.addObject("papel", role);
        mv.setViewName("Login/index");
        return mv;
    }

    @GetMapping("usuario/inserir")
    public ModelAndView cadastro() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("roles", roleRepository.findAll());
        mv.addObject("usuario", new Usuario());
        mv.setViewName("Login/cadastro");
        return mv;


    }
}


