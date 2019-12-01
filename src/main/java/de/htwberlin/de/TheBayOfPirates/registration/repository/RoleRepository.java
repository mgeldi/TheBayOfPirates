package de.htwberlin.de.TheBayOfPirates.registration.repository;

import de.htwberlin.de.TheBayOfPirates.registration.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Optional<Role> findByRole(String role);
}
