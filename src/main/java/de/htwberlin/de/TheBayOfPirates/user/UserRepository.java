package de.htwberlin.de.TheBayOfPirates.user;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.Email;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(@Email String email);

    Optional<User> findByUsername(String username);

    Optional<User> findByUserID(UUID userID);

    Optional<User> findByImage(String name);
}
