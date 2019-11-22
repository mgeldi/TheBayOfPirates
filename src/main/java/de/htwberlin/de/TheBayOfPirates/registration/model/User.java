package de.htwberlin.de.TheBayOfPirates.registration.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.UUID;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userID;

    @Length(min = 3, max = 24, message = "Name must be between 3 and 24 characters long!")
    private String name;

    @Length(min = 3, max = 24, message = "Surname must be between 3 and 24 characters long!")
    private String surname;

    @Email
    private String email;

    @Column(unique=true)
    @Length(min = 5, max = 16, message = "Username must be between 5 and 16 characters long!")
    private String userName;

    private String encryptedPassword;

    @ManyToOne(targetEntity = Role.class)
    private Role roleID;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Role getRole() {
        return roleID;
    }

    public void setRole(Role role) {
        this.roleID = role;
    }

    public UUID getUserID() {
        return userID;
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

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public User() {

    }

    public User(@Length(min = 3, max = 24, message = "Name must be between 3 and 24 characters long!")
                        String name, @Length(min = 3, max = 24, message = "Surname must be between 3 and 24 characters long!")
                        String surname, @Email String email, String encryptedPassword, String encryptedPassword1) {
        this.encryptedPassword = encryptedPassword1;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }
}
