package com.devolution.assurelle_api.controller.api.v1;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.devolution.assurelle_api.model.entity.Subscriber;
import com.devolution.assurelle_api.model.entity.Subscription;
import com.devolution.assurelle_api.model.record.SubscriptionRequest;
import com.devolution.assurelle_api.repository.SubscriptionRepository;
import com.devolution.assurelle_api.service.PdfRenderer;
import com.devolution.assurelle_api.service.QRcodeRenderer;
import com.devolution.assurelle_api.service.ViewRenderer;
import com.google.zxing.WriterException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api/v1/subscriptions")
@Tag(name = "Souscriptions", description = "Interfaces de souscriptions")
@RestController
public class SubscriptionController {
    
    @Autowired
    private SubscriptionRepository repos;

    @Operation(summary = "Liste des souscriptions", description = "Permet d'obtenir la listee de toutes les souscriptions")
    @GetMapping("")
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<Subscription> all(){
        return repos.subscriptionsOnly();
    }

    @Operation(summary = "Souscription", description = "Souscription a partir dun devis (simulation) préalablement calculé et enregistré. le chmap - quoteId- represente ID de la simulation")
    @PostMapping("")
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "Bearer Authentication")
    public String create(@RequestBody SubscriptionRequest requestBody){
        
        Subscription subscription = repos.findById(requestBody.quoteId()).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Devis non trouvé !"));

        Subscriber subscriber = new Subscriber();
        subscriber.setLastName(requestBody.subscriberLastName());
        subscriber.setFirstName(requestBody.subscriberFirstName());
        subscriber.setAddress(requestBody.subscriberAddress());
        subscriber.setCity(requestBody.subscriberCity());
        subscriber.setIdCardNumber(requestBody.subscriberIdCardNumber());
        subscriber.setPhoneNumber(requestBody.subscriberPhone());
        subscription.setSubscriber(subscriber);

        subscription.getVehicle().setColor(requestBody.vehicleColor());
        subscription.getVehicle().setDoorsCount(requestBody.vehicleDoorsCount());
        subscription.getVehicle().setSeatsCount(requestBody.vehicleSeatsCount());
        subscription.getVehicle().setRegistryNumber(requestBody.vehicleRegNumber());
        subscription.getVehicle().setServiceDate(requestBody.vehicleDate());
        subscription.getVehicle().setCategoryId(requestBody.vehicleCategoryId());

        subscription.setStatus(1);
        subscription.setSubscriptDate(LocalDateTime.now());
        repos.save(subscription);
        return "saved";
    }

    @Operation(summary = "Détails d'une souscription", description = "Affiche les détails une sousscription")
    @GetMapping("/{id}")
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "Bearer Authentication")
    public Subscription read(@PathVariable(required = true) long id){
        return repos.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Souscription introuvable !"));
    }

    @GetMapping("/{id}/attestation")
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<byte[]> pdf(@PathVariable(required = true) long id) throws WriterException, IOException{
        
        Subscription subscription = repos.findById(id).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Souscription introuvable !"));
        String url = "https://devolution.africa";
        byte[] qrCodeImage = QRcodeRenderer.render(url, 50, 50);
        String qrcode = Base64.getEncoder().encodeToString(qrCodeImage);

        Map<String, Object> templateData = new HashMap<>();
        templateData.put("subscription", subscription);
        templateData.put("qrcode", qrcode);

        String htm = ViewRenderer.render("templates/demo", templateData);
        byte[] pdf = PdfRenderer.render(htm);

        HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attestation"+ subscription.getQuoteReference()+".pdf");
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

        return ResponseEntity.ok()
				.headers(header)
				.contentType(MediaType.APPLICATION_PDF)
				.body(pdf);
    }

    @Operation(summary = "Souscriptions selon le statut", description = "Affiche les souscriptions selon le code de statut : 0 = Devis, 1= Souscription ")
    @GetMapping("/status/{id}")
    @Secured("ROLE_USER")
    @SecurityRequirement(name = "Bearer Authentication")
    public List<Subscription> getByStatus(@PathVariable(required = true) int id){
        return repos.findByStatus(id);
    }

    

}
