package de.htwberlin.de.TheBayOfPirates.user;


import de.htwberlin.de.TheBayOfPirates.role.Role;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userID;

    @NotNull
    @Length(min = 3, max = 24, message = "Name must be between 3 and 24 characters long!")
    private String name;

    @NotNull
    @Length(min = 3, max = 24, message = "Surname must be between 3 and 24 characters long!")
    private String surname;

    @NotNull
    @Email(message = "The Mail address is incorrect")
    @Column(unique = true)
    private String email;

    @NotNull
    @Column(unique = true)
    @Length(min = 5, max = 16, message = "Username must be between 5 and 16 characters long!")
    private String username;

    @NotNull
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "userrole", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "roleid"))
    private Set<Role> roles;


    @Length(max = 1000, message = "The description has a limit of 1000 characters")
    private String description;

    @Length(max = 10, message = "The gender has a limit of 10 characters")
    private String gender;

    private boolean enabled;

    @Column(name = "image")
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] image;

    private String imageName;

    private boolean hasProfilePicture;


    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean hasProfilePicture() {
        return hasProfilePicture;
    }

    public void setHasProfilePicture(boolean hasProfilePicture) {
        this.hasProfilePicture = hasProfilePicture;
    }

    public void setRoles(HashSet<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return this.roles;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public User() {

    }

    public User(@Length(min = 3, max = 24, message = "Name must be between 3 and 24 characters long!")
                        String name, @Length(min = 3, max = 24, message = "Surname must be between 3 and 24 characters long!")
                        String surname, @Email String email, @NotNull String password, UUID userID) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.userID = userID;

    }


}
