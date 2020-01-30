package de.htwberlin.de.TheBayOfPirates.user;


import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {


    /**
     * Saves the user (registration).
     *
     * @param user User with full data
     */
    void saveUser(User user) throws Exception;

    /**
     * Checks if the user (username or email) already exists.
     *
     * @param user User with full data
     * @return True, if it already exists, otherwise false
     */
    boolean userExists(User user);

    /**
     * Find user by email.
     *
     * @param email
     * @return
     */
    Optional<User> findByUserEmail(String email);

    Optional<User> findByUserName(String username);

    User saveUserProfile(byte[] imageByte, String description, String gender, String imageName, String mail) throws Exception;
}
