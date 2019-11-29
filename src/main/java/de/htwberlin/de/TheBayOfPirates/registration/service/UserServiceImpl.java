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
    @Autowired
    RoleRepository roleRepository;

    public UserServiceImpl(BCryptPasswordEncoder pwEncoder, UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.pwEncoder = pwEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveUser(@Valid User user) throws Exception {
        user.setEncryptedPassword(pwEncoder.encode(user.getEncryptedPassword()));
        Role userRole = roleRepository.findByRoleName("USER");
        if(userRole == null) {
            throw new Exception("Role not found!");
        }
        user.setRole(userRole);
        userRepository.save(user);
    }

    @Override
    public boolean userExists(@Valid User user) {
        User existingUserEmail = userRepository.findByEmail(user.getEmail());
        User existingUserName = userRepository.findByName(user.getUserName());
        if(existingUserEmail != null || existingUserName != null){
            return true;
        }
        else {
            return false;
        }
    }
}
