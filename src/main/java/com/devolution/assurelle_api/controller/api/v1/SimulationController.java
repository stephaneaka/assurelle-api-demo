package com.devolution.assurelle_api.controller.api.v1;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.devolution.assurelle_api.model.entity.Charge;
import com.devolution.assurelle_api.model.entity.Product;
import com.devolution.assurelle_api.model.entity.Subscription;
import com.devolution.assurelle_api.model.entity.Vehicle;
import com.devolution.assurelle_api.model.record.QuoteRequest;
import com.devolution.assurelle_api.model.record.QuoteResponse;
import com.devolution.assurelle_api.repository.ProductRepository;
import com.devolution.assurelle_api.repository.SubscriptionRepository;
import com.devolution.assurelle_api.service.QuoteCalculator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/simulations")
@Secured("ROLE_USER")
@Tag(name = "Simulations", description = "Interfaces de calcul et enregistreememt de devis")
public class SimulationController {
    
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Operation(summary = "Liste des devis", description = "Affiche la totalite des Devis enregistrés")
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("")
    public List<Subscription> getAll(){
        return subscriptionRepository.findByStatus(0);
    }

    @PostMapping("")
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "Bearer Authentication")
    @ResponseBody
    @Operation(summary = "Calculer un devis", description = "Permet de calculer, enregistrer et retourner un devis sur la base des caractéristique du vehicule")
    public QuoteResponse create(@RequestBody QuoteRequest quoteRequest){
        
        Product product = productRepository.findById(quoteRequest.productId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit introuvable"));
        String quoteRef = "QT000000000000";
        Map<String, Double> quoteDetails = QuoteCalculator.calculate(product, quoteRequest);
        
        LocalDateTime quoteDate = LocalDateTime.now();
        
        if (quoteRequest.save()) {
            Subscription subscription = new Subscription();
            subscription.setCreator(SecurityContextHolder.getContext().getAuthentication().getName());
            Long quoteId = subscriptionRepository.save(subscription).getId();
            subscription.setProductId(product.getId());
            subscription.setProductName(product.getName());
            subscription.setQuoteDate(quoteDate);
            
            Vehicle vehicle = new Vehicle();
            vehicle.setFiscalPower(quoteRequest.vehicleFiscalPower());
            vehicle.setOriginalCost(quoteRequest.vehicleOriginalCost());
            vehicle.setMarketCost(quoteRequest.vehicleMarketCost());
            subscription.setVehicle(vehicle);
            
            quoteDetails.forEach( (k,v) -> {
                Charge c = new Charge();
                c.setTitle(k);
                c.setPrice(v);
                subscription.getCharges().add(c);
            });

            subscription.setQuoteReference("QT"+ StringUtils.right("000000000000"+quoteId, 12));
            quoteRef = subscription.getQuoteReference();
            subscriptionRepository.save(subscription);
        }
        return new QuoteResponse(quoteRef, quoteDate, quoteDate.plusDays(14) , product.getName(), quoteDetails.values().stream().mapToDouble(v ->v).sum(), quoteDetails, quoteRequest);
    }

    @Operation(summary = "Lire un devis", description = "Cette methode permet de lire un devis dont on connait l'identifiant ")
    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "Bearer Authentication")
    public Subscription getOne(@PathVariable(required = true) long id){
        return subscriptionRepository.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Souscription introuvable !"));
    }
}
