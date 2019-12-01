package de.htwberlin.de.TheBayOfPirates;

import de.htwberlin.de.TheBayOfPirates.registration.model.User;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class MyUserDetails implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> roles;
    private boolean enabled;

    public MyUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        //this.roles = Arrays.asList(new SimpleGrantedAuthority(user.getRole()));
        this.enabled = user.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false; //for now just return false
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //for now just return true
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
