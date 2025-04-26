package com.devolution.assurelle_api.controller.api.v1;

import org.springframework.web.bind.annotation.RestController;

import com.devolution.assurelle_api.model.entity.Guarantee;
import com.devolution.assurelle_api.model.entity.Product;
import com.devolution.assurelle_api.model.entity.UserAccount;
import com.devolution.assurelle_api.model.entity.VehicleCategory;
import com.devolution.assurelle_api.model.record.AuthRequest;
import com.devolution.assurelle_api.model.record.AuthResponse;
import com.devolution.assurelle_api.model.record.RegistrationInfos;
import com.devolution.assurelle_api.repository.GuaranteeRepository;
import com.devolution.assurelle_api.repository.ProductRepository;
import com.devolution.assurelle_api.repository.VehicleCategoryRepository;
import com.devolution.assurelle_api.service.UserAccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserAccountService userAccountService;

    @Operation(summary = "Page d'accueil", description = "Affiche un message de bienveneue")
    @GetMapping("/api/v1/home")
    public String home() {
        return new String("Bienvenue chez DEVOLUTION !");
    }

    @PostMapping("/api/v1/token")
    public ResponseEntity<AuthResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        return new ResponseEntity<AuthResponse>(authenticate(authRequest.email(), authRequest.password()),HttpStatus.OK);
    }

    @PostMapping("/api/v1/register")
    @Secured("ROLE_ADMIN")
    @Operation(summary = "Création de Compte")
    @SecurityRequirement(name = "Bearer Authentication")
    public String register(@RequestBody RegistrationInfos userInfos
    ) {
        UserAccount u = new UserAccount();
        u.setName(userInfos.email());
        u.setPassword(userInfos.password());
        if (userInfos.isAdmin()) {
            u.setRoles("ROLE_ADMIN");
        }
        return userAccountService.addUser(u);
    }
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/api/v1/users")
    @Secured("ROLE_ADMIN")
    public List<UserAccount> users() {
        return userAccountService.allUsers();
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Catalogue de produits", description = "Permet d'obtenir la liste de tous les produits du catalogue")
    @GetMapping("/api/v1/products")
    public List<Product> products() {
        return productRepository.findAll();
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Catégorie de Véhicules", description = "Permet d'obtenir la liste des catégories de véhicules elligibes")
    @GetMapping("/api/v1/vehicle-categories")
    public List<VehicleCategory> vehicleCategories() {
        return vehicleCategoryRepository.findAll();
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(summary = "Garanties", description = "Permet d'obtenir la liste de toutes des ggaranties disponibles dans le catalogue de produits")
    @GetMapping("/api/v1/guarantees")
    public List<Guarantee> guarantees() {
        return guaranteeRepository.findAll();
    }

    private AuthResponse authenticate(String username, String password){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authentication.isAuthenticated()) {
            String token = userAccountService.jwtAuthFilter().jwtService().generateToken(username);
            return new AuthResponse(
                200,
                "Sucsess",
                token, 
                userAccountService.jwtAuthFilter().jwtService().extractExpiration(token)
            );

        } else {
            return new AuthResponse(
                403,
                "Fail",
                "", 
                null
            );
        }
    }
    
}
