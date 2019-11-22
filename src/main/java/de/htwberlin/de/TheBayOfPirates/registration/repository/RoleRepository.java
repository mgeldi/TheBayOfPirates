package de.htwberlin.de.TheBayOfPirates.registration.repository;

import de.htwberlin.de.TheBayOfPirates.registration.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    public Role findByRoleName(String role);

}
