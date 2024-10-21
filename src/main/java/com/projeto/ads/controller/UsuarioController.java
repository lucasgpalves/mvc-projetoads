package com.projeto.ads.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.ads.model.Usuario;
import com.projeto.ads.repository.RoleRepository;
import com.projeto.ads.repository.UsuarioRepository;



@Controller
public class UsuarioController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository userRepository;

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

    @PostMapping("usuario/inserir")
    public ModelAndView salvarUsuario(
        @ModelAttribute Usuario user, 
        @RequestParam("confirmPassword") String confirmPassword,
        @RequestParam("dataNasc") String dataNasc,
        @RequestParam("roleUser") String roleUser
    ) {
        ModelAndView mv = new ModelAndView();

        if(user.getPassword().length() < 8) {
            mv.setViewName("Login/cadastro");
            mv.addObject("error", "A senha possuí menos de 8 dígitos");
            mv.addObject("roles", roleRepository.findAll());
            return mv;
        }

        if(!user.getPassword().equals(confirmPassword)) {
            mv.setViewName("Login/cadastro");
            mv.addObject("error", "As senhas não correspondem");
            mv.addObject("roles", roleRepository.findAll());
            return mv;
        }
        Date dataFormatada = null;

        try {
            SimpleDateFormat formataEntrada = new SimpleDateFormat("yyyy-MM-dd");
            dataFormatada = formataEntrada.parse(dataNasc);
        } catch (ParseException error) {
            error.printStackTrace();
        }

        user.setDataNascimento(dataFormatada);
        user.setUsername(user.getEmail());

        Usuario aux = userRepository.findByUsername(user.getUsername());

        if(aux == null) {
            user.setRole(roleRepository.findByNome(roleUser));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
    
            userRepository.save(user);
    
            mv.setViewName("redirect:/login");
            
            return mv;
        } else {
            mv.setViewName("Login/cadastro");
            mv.addObject("error", "Usuário já foi cadastrado");
            mv.addObject("roles", roleRepository.findAll());
            return mv;
        }
    }
    
}


