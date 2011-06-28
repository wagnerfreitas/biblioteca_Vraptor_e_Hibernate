package com.br.biblioteca.report;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class JasperMaker {

	private static final Logger LOGGER = LoggerFactory.getLogger(JasperMaker.class);
	private final String jasperDir;
	private final String contextDir;

	public JasperMaker(ServletContext servletContext) {
		contextDir = servletContext.getRealPath("/");
		String temp = servletContext.getInitParameter("vraptor.jasperMaker");
		temp = temp == null ? "WEB-INF/jasper/" : temp.trim();
		if (!temp.endsWith("/"))
			temp = temp.concat("/");
		jasperDir = contextDir + temp;
	}

	/**
	 * Cria um PDF a partir da coleção de Beans <tt>dataSource</tt> enviada.
	 * 
	 * dois parametros são injetados por este método o "jasperPath" contendo o
	 * caminho para a paste dos arquivos jasper e o "contextPath" contendo o
	 * caminho para a raiz do aplicativo web.
	 * 
	 * @param jasperFile
	 *            o nome do arquivo .jasper, o diretório onde o .jasper deve
	 *            estar é definido no properties através do parametro
	 *            "jasper.dir".
	 * @param dataSource
	 *            a coleção de beans a ser usada no reporter.
	 * @param fileName
	 *            o nome do arquivo a ser gerado.
	 * @param doDownload
	 *            se o arquivo é para download, em caso de <tt>false</tt> o
	 *            arquivo poderá ser aberto no proprio browser.
	 * @return um {@link Download} a ser enviado como retorno.
	 */
	public Download makePdf(String jasperFile, Collection<?> dataSource,
			String fileName, boolean doDownload, HashMap<String, Object> parametros) {
		return makePdf(jasperFile, dataSource, fileName, doDownload,
				parametros);
	}

	/**
	 * Cria um PDF a partir da coleção de Beans <tt>dataSource</tt> enviada.
	 * 
	 * @param jasperFile
	 *            o nome do arquivo .jasper, o diretório onde o .jasper deve
	 *            estar é definido no properties através do parametro
	 *            "jasper.dir".
	 * @param dataSource
	 *            a coleção de beans a ser usada no reporter.
	 * @param fileName
	 *            o nome do arquivo a ser gerado.
	 * @param doDownload
	 *            se o arquivo é para download, em caso de <tt>false</tt> o
	 *            arquivo poderá ser aberto no proprio browser.
	 * @param parametros
	 *            os parametros a serem passados ao jasper reporter, dois
	 *            parametros são injetados por este método o "jasperPath"
	 *            contendo o caminho para a paste dos arquivos jasper e o
	 *            "contextPath" contendo o caminho para a raiz do aplicativo
	 *            web.
	 * @return um {@link Download} a ser enviado como retorno.
	 */
	public Download makePdf(String jasperFile, Collection<?> dataSource,String fileName, boolean doDownload, Map<String, Object> parametros) {
		jasperFile = jasperDir + jasperFile;
		
		try {
			JasperReport relatorio = JasperCompileManager.compileReport(jasperFile);  
			JasperPrint print = JasperFillManager.fillReport(relatorio, parametros, new JRBeanCollectionDataSource(dataSource));

			JRExporter exporter = new JRPdfExporter();

			ByteArrayOutputStream exported = new ByteArrayOutputStream();

			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, exported);
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);

			exporter.exportReport();

			byte[] content = exported.toByteArray();

			return new InputStreamDownload(new ByteArrayInputStream(content),
					"application/pdf", fileName, doDownload, content.length);

		} catch (Exception e) {
			LOGGER.error("PDF Exporter error", e);
			throw new RuntimeException(e);
		}

	}
}