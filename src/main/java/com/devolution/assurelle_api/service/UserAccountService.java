package com.devolution.assurelle_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devolution.assurelle_api.filter.JwtAuthFilter;
import com.devolution.assurelle_api.model.entity.UserAccount;
import com.devolution.assurelle_api.repository.UserAccountRepository;

@Service
public class UserAccountService implements UserDetailsService{
    
    @Autowired
    private UserAccountRepository repository ;
   

    private final JwtAuthFilter jwtAuthFilter = new JwtAuthFilter();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();;

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
    public JwtAuthFilter jwtAuthFilter() {
        return jwtAuthFilter;
    }
    public UserAccountRepository getRepository() {
        return repository;
    }

  
    @Override
    public UserAccountDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> userAccount = repository.findByName(username);

        return userAccount.map(UserAccountDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    public String addUser(UserAccount userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }
    
    public Boolean userExist(String username) {
        Optional<UserAccount> userAccount = repository.findByName(username);
        return userAccount.isPresent();
    }

    public List<UserAccount> allUsers() {
        return repository.findAll();
    }

    

    

}
