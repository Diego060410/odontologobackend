package com.citasodontologicas.backend.repository;

import com.citasodontologicas.backend.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Integer> {
}
