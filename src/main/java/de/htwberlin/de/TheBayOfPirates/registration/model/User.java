package de.htwberlin.de.TheBayOfPirates.registration.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Table(name="users")
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
    @Email
    private String email;

    @NotNull
    @Column(unique=true)
    @Length(min = 5, max = 16, message = "Username must be between 5 and 16 characters long!")
    private String userName;

    @NotNull
    private String encryptedPassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
