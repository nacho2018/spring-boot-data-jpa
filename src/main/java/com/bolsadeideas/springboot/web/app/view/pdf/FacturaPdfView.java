package com.bolsadeideas.springboot.web.app.view.pdf;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.bolsadeideas.springboot.web.app.models.entities.Cliente;
import com.bolsadeideas.springboot.web.app.models.entities.Factura;
import com.bolsadeideas.springboot.web.app.models.entities.ItemFactura;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Component("factura/ver")
public class FacturaPdfView extends AbstractPdfView{

	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
	@Override
	protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
			Factura factura = (Factura)model.get("factura");
			
			if (factura != null) {
				Cliente cliente = factura.getCliente();
				if (cliente != null) {
					try {
						
						
						PdfWriter.getInstance(document, new FileOutputStream("Factura.pdf"));
						document.open();
						Paragraph p0 = new Paragraph(new Chunk((String)model.get("titulo"), FontFactory.getFont(FontFactory.HELVETICA, 18,Color.GREEN)));
						Paragraph p1 = new Paragraph(new Chunk("Datos del Cliente", FontFactory.getFont(FontFactory.HELVETICA, 16,Color.BLUE)));
						Paragraph p2 = new Paragraph(new Chunk("Nombre  ".concat(cliente.getNombre().concat(" ").concat(cliente.getApellido())), FontFactory.getFont(FontFactory.HELVETICA, 14,Color.BLACK)));
						Paragraph p3 = new Paragraph(new Chunk("Email  ".concat(cliente.getEmail()), FontFactory.getFont(FontFactory.HELVETICA, 14,Color.BLACK)));
						
						Paragraph p4 = new Paragraph(new Chunk("Datos de la Factura", FontFactory.getFont(FontFactory.HELVETICA, 16,Color.BLUE)));
						Paragraph p5 = new Paragraph(new Chunk("Id  ".concat(factura.getId().toString()), FontFactory.getFont(FontFactory.HELVETICA, 14,Color.BLACK)));
						Paragraph p6 = new Paragraph(new Chunk("Descripción  ".concat(factura.getDescripcion()), FontFactory.getFont(FontFactory.HELVETICA, 14,Color.BLACK)));
						Paragraph p7 = new Paragraph(new Chunk("Observaciones  ".concat(StringUtils.isEmpty(factura.getObservacion()) ? "" : factura.getObservacion()), FontFactory.getFont(FontFactory.HELVETICA, 14,Color.BLACK)));
						Paragraph p8 = new Paragraph(new Chunk("Total  ".concat(factura.calcularTotal().toString()), FontFactory.getFont(FontFactory.HELVETICA, 14,Color.BLUE)));
						Paragraph p9 = new Paragraph(new Chunk("Fecha de emisión  ".concat(sdf.format(factura.getCreateAt())), FontFactory.getFont(FontFactory.HELVETICA, 14,Color.BLUE)));
						
						Paragraph p10 = new Paragraph(new Chunk("Lista de Items", FontFactory.getFont(FontFactory.HELVETICA, 16,Color.BLUE)));
						
						document.add(p0);
						document.add(p1);
						document.add(p2);
						document.add(p3);
						document.add(p4);
						document.add(p5);
						document.add(p6);
						document.add(p7);
						document.add(p8);
						document.add(p9);
						document.add(p10);
						
						for (ItemFactura item : factura.getItems()) {
							StringBuilder sb = new StringBuilder("");
							sb.append(item.getProducto().getNombre())
							.append(" ")
							.append("$")
							.append(item.getProducto().getPrecio().toString())
							.append(" ")
							.append(item.getCantidad())
							.append(" unidades");
							Paragraph paragraph = new Paragraph(new Chunk(sb.toString(), FontFactory.getFont(FontFactory.HELVETICA, 14,Color.BLACK)));
							
							document.add(paragraph);
						}
						
					} catch (DocumentException de) {
			            System.err.println(de.getMessage());
			        }
					finally{
						document.close();
					}
				}
				
			}
			
			
		
	}

}
