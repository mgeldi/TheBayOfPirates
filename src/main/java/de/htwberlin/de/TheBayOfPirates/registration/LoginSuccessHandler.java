package de.htwberlin.de.TheBayOfPirates.registration;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{


    protected void handle(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String targetUrl = determineUrl();

        if (!response.isCommitted()) {
            response.sendRedirect(targetUrl);
        }
    }

    public static Authentication getAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();

        return context.getAuthentication();
    }

   private List<String> getAuthorities() {
       Authentication authentication = getAuthentication();


       Collection<? extends GrantedAuthority> grantedAuthorityList = authentication
               .getAuthorities();

       List<String> authorities = new ArrayList<String>();

       for (GrantedAuthority grantedAuthority : grantedAuthorityList) {
           authorities.add(grantedAuthority.getAuthority());
       }

       return authorities;
   }

    protected String determineUrl() {
        String url = "/login?error=true";

        if (getAuthorities().contains("ADMIN")) {
            url = "/admin";
        }
        else if (getAuthorities().contains("USER")) {
            url = "/home";
        }
        return url;
    }
}

