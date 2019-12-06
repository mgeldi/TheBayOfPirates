package de.htwberlin.de.TheBayOfPirates.repository;

import de.htwberlin.de.TheBayOfPirates.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    public Optional<Role> findByRole(String role);
}
