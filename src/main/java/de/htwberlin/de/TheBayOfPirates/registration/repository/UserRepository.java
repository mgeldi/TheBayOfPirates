package de.htwberlin.de.TheBayOfPirates.registration.repository;

import de.htwberlin.de.TheBayOfPirates.registration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    public Optional<User> findByEmail(@Email String email);

    public Optional<User> findByUsername(String username);
}
