package de.htwberlin.de.TheBayOfPirates.registration.service;

import de.htwberlin.de.TheBayOfPirates.registration.model.User;
import de.htwberlin.de.TheBayOfPirates.registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    BCryptPasswordEncoder pwEncoder;
    @Autowired
    UserRepository userRepository;


    public UserServiceImpl(BCryptPasswordEncoder pwEncoder, UserRepository userRepository) {
        this.pwEncoder = pwEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(@Valid User user) throws Exception {
        user.setPassword(pwEncoder.encode(user.getPassword()));
        //todo: add role
        userRepository.save(user);
    }

    @Override
    public boolean userExists(@Valid User user) {
        User existingUserEmail = userRepository.findByEmail(user.getEmail());
        User existingUserName = userRepository.findByUsername(user.getUsername());
        if(existingUserEmail != null || existingUserName != null){
            return true;
        }
        else {
            return false;
        }
    }
}
