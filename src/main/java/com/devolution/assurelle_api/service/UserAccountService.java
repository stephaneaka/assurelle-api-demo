package com.devolution.assurelle_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.devolution.assurelle_api.model.entity.UserAccount;
import com.devolution.assurelle_api.repository.UserAccountRepository;

@Service
public class UserAccountService implements UserDetailsService{
    @Autowired
    private UserAccountRepository repository;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserAccountDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> userAccount = repository.findByEmail(username);

        return userAccount.map(UserAccountDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }
    public String addUser(UserAccount userInfo) {
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "User Added Successfully";
    }
    public Boolean userExist(String username) {
        Optional<UserAccount> userAccount = repository.findByEmail(username);
        return userAccount.isPresent();
    }

    public List<UserAccount> allUsers() {
        return repository.findAll();
    }

}
