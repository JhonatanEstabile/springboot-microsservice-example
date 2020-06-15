package br.acme.ecommerce.controllers;

import br.acme.ecommerce.models.Sale;
import br.acme.ecommerce.services.CheckoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/checkout")
public class CheckoutController
{
    @Autowired
    private CheckoutService services;

    @PostMapping("/sale")
    public ResponseEntity<Boolean> sale(@RequestBody Sale sale)
    {
        services.saleProduct(sale);
        return ResponseEntity.ok().build();
    }
}