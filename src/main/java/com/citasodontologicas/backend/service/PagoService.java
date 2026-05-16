package com.citasodontologicas.backend.service;

import com.citasodontologicas.backend.dto.request.PagoRequest;
import com.citasodontologicas.backend.dto.response.PagoResponse;
import com.citasodontologicas.backend.entity.Pago; // Importante para el nuevo método

import java.util.List;

public interface PagoService {

    PagoResponse crearPago(PagoRequest request);

    List<PagoResponse> listar();

    PagoResponse obtenerPorId(Integer id);

    PagoResponse actualizar(Integer id, PagoRequest request);

    void eliminar(Integer id);

    // 🔥 Método necesario para que PayPalService funcione
    void confirmarPago(Pago pago);

    PagoResponse marcarComoPagado(Integer id);

    PagoResponse pagarConYape(Integer id);

    PagoResponse pagarConPlin(Integer id);

    PagoResponse confirmarPagoPaypal(Integer id, String transaccionId);
}