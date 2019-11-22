package de.htwberlin.de.TheBayOfPirates.registration.service;

import de.htwberlin.de.TheBayOfPirates.registration.model.Role;
import de.htwberlin.de.TheBayOfPirates.registration.model.User;
import de.htwberlin.de.TheBayOfPirates.registration.repository.RoleRepository;
import de.htwberlin.de.TheBayOfPirates.registration.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    BCryptPasswordEncoder pwEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;


    @Override
    public void saveUser(User user) {
        user.setEncryptedPassword(pwEncoder.encode(user.getEncryptedPassword()));
        Role userRole = roleRepository.findByRoleName("USER");
        user.setRole(userRole.getRoleID());
        userRepository.save(user);
    }

    @Override
    public boolean userExists(User user) {
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
