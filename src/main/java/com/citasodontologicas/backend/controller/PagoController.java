package com.citasodontologicas.backend.controller;

import com.citasodontologicas.backend.dto.request.PagoRequest;
import com.citasodontologicas.backend.dto.response.PagoResponse;
import com.citasodontologicas.backend.service.PagoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;

    @PostMapping
    @Operation(summary = "Crear pago")
    public PagoResponse crear(@RequestBody PagoRequest request) {
        return pagoService.crearPago(request);
    }

    @GetMapping
    @Operation(summary = "Listar pagos")
    public List<PagoResponse> listar() {
        return pagoService.listar();
    }

    @GetMapping("/{id}")
    public PagoResponse obtener(@PathVariable Integer id) {
        return pagoService.obtenerPorId(id);
    }

    @PutMapping("/{id}")
    public PagoResponse actualizar(@PathVariable Integer id, @RequestBody PagoRequest request) {
        return pagoService.actualizar(id, request);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        pagoService.eliminar(id);
    }

    @PostMapping("/{id}/pagar")
    @Operation(summary = "Marcar como pagado (manual)")
    public PagoResponse pagar(@PathVariable Integer id) {
        return pagoService.marcarComoPagado(id);
    }
}