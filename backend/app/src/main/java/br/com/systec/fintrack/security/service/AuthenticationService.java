package br.com.systec.fintrack.security.service;

import br.com.systec.fintrack.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    private UserService service;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return service.findByLogin(login);
    }
}