package br.emprestimo.testeUnitario;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.emprestimo.dadosDeTeste.ObtemLivro;
import br.emprestimo.modelo.Emprestimo;
import br.emprestimo.modelo.Livro;
import br.emprestimo.modelo.Usuario;
import br.emprestimo.servico.ServicoEmprestimo;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class UC01RegistraEmprestimoDeLivro {
	static private Livro livro;
	static private Usuario usuario;
	static private ServicoEmprestimo servico;
	static private Emprestimo emprestimo;
	static private ObtemLivro obtemLivro;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//cenario
		livro = ObtemLivro.comDadosValidos();
		usuario = new Usuario();
		usuario.setRa("11111");
		usuario.setNome("Jose da Silva");
		servico = new ServicoEmprestimo();
		emprestimo = new Emprestimo();
	}
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	@Test
	public void CT01UC01FB_registrar_emprestimo_com_sucesso() {
		assertNotNull(servico.empresta(livro, usuario));
	}
	@Test(expected=RuntimeException.class)
	public void CT02UC01FB_registrar_emprestimo_com_dados_invalidos() {
		assertNotNull(servico.empresta(null, usuario));
	}
	@Test
	public void CT03UC01FB_registrar_emprestimo_com_dados_invalidos(){
		try{
			servico.empresta(null, usuario);
			fail ("deveria lan�ar uma exce��o");
		}catch(RuntimeException e){
			assertEquals("Dados inv�lidos.", e.getMessage());
		}
	}
	@Test
	public void CT04UC01FB_registrar_emprestimo_com_sucesso_validacao_da_data() {
		//acao
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/YYYY");
		String dataEsperada = new DateTime().plusDays(8).toString(fmt);
		emprestimo = servico.empresta(livro, usuario);
		String dataObtida = emprestimo.getDataDevolucao();
		//verificacao
	    assertTrue(dataEsperada.equals(dataObtida));
	}
	@Test
	public void CT05UC01FB_registrar_emprestimo_com_data_invalida() {
		assertFalse(emprestimo.validaData("29-03-2000"));
	}
	@Test
	public void CT06UC01FB_registrar_emprestimo_com_data_invalida() {
		
		Emprestimo emprestimo2 = new Emprestimo();
		
		try {
			
			emprestimo2.setDataEmprestimo("30/02/2000");	
		} 
		catch (Exception e) {
			
			assertFalse(emprestimo.validaData("30/02/2000"));
		}
	}
	@Test
	public void CT07UC01FB_registrar_emprestimo_com_data_valida() {
		assertTrue(emprestimo.validaData("29/03/2000"));
	}
	/*@Test(expected=RuntimeException.class)
	public void CT09UC01_registrar_emprestimo_nulo() {
		
		emprestimo.setLivro(null);
	}*/
}
