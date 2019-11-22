package de.htwberlin.de.TheBayOfPirates.registration.controller;

import de.htwberlin.de.TheBayOfPirates.registration.model.Role;
import de.htwberlin.de.TheBayOfPirates.registration.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RoleInitiationController {

    @Autowired
    RoleRepository roleRepository;

    @PostConstruct
    public void initApiRoleData() {
        if(roleRepository.findByRoleName("USER") != null){
            return;
        }
        Role user = new Role();
        user.setRoleName("USER");
        // set user properties here
        roleRepository.save(user);
    }

}