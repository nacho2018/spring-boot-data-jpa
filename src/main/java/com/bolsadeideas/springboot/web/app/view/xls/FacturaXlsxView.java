package com.bolsadeideas.springboot.web.app.view.xls;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.bolsadeideas.springboot.web.app.models.entities.Factura;
import com.bolsadeideas.springboot.web.app.models.entities.ItemFactura;

@Component("factura/ver.xlsx")
public class FacturaXlsxView  extends AbstractXlsxView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Factura factura = (Factura)model.get("factura");
		Sheet sheet = workbook.createSheet();
		
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue("Datos del cliente");
		
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue("Nombre: " + factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue("Email: " + factura.getCliente().getEmail());
		
		row = sheet.createRow(3);
		cell = row.createCell(0);
		cell.setCellValue("");
		
		row = sheet.createRow(4);
		cell = row.createCell(0);
		cell.setCellValue("Datos de la Factura");
		
		row = sheet.createRow(5);
		cell = row.createCell(0);
		cell.setCellValue("Descripción: " + factura.getDescripcion());
		
		row = sheet.createRow(6);
		cell = row.createCell(0);
		cell.setCellValue("Observaciones: " + factura.getObservacion());
		
		row = sheet.createRow(7);
		cell = row.createCell(0);
		cell.setCellValue("Lista de Items");
		
		row = sheet.createRow(8);
		cell = row.createCell(0);
		cell.setCellValue("");
		
		int i = 9;
		for (ItemFactura item : factura.getItems()) {
			row = sheet.createRow(i);
			cell = row.createCell(0);
			cell.setCellValue(item.getProducto().toString() + " cantidad " + item.getCantidad());
			i++;
		}
		
		
		
	}

}
