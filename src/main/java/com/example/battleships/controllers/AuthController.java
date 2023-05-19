package com.example.battleships.controllers;

import com.example.battleships.models.dtos.LoginDTO;
import com.example.battleships.models.dtos.UserRegistrationDTO;
import com.example.battleships.services.AuthService;
import com.example.battleships.session.LoggedUser;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final AuthService authService;

    private final LoggedUser loggedUser;

    public AuthController(AuthService authService, LoggedUser loggedUser) {
        this.authService = authService;
        this.loggedUser = loggedUser;
    }

    @ModelAttribute("registrationDTO")
    public UserRegistrationDTO getRegistrationDTO(){
        return new UserRegistrationDTO();
    }

    @ModelAttribute("loginDTO")
    public LoginDTO initLoginDTO(){
        return new LoginDTO();
    }

    @GetMapping("/register")
    public String register(){

        long loggedUserId = this.loggedUser.getId();

        if(loggedUserId != 0){
            return "redirect:/home";
        }
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegistrationDTO registrationDTO,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        long loggedUserId = this.loggedUser.getId();

        if(loggedUserId != 0){
            return "redirect:/home";
        }

        if(bindingResult.hasErrors() || !this.authService.register(registrationDTO)){
            redirectAttributes.addFlashAttribute("registrationDTO", registrationDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingsResult.registrationDTO", bindingResult);

            return "reddirect:/register";
        }
        return "login";
    }

    @GetMapping ("/login")
    public String login(){

        long loggedUserId = this.loggedUser.getId();

        if(loggedUserId != 0){
            return "redirect:/home";
        }

        return "login";
    }

    @PostMapping("/login")
    public String login(@Valid LoginDTO loginDTO,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes){

        long loggedUserId = this.loggedUser.getId();

        if(loggedUserId != 0){
            return "redirect:/home";
        }

        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingsResult.loginDTO", bindingResult);

            return "redirect:/login";
        }

        if(!this.authService.login(loginDTO)){
            redirectAttributes.addFlashAttribute("loginDTO", loginDTO);
            redirectAttributes.addFlashAttribute("badCredentials", true);

            return "reddirect:/login";
        }

        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(){
        this.authService.logout();

        return "redirect:/";
    }
}
