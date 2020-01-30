package de.htwberlin.de.TheBayOfPirates.user;

import de.htwberlin.de.TheBayOfPirates.role.Role;
import de.htwberlin.de.TheBayOfPirates.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    BCryptPasswordEncoder pwEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    private static final short MAX_FILE_SIZE_IN_KILO_BYTES = 500;


    public UserServiceImpl(BCryptPasswordEncoder pwEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.pwEncoder = pwEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserServiceImpl() {

    }

    @PostConstruct
    public void initRoles() {
        if (!roleRepository.findByRole("USER").isPresent()) {
            roleRepository.save(new Role("USER"));
        }
    }

    @Override
    public void saveUser(@Valid User user) throws Exception {
        user.setPassword(pwEncoder.encode(user.getPassword()));
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
        ;
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveUserProfile(byte[] imageByte, String description, String gender, String imageName, String userEmail) throws Exception {
        if ((imageByte.length / 1000) < MAX_FILE_SIZE_IN_KILO_BYTES) {
            Optional<User> user = userRepository.findByEmail(userEmail);
            if (!user.isPresent())
                throw new Exception("User not found!");
            User userProfile = user.get();
            userProfile.setHasProfilePicture(true);
            userProfile.setDescription(description);
            userProfile.setGender(gender);
            userProfile.setImage(imageByte);
            userProfile.setImageName(imageName);
            userRepository.saveAndFlush(userProfile);
            return userProfile;
        } else {
            throw new Exception("The uploaded picture is neither a png or jpg");
        }
    }
}
