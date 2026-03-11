package com.proyectoSo.proyecto_so.repository;

import com.proyectoSo.proyecto_so.model.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {

}
