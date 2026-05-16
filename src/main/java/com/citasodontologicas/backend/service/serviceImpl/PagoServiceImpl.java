package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.dto.request.PagoRequest;
import com.citasodontologicas.backend.dto.response.PagoResponse;
import com.citasodontologicas.backend.entity.Cita;
import com.citasodontologicas.backend.entity.EstadoCita;
import com.citasodontologicas.backend.entity.Pago;
import com.citasodontologicas.backend.repository.CitaRepository;
import com.citasodontologicas.backend.repository.EstadoCitaRepository;
import com.citasodontologicas.backend.repository.PagoRepository;
import com.citasodontologicas.backend.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoServiceImpl implements PagoService {

    private final PagoRepository pagoRepository;
    private final CitaRepository citaRepository;
    private final EstadoCitaRepository estadoCitaRepository;

    @Override
    @Transactional
    public PagoResponse crearPago(PagoRequest request) {
        // 1. Buscar la cita
        Cita cita = citaRepository.findById(request.getIdCita())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        // 2. Construir el objeto Pago con los datos que vienen del Frontend
        Pago pago = Pago.builder()
                .cita(cita)
                .monto(request.getMonto())
                .metodo(request.getMetodo() != null ? request.getMetodo().toUpperCase() : "PAYPAL")
                .estado("PAGADO") // Como ya pasó por PayPal, lo creamos como PAGADO
                .fechaPago(LocalDateTime.now()) // 🔥 Usamos la hora del servidor
                .transaccionId(request.getTransaccionId()) // ✅ Guardamos el ID de PayPal
                .build();

        // 3. Guardar el pago
        Pago pagoGuardado = pagoRepository.save(pago);

        // 4. Actualizar el estado de la cita automáticamente a CONFIRMADA
        EstadoCita estadoConfirmado = estadoCitaRepository
                .findByNombreEstado("CONFIRMADA")
                .orElseThrow(() -> new RuntimeException("Error: El estado 'CONFIRMADA' no existe."));

        cita.setEstadoCita(estadoConfirmado);
        citaRepository.save(cita);

        return mapToResponse(pagoGuardado);
    }

    @Override
    public List<PagoResponse> listar() {
        return pagoRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PagoResponse obtenerPorId(Integer id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        return mapToResponse(pago);
    }

    @Override
    @Transactional
    public PagoResponse actualizar(Integer id, PagoRequest request) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        pago.setMonto(request.getMonto());
        pago.setMetodo(request.getMetodo().toUpperCase());

        return mapToResponse(pagoRepository.save(pago));
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
        pagoRepository.delete(pago);
    }

    // 🔥 IMPLEMENTACIÓN DEL MÉTODO QUE FALTABA
    @Override
    @Transactional
    public void confirmarPago(Pago pago) {
        // Actualizar datos del pago
        pago.setEstado("PAGADO");
        pago.setFechaPago(LocalDateTime.now());
        pagoRepository.save(pago);

        // Actualizar la cita asociada a "CONFIRMADA"
        Cita cita = pago.getCita();
        EstadoCita estadoConfirmado = estadoCitaRepository
                .findByNombreEstado("CONFIRMADA")
                .orElseThrow(() -> new RuntimeException("Error: El estado 'CONFIRMADA' no existe en la BD."));

        cita.setEstadoCita(estadoConfirmado);
        citaRepository.save(cita);
    }

    @Override
    @Transactional
    public PagoResponse marcarComoPagado(Integer id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        pago.setTransaccionId("SIM-" + System.currentTimeMillis());
        confirmarPago(pago); // Reutilizamos el método de arriba

        return mapToResponse(pago);
    }

    @Override
    @Transactional
    public PagoResponse pagarConYape(Integer id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        pago.setMetodo("YAPE");
        pago.setTransaccionId("YAPE-" + System.currentTimeMillis());
        confirmarPago(pago);

        return mapToResponse(pago);
    }

    @Override
    @Transactional
    public PagoResponse pagarConPlin(Integer id) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        pago.setMetodo("PLIN");
        pago.setTransaccionId("PLIN-" + System.currentTimeMillis());
        confirmarPago(pago);

        return mapToResponse(pago);
    }

    @Override
    @Transactional
    public PagoResponse confirmarPagoPaypal(Integer id, String transaccionId) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        pago.setMetodo("PAYPAL");
        pago.setTransaccionId(transaccionId);
        confirmarPago(pago);

        return mapToResponse(pago);
    }

    private PagoResponse mapToResponse(Pago pago) {
        return PagoResponse.builder()
                .idPago(pago.getIdPago())
                .idCita(pago.getCita().getIdCita())
                .monto(pago.getMonto())
                .metodo(pago.getMetodo())
                .estado(pago.getEstado())
                .fechaPago(pago.getFechaPago())
                .transaccionId(pago.getTransaccionId())
                .build();
    }
}