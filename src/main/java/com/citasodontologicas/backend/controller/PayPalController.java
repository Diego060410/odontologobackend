package com.citasodontologicas.backend.controller;

import com.citasodontologicas.backend.service.serviceImpl.PayPalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/paypal")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // Permite que React se conecte
public class PayPalController {

    private final PayPalService payPalService;

    // Cambiamos a capture/{orderId} para que coincida con tu fetch de React
    @PostMapping("/capture/{orderId}")
    public ResponseEntity<?> capturar(@PathVariable String orderId) {
        try {
            // Aquí capturamos la orden.
            // Nota: Si necesitas el idPago, deberías pasarlo en el body o como QueryParam,
            // pero para la captura de PayPal lo principal es el orderId.
            Object response = payPalService.capturarOrden(orderId);

            return ResponseEntity.ok(response); // Retorna el objeto JSON que React espera
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/crear/{idPago}")
    public ResponseEntity<?> crear(@PathVariable Integer idPago) {
        try {
            String approvalUrl = payPalService.crearOrden(idPago);
            return ResponseEntity.ok(Map.of("url", approvalUrl));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}