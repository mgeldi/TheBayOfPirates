package de.htwberlin.de.TheBayOfPirates.service;

import de.htwberlin.de.TheBayOfPirates.entity.Role;
import de.htwberlin.de.TheBayOfPirates.entity.User;
import de.htwberlin.de.TheBayOfPirates.repository.RoleRepository;
import de.htwberlin.de.TheBayOfPirates.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    BCryptPasswordEncoder pwEncoder;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserService userService;

    private static final short MAX_FILE_SIZE_IN_KILO_BYTES = 500 ;


    public UserServiceImpl(BCryptPasswordEncoder pwEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.pwEncoder = pwEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserServiceImpl(){

    }

    @PostConstruct
    public void initRoles(){
        System.out.println("Initializing User role!");
        if(!roleRepository.findByRole("USER").isPresent()){
            roleRepository.save(new Role("USER"));
        }
    }

    @Override
    public void saveUser(@Valid User user) throws Exception {
        user.setPassword(pwEncoder.encode(user.getPassword()));
        //todo: add role
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
    public User saveUserProfile(byte[] imageByte, String description, String gender, String imageName,String userEmail) throws Exception {
        if(imageName.endsWith(".png")|| imageName.endsWith(".jpg") &&
                (imageByte.length/ 1000) < MAX_FILE_SIZE_IN_KILO_BYTES){
            User userProfile = new User();
            userProfile.setDescription(description);
            Optional<User> user = userRepository.findByEmail(userEmail);
            if (!user.isPresent())
                throw new Exception("User not found!");
            userProfile.setGender(gender);
            userProfile.setImage(imageByte);
            userRepository.save(userProfile);
            return userProfile;
        }else{
            throw new Exception("The uploaded picture is neither a png or jpg");
        }
    }

    public File uploadPic(String pictureName) throws Exception {
        Optional<User> user = userRepository.findByImage(pictureName);
        File file = new File(System.getProperty(System.getProperty("user.home") + "/" + "" + pictureName + ".jpg"));
        if (file.exists() == true) {
            return file;
        } else {
            throw new Exception("File does not exist");
        }
    }
}
