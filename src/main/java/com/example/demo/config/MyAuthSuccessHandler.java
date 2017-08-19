package com.example.demo.config;


import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

@Component
public class MyAuthSuccessHandler implements AuthenticationSuccessHandler {
    private Logger logger = Logger.getLogger(MyAuthSuccessHandler.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req,
                                        HttpServletResponse resp,
                                        Authentication authentication)
            throws IOException, ServletException {
        logger.warn("in successHandler role: " + authentication.getAuthorities());
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority r : authorities) {
            if (r.getAuthority().equals("USER")) redirectStrategy.sendRedirect(req, resp, "/user");
            else if (r.getAuthority().equals("ADMIN")) redirectStrategy.sendRedirect(req, resp, "/admin");
        }

    }
}
