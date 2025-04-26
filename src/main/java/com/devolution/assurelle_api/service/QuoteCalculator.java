package com.devolution.assurelle_api.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


import com.devolution.assurelle_api.model.entity.Guarantee;
import com.devolution.assurelle_api.model.entity.Product;
import com.devolution.assurelle_api.model.record.QuoteRequest;


public class QuoteCalculator {

    public static Map<String, Double> calculate(Product p,QuoteRequest quoteRequest){
        Map<String, Double> charges = new HashMap<>();

        p.getGuarantees().stream().sorted(Comparator.comparingLong(Guarantee::getId)).forEach(g->{
            charges.put(g.getName() + " (" + g.getDescription() + ")" ,calculateCharge(g, quoteRequest));
        });
       
        
        return charges;
    }

    private static double calculateCharge(Guarantee g, QuoteRequest q){
        
        switch (g.getName()) {
            case "RC":
                if (q.vehicleFiscalPower() <=2) {
                return 37601;
                } else if (q.vehicleFiscalPower() >=3 && q.vehicleFiscalPower()<=6) {
                    return 45181;
                }else if (q.vehicleFiscalPower() >=7 && q.vehicleFiscalPower()<=10){
                    return 51078; 
                }else if (q.vehicleFiscalPower() >=11 && q.vehicleFiscalPower()<=14){
                    return 65677;
                }else if (q.vehicleFiscalPower() >=15 && q.vehicleFiscalPower()<=23){
                    return 86456;
                }else if (q.vehicleFiscalPower() >=24){
                    return 104143;
                }
                return 0;

            case "DOMMAGES":
                return Math.round(g.getRateValue()*q.vehicleOriginalCost()/100) ;
            case "COLLISION":
                return  Math.round(g.getRateValue()*q.vehicleOriginalCost()/100) ;
            case "PLAFONNEE":
                double assuredValue = q.vehicleMarketCost()/2;
                return Math.round(g.getRateValue()*assuredValue/100) ;
            case "VOL":
                return Math.round(g.getRateValue()*q.vehicleMarketCost()/100) ;
            case "INCENDIE":
                return Math.round(g.getRateValue()*q.vehicleMarketCost()/100) ;
            default:
                return 0;
        }
    }
}
