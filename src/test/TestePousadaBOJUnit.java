package test;

import bo.PousadaBO;
import dto.Pousada;
import exception.DadosInvalidosException;
import exception.EntidadeNaoEncontradaException;
import exception.ValidacaoException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Testes unitários JUnit para a camada BO de Pousada
 * Testa validações e regras de negócio
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestePousadaBOJUnit {
    
    private static PousadaBO bo;
    
    @BeforeAll
    public static void inicializar() {
        bo = new PousadaBO();
        System.out.println("=== INICIANDO TESTES JUNIT - PousadaBO ===\n");
    }
    
    @AfterAll
    public static void finalizar() {
        System.out.println("\n=== TESTES JUNIT FINALIZADOS ===");
    }
    
    @Test
    @Order(1)
    @DisplayName("1. Adicionar pousada com dados válidos")
    public void testeAdicionarPousadaValida() {
        assertDoesNotThrow(() -> {
            Pousada p = criarPousadaValida();
            p.setNome("Pousada JUnit " + System.currentTimeMillis());
            bo.adicionarPousada(p);
        });
    }
    
    @Test
    @Order(2)
    @DisplayName("2. Validação - Nome vazio deve gerar exceção")
    public void testeNomeVazio() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            Pousada p = criarPousadaValida();
            p.setNome("");
            bo.adicionarPousada(p);
        });
        assertTrue(exception.getMessage().contains("Nome"));
    }
    
    @Test
    @Order(3)
    @DisplayName("3. Validação - Nome com menos de 3 caracteres")
    public void testeNomeCurto() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            Pousada p = criarPousadaValida();
            p.setNome("AB");
            bo.adicionarPousada(p);
        });
        assertTrue(exception.getMessage().contains("3 caracteres"));
    }
    
    @Test
    @Order(4)
    @DisplayName("4. Validação - Estrelas = 0 deve gerar exceção")
    public void testeEstrelasZero() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            Pousada p = criarPousadaValida();
            p.setEstrelas(0);
            bo.adicionarPousada(p);
        });
        assertTrue(exception.getMessage().contains("1 e 5"));
    }
    
    @Test
    @Order(5)
    @DisplayName("5. Validação - Estrelas = 6 deve gerar exceção")
    public void testeEstrelasSeis() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            Pousada p = criarPousadaValida();
            p.setEstrelas(6);
            bo.adicionarPousada(p);
        });
        assertTrue(exception.getMessage().contains("1 e 5"));
    }
    
    @Test
    @Order(6)
    @DisplayName("6. Validação - Telefone vazio deve gerar exceção")
    public void testeTelefoneVazio() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            Pousada p = criarPousadaValida();
            p.setTelefone("");
            bo.adicionarPousada(p);
        });
        assertTrue(exception.getMessage().contains("Telefone"));
    }
    
    @Test
    @Order(7)
    @DisplayName("7. Validação - Telefone inválido (menos de 8 dígitos)")
    public void testeTelefoneInvalido() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            Pousada p = criarPousadaValida();
            p.setTelefone("123456");
            bo.adicionarPousada(p);
        });
        assertTrue(exception.getMessage().contains("Telefone inválido"));
    }
    
    @Test
    @Order(8)
    @DisplayName("8. Validação - Cidade vazia deve gerar exceção")
    public void testeCidadeVazia() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            Pousada p = criarPousadaValida();
            p.setCidade("");
            bo.adicionarPousada(p);
        });
        assertTrue(exception.getMessage().contains("Cidade"));
    }
    
    @Test
    @Order(9)
    @DisplayName("9. Validação - Estado vazio deve gerar exceção")
    public void testeEstadoVazio() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            Pousada p = criarPousadaValida();
            p.setEstado("");
            bo.adicionarPousada(p);
        });
        assertTrue(exception.getMessage().contains("Estado"));
    }
    
    @Test
    @Order(10)
    @DisplayName("10. Validação - Estado 'ALAGOAS' ao invés de 'AL'")
    public void testeEstadoFormatoInvalido() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            Pousada p = criarPousadaValida();
            p.setEstado("ALAGOAS");
            bo.adicionarPousada(p);
        });
        assertTrue(exception.getMessage().contains("sigla de 2 letras"));
    }
    
    @Test
    @Order(11)
    @DisplayName("11. Listar todas as pousadas")
    public void testeListarTodas() {
        assertDoesNotThrow(() -> {
            List<Pousada> pousadas = bo.listarTodas();
            assertNotNull(pousadas);
            System.out.println("   Total de pousadas: " + pousadas.size());
        });
    }
    
    @Test
    @Order(12)
    @DisplayName("12. Validação - Buscar pousada com ID = 0")
    public void testeBuscarIdZero() {
        DadosInvalidosException exception = assertThrows(DadosInvalidosException.class, () -> {
            bo.obterPousadaPorId(0);
        });
        assertTrue(exception.getMessage().contains("maior que zero"));
    }
    
    @Test
    @Order(13)
    @DisplayName("13. Validação - Buscar pousada com ID negativo")
    public void testeBuscarIdNegativo() {
        DadosInvalidosException exception = assertThrows(DadosInvalidosException.class, () -> {
            bo.obterPousadaPorId(-5);
        });
        assertTrue(exception.getMessage().contains("maior que zero"));
    }
    
    @Test
    @Order(14)
    @DisplayName("14. Calcular média de estrelas das pousadas")
    public void testeCalcularMediaEstrelas() {
        assertDoesNotThrow(() -> {
            double media = bo.calcularMediaEstrelas();
            assertTrue(media >= 0.0 && media <= 5.0);
            System.out.println("   Média de estrelas: " + String.format("%.2f", media));
        });
    }
    
    @Test
    @Order(15)
    @DisplayName("15. Buscar pousadas com 4 estrelas")
    public void testeBuscarPorEstrelas() {
        assertDoesNotThrow(() -> {
            List<Pousada> pousadas = bo.buscarPorEstrelas(4);
            assertNotNull(pousadas);
            System.out.println("   Pousadas 4 estrelas: " + pousadas.size());
        });
    }
    
    /**
     * Cria uma pousada com dados válidos para testes
     */
    private static Pousada criarPousadaValida() {
        Pousada p = new Pousada();
        p.setNome("Pousada Teste Válida");
        p.setEndereco("Rua Teste, 123");
        p.setBairro("Centro");
        p.setCidade("Maceió");
        p.setEstado("AL");
        p.setTelefone("82 3333-4444");
        p.setEstrelas(4);
        p.setObservacao("Pousada para testes");
        p.setSite("http://www.teste.com.br");
        p.setStatus("S");
        return p;
    }
}
