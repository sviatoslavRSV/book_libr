package com.example.demo.service;


import com.example.demo.model.login.Role;
import com.example.demo.model.login.Userr;
import com.example.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    boolean accountNonExpired = true;
    boolean credentialsNonExpired = true;
    boolean accountNonLocked = true;

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Userr user = userRepository.findByEmail(userName);
        List<GrantedAuthority> authorities = getAuthorities(user.getRoles());
        UserDetails userDetails = new User(user.getEmail()
                , user.getPassword()
                , user.getActive()
                , accountNonExpired
                , credentialsNonExpired
                , accountNonLocked
                , authorities);
        return userDetails;
    }

    private List<GrantedAuthority> getAuthorities(Set<Role> roleSet) {
        Set<GrantedAuthority> set = new HashSet<>();
        for (Role r : roleSet) {
            set.add(new SimpleGrantedAuthority(r.getRole()));
        }
        return new ArrayList<>(set);
    }
}
