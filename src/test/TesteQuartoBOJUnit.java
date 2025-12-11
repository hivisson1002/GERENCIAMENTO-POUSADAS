package test;

import bo.QuartoBO;
import dto.Quarto;
import dto.QuartoStandard;
import dto.QuartoDeluxe;
import dto.QuartoPresidencial;
import exception.DadosInvalidosException;
import exception.EntidadeNaoEncontradaException;
import exception.ValidacaoException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Testes unitários JUnit para a camada BO de Quarto
 * Testa validações e regras de negócio
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TesteQuartoBOJUnit {
    
    private static QuartoBO bo;
    private static final int ID_POUSADA_TESTE = 4; // Pousada existente no banco
    
    @BeforeAll
    public static void inicializar() {
        bo = new QuartoBO();
        System.out.println("=== INICIANDO TESTES JUNIT - QuartoBO ===\n");
    }
    
    @AfterAll
    public static void finalizar() {
        System.out.println("\n=== TESTES JUNIT FINALIZADOS ===");
    }
    
    @Test
    @Order(1)
    @DisplayName("1. Adicionar quarto Standard válido")
    public void testeAdicionarQuartoValido() {
        assertDoesNotThrow(() -> {
            Quarto q = new QuartoStandard(ID_POUSADA_TESTE, 
                "Quarto JUnit " + System.currentTimeMillis(), 2, 150);
            bo.adicionarQuarto(q);
        });
    }
    
    @Test
    @Order(2)
    @DisplayName("2. Validação - Nome vazio deve gerar exceção")
    public void testeNomeVazio() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            Quarto q = new QuartoStandard(ID_POUSADA_TESTE, "", 2, 150);
            bo.adicionarQuarto(q);
        });
        assertTrue(exception.getMessage().contains("Nome"));
    }
    
    @Test
    @Order(3)
    @DisplayName("3. Validação - Nome com menos de 3 caracteres")
    public void testeNomeCurto() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            Quarto q = new QuartoStandard(ID_POUSADA_TESTE, "AB", 2, 150);
            bo.adicionarQuarto(q);
        });
        assertTrue(exception.getMessage().contains("3 caracteres"));
    }
    
    @Test
    @Order(4)
    @DisplayName("4. Validação - Camas = 0 deve gerar exceção")
    public void testeCamasZero() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            Quarto q = new QuartoStandard(ID_POUSADA_TESTE, "Quarto Teste", 0, 150);
            bo.adicionarQuarto(q);
        });
        assertTrue(exception.getMessage().contains("1 e 10"));
    }
    
    @Test
    @Order(5)
    @DisplayName("5. Validação - Camas = 15 deve gerar exceção")
    public void testeCamasExcessivo() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            Quarto q = new QuartoStandard(ID_POUSADA_TESTE, "Quarto Teste", 15, 150);
            bo.adicionarQuarto(q);
        });
        assertTrue(exception.getMessage().contains("1 e 10"));
    }
    
    @Test
    @Order(6)
    @DisplayName("6. Validação - Valor R$ 30 (abaixo do mínimo)")
    public void testeValorAbaixoMinimo() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            Quarto q = new QuartoStandard(ID_POUSADA_TESTE, "Quarto Teste", 2, 30);
            bo.adicionarQuarto(q);
        });
        assertTrue(exception.getMessage().contains("50"));
    }
    
    @Test
    @Order(7)
    @DisplayName("7. Validação - Valor R$ 15.000 (acima do máximo)")
    public void testeValorExcessivo() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            Quarto q = new QuartoStandard(ID_POUSADA_TESTE, "Quarto Teste", 2, 15000);
            bo.adicionarQuarto(q);
        });
        assertTrue(exception.getMessage().contains("10.000"));
    }
    
    @Test
    @Order(8)
    @DisplayName("8. Validação - ID pousada = 0 deve gerar exceção")
    public void testeIdPousadaZero() {
        ValidacaoException exception = assertThrows(ValidacaoException.class, () -> {
            Quarto q = new QuartoStandard(0, "Quarto Teste", 2, 150);
            bo.adicionarQuarto(q);
        });
        assertTrue(exception.getMessage().contains("maior que zero"));
    }
    
    @Test
    @Order(9)
    @DisplayName("9. Validação - Pousada ID 99999 não existe")
    public void testePousadaInexistente() {
        EntidadeNaoEncontradaException exception = assertThrows(EntidadeNaoEncontradaException.class, () -> {
            Quarto q = new QuartoStandard(99999, "Quarto Teste", 2, 150);
            bo.adicionarQuarto(q);
        });
        assertTrue(exception.getMessage().contains("não encontrada"));
    }
    
    @Test
    @Order(10)
    @DisplayName("10. Listar quartos da pousada")
    public void testeListarQuartos() {
        assertDoesNotThrow(() -> {
            List<Quarto> quartos = bo.listarQuartosPorPousada(ID_POUSADA_TESTE);
            assertNotNull(quartos);
            System.out.println("   Total de quartos: " + quartos.size());
        });
    }
    
    @Test
    @Order(11)
    @DisplayName("11. Calcular valor total para 1 dia (sem desconto)")
    public void testeCalcularValorSemDesconto() {
        assertDoesNotThrow(() -> {
            Quarto q = new QuartoStandard(ID_POUSADA_TESTE, "Quarto Teste", 2, 100);
            double valor = bo.calcularValorTotal(q, 1);
            assertEquals(100.0, valor, 0.01);
        });
    }
    
    @Test
    @Order(12)
    @DisplayName("12. Calcular valor total para 5 dias (desconto 10%)")
    public void testeCalcularValorDesconto10() {
        assertDoesNotThrow(() -> {
            Quarto q = new QuartoStandard(ID_POUSADA_TESTE, "Quarto Teste", 2, 100);
            double valor = bo.calcularValorTotal(q, 5);
            double esperado = 100 * 5 * 0.90; // R$ 450
            assertEquals(esperado, valor, 0.01);
            System.out.println("   Valor com 10% desconto: R$ " + valor);
        });
    }
    
    @Test
    @Order(13)
    @DisplayName("13. Calcular valor total para 10 dias (desconto 15%)")
    public void testeCalcularValorDesconto15() {
        assertDoesNotThrow(() -> {
            Quarto q = new QuartoStandard(ID_POUSADA_TESTE, "Quarto Teste", 2, 100);
            double valor = bo.calcularValorTotal(q, 10);
            double esperado = 100 * 10 * 0.85; // R$ 850
            assertEquals(esperado, valor, 0.01);
            System.out.println("   Valor com 15% desconto: R$ " + valor);
        });
    }
    
    @Test
    @Order(14)
    @DisplayName("14. Calcular valor com adicional luxo - Quarto Deluxe")
    public void testeCalcularValorComAdicionalLuxo() {
        assertDoesNotThrow(() -> {
            Quarto q = new QuartoDeluxe(ID_POUSADA_TESTE, "Quarto Deluxe", 2, 200, true);
            double valor = bo.calcularValorComAdicionalLuxo(q, 1);
            // Valor base: R$ 200, Adicional Jacuzzi: R$ 100 = R$ 300
            assertEquals(300.0, valor, 0.01);
            System.out.println("   Valor com adicional: R$ " + valor);
        });
    }
    
    @Test
    @Order(15)
    @DisplayName("15. Contar quantidade de quartos")
    public void testeContarQuartos() {
        assertDoesNotThrow(() -> {
            int quantidade = bo.contarQuartosPorPousada(ID_POUSADA_TESTE);
            assertTrue(quantidade >= 0);
            System.out.println("   Quantidade de quartos: " + quantidade);
        });
    }
    
    @Test
    @Order(16)
    @DisplayName("16. Calcular capacidade total de hóspedes")
    public void testeCalcularCapacidadeTotal() {
        assertDoesNotThrow(() -> {
            int capacidade = bo.calcularCapacidadeTotal(ID_POUSADA_TESTE);
            assertTrue(capacidade >= 0);
            System.out.println("   Capacidade total: " + capacidade + " camas");
        });
    }
    
    @Test
    @Order(17)
    @DisplayName("17. Buscar quartos com valor máximo de R$ 200")
    public void testeBuscarPorValorMaximo() {
        assertDoesNotThrow(() -> {
            List<Quarto> quartos = bo.buscarQuartosPorValorMaximo(ID_POUSADA_TESTE, 200);
            assertNotNull(quartos);
            System.out.println("   Quartos encontrados: " + quartos.size());
        });
    }
    
    @Test
    @Order(18)
    @DisplayName("18. Listar apenas quartos de luxo (IQuartoLuxo)")
    public void testeListarQuartosLuxo() {
        assertDoesNotThrow(() -> {
            List<Quarto> quartosLuxo = bo.listarQuartosLuxo(ID_POUSADA_TESTE);
            assertNotNull(quartosLuxo);
            System.out.println("   Quartos de luxo: " + quartosLuxo.size());
        });
    }
}
