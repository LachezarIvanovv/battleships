package com.example.battleships.services;

import com.example.battleships.models.User;
import com.example.battleships.models.dtos.LoginDTO;
import com.example.battleships.models.dtos.UserRegistrationDTO;
import com.example.battleships.repositories.UserRepository;
import com.example.battleships.session.LoggedUser;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final LoggedUser userSession;

    public AuthService(UserRepository userRepository, LoggedUser userSession) {
        this.userRepository = userRepository;
        this.userSession = userSession;
    }

    public boolean register(UserRegistrationDTO userRegistrationDTO){
        if(!userRegistrationDTO.getPassword().equals(userRegistrationDTO.getConfirmPassword())){
            return false;
        }

        Optional<User> byEmail = this.userRepository.findByEmail(userRegistrationDTO.getEmail());
        if(byEmail.isPresent()){
            return false;
        }

        Optional<User> byUsername = this.userRepository.findByUsername(userRegistrationDTO.getUsername());
        if(byUsername.isPresent()){
            return false;
        }

        User user = new User();
        user.setUsername(userRegistrationDTO.getUsername());
        user.setEmail(userRegistrationDTO.getEmail());
        user.setFullName(userRegistrationDTO.getFullName());
        user.setPassword(userRegistrationDTO.getPassword());

        this.userRepository.save(user);

        return true;
    }

    public boolean login(LoginDTO loginDTO) {
        Optional<User> user = this.userRepository
                .findByUsernameAndPassword(loginDTO.getUsername(), loginDTO.getPassword());

        if (user.isEmpty()) {
            return false;
        }

        this.userSession.login(user.get());

        return true;
    }
}
