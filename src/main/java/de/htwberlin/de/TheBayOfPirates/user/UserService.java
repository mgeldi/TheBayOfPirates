package de.htwberlin.de.TheBayOfPirates.user;


import java.io.File;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {



    /**
     * Saves the user (registration).
     *
     * @param user User with full data
     */
    public void saveUser(User user) throws Exception;

    /**
     * Checks if the user (username or email) already exists.
     *
     * @param user User with full data
     * @return True, if it already exists, otherwise false
     */
    public boolean userExists(User user);

    /**
     * Find user by email.
     * @param email
     * @return
     */
    public Optional<User> findByUserEmail(String email);

    public Optional<User> findByUserName(String username) throws Exception;

    public User saveUserProfile(byte [] imageByte, String description, String gender, String imageName, String mail) throws Exception;

}
