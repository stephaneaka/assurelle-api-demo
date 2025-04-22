package com.devolution.assurelle_api.controller.api.v1;

import org.springframework.web.bind.annotation.RestController;

import com.devolution.assurelle_api.model.entity.Guarantee;
import com.devolution.assurelle_api.model.entity.Product;
import com.devolution.assurelle_api.model.entity.UserAccount;
import com.devolution.assurelle_api.model.entity.VehicleCategory;
import com.devolution.assurelle_api.repository.GuaranteeRepository;
import com.devolution.assurelle_api.repository.ProductRepository;
import com.devolution.assurelle_api.repository.VehicleCategoryRepository;
import com.devolution.assurelle_api.service.JwtService;
import com.devolution.assurelle_api.service.UserAccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@Tag(name = "Base", description = "Les Interfaces de base")

public class DefaultController {
    
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private VehicleCategoryRepository vehicleCategoryRepository;
    @Autowired
    private GuaranteeRepository guaranteeRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Page d'accueil", description = "Affiche un message de bienveneue")
    @GetMapping("/api/v1/home")
    public String home() {
        return new String("Bienvenue chez DEVOLUTION !");
    }

     @PostMapping("/api/v1/token")
    public ResponseEntity<AuthResponse> authenticateAndGetToken(@RequestBody AuthRequestBody authRequest) {
        
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.email, authRequest.password));
        if (authentication.isAuthenticated()) {
            String token =jwtService.generateToken(authRequest.email);
            AuthResponse response =new AuthResponse(
                token, 
                jwtService.extractExpiration(token)
            );

            return new ResponseEntity<AuthResponse>(response,HttpStatus.OK);
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @PostMapping("/api/v1/register")
    @Operation(summary = "Création de Compte")
    public String register(@RequestBody RegistrationInfos userInfos
    ) {
        UserAccount u = new UserAccount();
        u.setEmail(userInfos.email());
        u.setName(userInfos.userName());
        u.setPassword(userInfos.password);
        if (userInfos.isAdmin) {
            u.setRoles("ROLE_ADMIN");
        }
        //return userInfos.password();
        return userAccountService.addUser(u);
    }

    @GetMapping("/api/v1/users")
    //@Secured("ROLE_ADMIN")
    public List<UserAccount> users() {
        return userAccountService.allUsers();
    }

    @Operation(summary = "Catalogue de produits", description = "Permet d'obtenir la liste de tous les produits du catalogue")
    @GetMapping("/api/v1/products")
    public List<Product> products() {
        return productRepository.findAll();
    }

    @Operation(summary = "Catégorie de Véhicules", description = "Permet d'obtenir la liste des catégories de véhicules elligibes")
    @GetMapping("/api/v1/vehicle-categories")
    public List<VehicleCategory> vehicleCategories() {
        return vehicleCategoryRepository.findAll();
    }
    @Operation(summary = "Garanties", description = "Permet d'obtenir la liste de toutes des ggaranties disponibles dans le catalogue de produits")
    @GetMapping("/api/v1/guarantees")
    public List<Guarantee> guarantees() {
        return guaranteeRepository.findAll();
    }

    public record AuthRequestBody(
        String email, 
        String password) {
    }

    public record AuthResponse(
        String token, 
        Date expire) {
    }

    public record RegistrationInfos(
        String email, 
        String userName, 
        boolean isAdmin,
        String password) {
    }
}
