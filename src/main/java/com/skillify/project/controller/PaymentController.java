package com.skillify.project.controller;

import com.skillify.project.service.PaymentService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/create-checkout-session")
    public String createCheckoutSession(@RequestParam String courseName, @RequestParam Long amount) {
        try {
            return paymentService.createCheckoutSession(courseName, Math.toIntExact(amount));
        } catch (Exception e) {
            return "Error creating checkout session: " + e.getMessage();
        }
    }
}
