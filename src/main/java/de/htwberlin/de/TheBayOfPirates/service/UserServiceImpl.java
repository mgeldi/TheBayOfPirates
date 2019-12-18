package de.htwberlin.de.TheBayOfPirates.service;

import de.htwberlin.de.TheBayOfPirates.entity.Role;
import de.htwberlin.de.TheBayOfPirates.entity.User;
import de.htwberlin.de.TheBayOfPirates.repository.RoleRepository;
import de.htwberlin.de.TheBayOfPirates.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    BCryptPasswordEncoder pwEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;


    public UserServiceImpl(BCryptPasswordEncoder pwEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.pwEncoder = pwEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserServiceImpl(){

    }

    @PostConstruct
    public void initRoles(){
        System.out.println("Initializing User role!");
        if(!roleRepository.findByRole("USER").isPresent()){
            roleRepository.save(new Role("USER"));
        }
    }

    @Override
    public void saveUser(@Valid User user) throws Exception {
        user.setPassword(pwEncoder.encode(user.getPassword()));
        //todo: add role
        Optional<Role> userRole = roleRepository.findByRole("USER");
        Role role = userRole.get();
        user.setRoles(new HashSet<Role>(Arrays.asList(role)));
        userRepository.save(user);
    }

    @Override
    public boolean userExists(@Valid User user) {
        Optional<User> existingUserEmail = userRepository.findByEmail(user.getEmail());
        Optional<User> existingUserName = userRepository.findByUsername(user.getUsername());
        return existingUserEmail.isPresent() || existingUserName.isPresent();
    }

    @Override
    public Optional<User> findByUserEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
