package de.htwberlin.de.TheBayOfPirates.role;

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

    public void setRoleName(String user) {
    }

    public void setRoleID(int i) {
    }

    public String getRoleName(){
        return this.role;
    }
}