package com.proyectoSo.proyecto_so.service;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.proyectoSo.proyecto_so.model.Pedido;
import com.proyectoSo.proyecto_so.model.PedidoDetalle;
import org.springframework.stereotype.Service;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Service
public class FacturaService {

    public String generarFactura(Pedido pedido) {

        try {

            String ruta = "/srv/facturas/factura_" + pedido.getId() + ".pdf";

            PdfWriter writer = new PdfWriter(ruta);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("FACTURA"));
            document.add(new Paragraph("Pedido #: " + pedido.getId()));
            document.add(new Paragraph("Cliente: " + pedido.getUsuario()));
            document.add(new Paragraph(" "));

            double total = 0;

            for(PedidoDetalle d : pedido.getDetalles()){

                String linea =
                        d.getProducto().getNombre()
                                + " x" + d.getCantidad()
                                + " - Q" + d.getSubtotal();

                document.add(new Paragraph(linea));

                total += d.getSubtotal();
            }

            document.add(new Paragraph(" "));
            document.add(new Paragraph("TOTAL: Q" + total));

            document.close();

            return ruta;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
