package br.com.biblioteca;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.com.biblioteca.dao.EmprestimoDAO;
import br.com.biblioteca.report.EmprestimoReportController;
import br.com.biblioteca.report.JasperMaker;

public class EmprestimoReportControllerTest {

	private EmprestimoReportController emprestimoReportController;
	
	@Mock
	private JasperMaker jasperMaker;
	@Mock
	private EmprestimoDAO emprestimoDAO;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		emprestimoReportController = new EmprestimoReportController(jasperMaker, emprestimoDAO);
	}
	
	
}
