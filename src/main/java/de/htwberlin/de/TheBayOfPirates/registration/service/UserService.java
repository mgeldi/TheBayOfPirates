package de.htwberlin.de.TheBayOfPirates.registration.service;

import de.htwberlin.de.TheBayOfPirates.registration.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * Saves the user (registration).
     *
     * @param user User with full data
     */
    public void saveUser(User user);

    /**
     * Checks if the user (username or email) already exists.
     *
     * @param user User with full data
     * @return True, if it already exists, otherwise false
     */
    public boolean userExists(User user);

}
