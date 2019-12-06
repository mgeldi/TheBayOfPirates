package de.htwberlin.de.TheBayOfPirates.entity;

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "userrole", joinColumns = @JoinColumn(name = "userid"), inverseJoinColumns = @JoinColumn(name = "roleid"))
    private Set<Role> roles;

    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User() {

    }

    public User(@Length(min = 3, max = 24, message = "Name must be between 3 and 24 characters long!")
                        String name, @Length(min = 3, max = 24, message = "Surname must be between 3 and 24 characters long!")
                        String surname, @Email String email, @NotNull String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public void setRoles(HashSet<Role> roles) {
        this.roles = roles;
    }
}
