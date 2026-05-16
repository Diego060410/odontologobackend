package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.entity.Pago;
import com.citasodontologicas.backend.repository.PagoRepository;
import com.citasodontologicas.backend.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class YapeService {

    private final PagoRepository pagoRepository;
    private final PagoService pagoService;

    public String generarQR(Integer idPago) {

        Pago pago = pagoRepository.findById(idPago).get();

        String data = "YAPE|" + pago.getMonto() + "|ID:" + idPago;

        return "https://api.qrserver.com/v1/create-qr-code/?size=250x250&data=" + data;
    }

    public Pago confirmarPago(Integer idPago) {

        Pago pago = pagoRepository.findById(idPago).get();

        pago.setMetodo("YAPE");
        pago.setTransaccionId("YAPE-" + System.currentTimeMillis());

        pagoService.confirmarPago(pago);

        return pago;
    }
}