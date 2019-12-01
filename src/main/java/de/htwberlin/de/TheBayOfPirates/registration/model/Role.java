package de.htwberlin.de.TheBayOfPirates.registration.model;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "roleid")
    private int id;
    @Column(name = "role", unique = true)
    private String role;

    public Role(String role){
        this.role = role;
    }

    public Role(){

    }
}