package com.citasodontologicas.backend.service.serviceImpl;

import com.citasodontologicas.backend.entity.Pago;
import com.citasodontologicas.backend.repository.PagoRepository;
import com.citasodontologicas.backend.service.PagoService;
import com.paypal.orders.*;
import com.paypal.core.PayPalHttpClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PayPalService {

    private final PayPalHttpClient client;
    private final PagoRepository pagoRepository;
    private final PagoService pagoService;

    public String crearOrden(Integer idPago) throws Exception {

        Pago pago = pagoRepository.findById(idPago).get();

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.checkoutPaymentIntent("CAPTURE");

        PurchaseUnitRequest purchaseUnit = new PurchaseUnitRequest()
                .amountWithBreakdown(new AmountWithBreakdown()
                        .currencyCode("USD")
                        .value(String.valueOf(pago.getMonto())));

        orderRequest.purchaseUnits(List.of(purchaseUnit));

        OrdersCreateRequest request = new OrdersCreateRequest();
        request.requestBody(orderRequest);

        var response = client.execute(request);

        for (LinkDescription link : response.result().links()) {

            if (link.rel().equals("approve")) {

                return link.href();

            }
        }

        throw new RuntimeException("No se encontró link de aprobación");
    }

    // Cambia la firma: quitamos idPago y devolvemos Order (de com.paypal.orders)
    public Order capturarOrden(String orderId) throws Exception {

        OrdersCaptureRequest request = new OrdersCaptureRequest(orderId);
        request.requestBody(new OrderRequest());

        // Ejecuta la captura en PayPal
        var response = client.execute(request);

        // Retornamos el resultado de PayPal (el objeto Order que tiene el ID, status, etc.)
        return response.result();
    }
}