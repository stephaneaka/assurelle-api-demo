package com.devolution.assurelle_api.controller.api.v1;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devolution.assurelle_api.model.entity.UserAccount;
import com.devolution.assurelle_api.service.UserAccountService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/admin")
@Tag(name = "Admin", description = "Interfaces d'administration")

public class AdminController {

    @Autowired
    private UserAccountService userAccountService;

 @PostMapping("/users")
    //@Secured("ROLE_ADMIN")
    public String newUser(@RequestBody UserRegistrationInfos requestBody
    ) {
        UserAccount u = new UserAccount();
        u.setEmail(requestBody.email);
        u.setName(requestBody.userName);
        u.setPassword(requestBody.password);
        if (requestBody.isAdmin) {
            u.setRoles("ROLE_ADMIN");
        }
        return userAccountService.addUser(u);
    }
    @GetMapping("/users")
    @Secured("ROLE_ADMIN")
    public List<UserAccount> users() {
        return userAccountService.allUsers();
    }
    public record UserRegistrationInfos(
        String email, 
        String userName, 
        String password, 
        boolean isAdmin
    ) {
    }
}
