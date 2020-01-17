package de.htwberlin.de.TheBayOfPirates.user;

import de.htwberlin.de.TheBayOfPirates.role.Role;
import de.htwberlin.de.TheBayOfPirates.role.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.StringTokenizer;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    BCryptPasswordEncoder pwEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserService userService;

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
        System.out.println("Initializing User role!");
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
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUserName(String username) throws Exception {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new Exception("User profile not found!");
        }
        return user;
    }

    @Override
    public User saveUserProfile(byte[] imageByte, String description, String gender, String imageName, String userEmail) throws Exception {
        /*if (imageName.endsWith(".png") || imageName.endsWith(".jpg") && */
        if ((imageByte.length / 1000) < MAX_FILE_SIZE_IN_KILO_BYTES) {
            Optional<User> user = userRepository.findByEmail(userEmail);
            if (!user.isPresent())
                throw new Exception("User not found!");
            System.out.println(imageByte.length);
            BufferedImage createImg = ImageIO.read(new ByteArrayInputStream(imageByte));
            File file = new File("src/main/java/resources/images/" + userEmail + imageName);
            file.createNewFile();
            StringTokenizer typeTokenizer = new StringTokenizer(imageName, ".");
            String type = "";
            while (typeTokenizer.hasMoreTokens()) {
                type = typeTokenizer.nextToken();
                System.out.println(type);
            }
            OutputStream os = new BufferedOutputStream(new FileOutputStream(file));
            os.write(imageByte);
            User userProfile = user.get();
            userProfile.setHasProfilePicture(true);
            userProfile.setDescription(description);
            userProfile.setGender(gender);
            userProfile.setImage(imageByte);
            userProfile.setImageName(imageName);
            userRepository.save(userProfile);

            return userProfile;
        } else {
            throw new Exception("The uploaded picture is neither a png or jpg");
        }
    }


}
