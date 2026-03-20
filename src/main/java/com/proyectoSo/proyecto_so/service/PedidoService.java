package com.proyectoSo.proyecto_so.service;

import com.proyectoSo.proyecto_so.dto.CrearPedidosRequest;
import com.proyectoSo.proyecto_so.dto.ItemPedidos;
import com.proyectoSo.proyecto_so.model.Pedido;
import com.proyectoSo.proyecto_so.model.PedidoDetalle;
import com.proyectoSo.proyecto_so.model.Producto;
import com.proyectoSo.proyecto_so.repository.PedidoRepository;
import com.proyectoSo.proyecto_so.repository.ProductoRespository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {
    private final PedidoRepository pedidoRepository;
    private final ProductoRespository productoRepository;
    private final EmailService emailService;
    private final FacturaService facturaService;

    public PedidoService(PedidoRepository pedidoRepository,
                         ProductoRespository productoRepository,
                         EmailService emailService,
                         FacturaService facturaService) {
        this.pedidoRepository = pedidoRepository;
        this.productoRepository = productoRepository;
        this.emailService = emailService;
        this.facturaService = facturaService;
    }

    public Pedido crearPedido(CrearPedidosRequest request){

        String usuario = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setEstado("CREADO");

        List<PedidoDetalle> detalles = new ArrayList<>();
        double total = 0;

        StringBuilder detalleCorreo = new StringBuilder();

        detalleCorreo.append("Detalle del pedido:\n\n");

        for(ItemPedidos item : request.getProductos()){

            Producto producto = productoRepository
                    .findById(item.getProductoId())
                    .orElseThrow();

            PedidoDetalle detalle = new PedidoDetalle();

            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecio(producto.getPrecio());

            double subtotal = producto.getPrecio() * item.getCantidad();

            detalle.setSubtotal(subtotal);
            detalle.setPedido(pedido);

            total += subtotal;

            detalles.add(detalle);

            detalleCorreo.append(producto.getNombre())
                    .append(" x")
                    .append(item.getCantidad())
                    .append(" - Q")
                    .append(subtotal)
                    .append("\n");
        }

        pedido.setDetalles(detalles);
        pedido.setTotal(total);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        String rutaFactura = facturaService.generarFactura(pedidoGuardado);
        detalleCorreo.append("\nTOTAL: Q").append(total);

        emailService.enviarCorreoConAdjunto(
                usuario,
                "Pedido confirmado #" + pedidoGuardado.getId(),
                detalleCorreo.toString(),
                rutaFactura
        );

        return pedidoGuardado;
    }

    public List<Pedido> misPedidos(){

        String usuario = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return pedidoRepository.findByUsuario(usuario);
    }

    public Pedido obtenerPedido(Long id){

        return pedidoRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
    }

    public void deletePedido(Long id){
        pedidoRepository.deleteById(id);
    }
}
