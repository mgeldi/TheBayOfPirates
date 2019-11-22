package de.htwberlin.de.TheBayOfPirates.registration.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Table(name="role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleID;

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID){

    }

    @Column(unique = true)
    @ColumnDefault("USER")
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Role() {

    }

    public Role(String roleName) {
        this.roleName = roleName;
    }
}
