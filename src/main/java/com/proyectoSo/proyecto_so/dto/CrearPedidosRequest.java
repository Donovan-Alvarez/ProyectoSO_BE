package com.proyectoSo.proyecto_so.dto;

import lombok.Data;

import java.util.List;

@Data
public class CrearPedidosRequest {
    private List<ItemPedidos> productos;
}

