package com.concafras.gestao.util;

import java.io.File;
import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class XlsView implements View {

	private static final String CONTENT_TYPE = "application/vnd.ms-excel";
	private String JASPER_URL;
	private String FILE_NAME = "XLSFile";

	public XlsView(String jasperUrl) {
		this.JASPER_URL = jasperUrl + ".url";
	}

	@Override
	public String getContentType() {
		return CONTENT_TYPE;
	}

	@Override
	public void render(Map model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (model.get("FILE_NAME") != null) {
			this.FILE_NAME = model.get("FILE_NAME").toString();
		}

		ResourceBundle rb = ResourceBundle.getBundle("view");/*
															 * Se lee el archivo
															 * view.properties
															 */
		ReportesDAOJDBC reporte = (ReportesDAOJDBC) model
				.get("OBJETO_CONEXION");/* Se obtiene el objeto de conexion */
		Connection con = reporte.getConexion();/*
												 * Se genera la conexion a la
												 * base de datos
												 */

		String jasperFilePath = Util.getRealPath(request)
				+ rb.getString(JASPER_URL);/*
											 * Se obtiene la ruta fisica del
											 * archivo .jasper a ejectuar
											 */
		JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(new File(jasperFilePath));/*
													 * Se carga el reporte ya
													 * compilado
													 */
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
				model, con);/*
							 * Se llena el reporte con datos del modelo y con la
							 * conexion a la BD
							 */

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ FILE_NAME + ".xls\"");
		response.setHeader("Pragma", "No-cache");
		response.setDateHeader("Expires", 1);

		try {

			OutputStream out = response.getOutputStream();

			JRXlsExporter exporterXLS = new JRXlsExporter();
			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
					jasperPrint);
			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, out);
			exporterXLS.exportReport();

			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		reporte.closeConecction(con);/*
									 * Cierro la conexion a la base de datos
									 * para liberar el pool
									 */

	}
}