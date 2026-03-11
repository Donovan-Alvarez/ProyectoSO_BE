package com.proyectoSo.proyecto_so.controller;

import com.proyectoSo.proyecto_so.dto.CrearPedidosRequest;
import com.proyectoSo.proyecto_so.model.Pedido;
import com.proyectoSo.proyecto_so.service.PedidoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
@CrossOrigin
public class PedidosController {
    private final PedidoService pedidoService;

    public PedidosController(PedidoService pedidoService){
        this.pedidoService = pedidoService;
    }

    @PostMapping
    public Pedido crearPedido(@RequestBody CrearPedidosRequest request){
        return pedidoService.crearPedido(request);
    }

    @GetMapping("/mis-pedidos")
    public List<Pedido> misPedidos(){
        return pedidoService.misPedidos();
    }

    @GetMapping("/{id}")
    public Pedido obtenerPedido(@PathVariable Long id){
        return pedidoService.obtenerPedido(id);
    }

    @DeleteMapping("/{id}")
    public void deletePedido(@PathVariable Long id){
        pedidoService.deletePedido(id);
    }

}
