package test;

import dao.ReservaDAO;
import dto.Reserva;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Testes unitários JUnit para ReservaDAO
 * Testa operações CRUD e métodos de consulta
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TesteReservaDAO {

    private static ReservaDAO dao;
    private static int reservaIdCriada = -1;

    @BeforeAll
    public static void inicializar() {
        dao = new ReservaDAO();
        System.out.println("=== INICIANDO TESTES JUNIT - ReservaDAO ===\n");
    }

    @AfterAll
    public static void finalizar() {
        System.out.println("\n=== TESTES JUNIT FINALIZADOS ===");
    }

    @Test
    @Order(1)
    @DisplayName("1. Adicionar reserva válida")
    public void testeAdicionarReservaValida() {
        assertDoesNotThrow(() -> {
            LocalDate entrada = LocalDate.now().plusDays(5);
            LocalDate saida = entrada.plusDays(3);
            Reserva r = new Reserva(0, 1, 1, "fer", entrada, saida, "S");
            dao.adicionar(r);
            
            assertTrue(r.getId() > 0, "Reserva deve ter ID gerado após inserção");
            reservaIdCriada = r.getId();
            System.out.println("   Reserva criada com ID: " + reservaIdCriada);
        });
    }

    @Test
    @Order(2)
    @DisplayName("2. Obter reserva por ID")
    public void testeObterPorId() {
        assertDoesNotThrow(() -> {
            assertNotEquals(-1, reservaIdCriada, "Deve existir uma reserva criada");
            Reserva r = dao.obterPorId(reservaIdCriada);
            assertNotNull(r, "Reserva não deve ser nula");
            assertEquals(reservaIdCriada, r.getId());
            System.out.println("   Reserva encontrada: " + r);
        });
    }

    @Test
    @Order(3)
    @DisplayName("3. Listar todas as reservas")
    public void testeListarTodas() {
        assertDoesNotThrow(() -> {
            List<Reserva> reservas = dao.listarTodos();
            assertNotNull(reservas);
            assertTrue(reservas.size() > 0, "Deve existir ao menos uma reserva");
            System.out.println("   Total de reservas: " + reservas.size());
        });
    }

    @Test
    @Order(4)
    @DisplayName("4. Listar reservas por pousada")
    public void testeListarPorPousada() {
        assertDoesNotThrow(() -> {
            List<Reserva> reservas = dao.listarPorPousada(1);
            assertNotNull(reservas);
            System.out.println("   Reservas da pousada 1: " + reservas.size());
        });
    }

    @Test
    @Order(5)
    @DisplayName("5. Listar reservas por quarto")
    public void testeListarPorQuarto() {
        assertDoesNotThrow(() -> {
            List<Reserva> reservas = dao.listarPorQuarto(1);
            assertNotNull(reservas);
            System.out.println("   Reservas do quarto 1: " + reservas.size());
        });
    }

    @Test
    @Order(6)
    @DisplayName("6. Verificar disponibilidade de quarto (período disponível)")
    public void testeVerificarDisponibilidadeLivre() {
        assertDoesNotThrow(() -> {
            LocalDate entrada = LocalDate.now().plusDays(30);
            LocalDate saida = entrada.plusDays(2);
            boolean disponivel = dao.verificarDisponibilidade(2, entrada, saida);
            System.out.println("   Quarto 2 disponível em " + entrada + " a " + saida + ": " + disponivel);
        });
    }

    @Test
    @Order(7)
    @DisplayName("7. Atualizar reserva")
    public void testeAtualizarReserva() {
        assertDoesNotThrow(() -> {
            assertNotEquals(-1, reservaIdCriada, "Deve existir uma reserva criada");
            Reserva r = dao.obterPorId(reservaIdCriada);
            assertNotNull(r);
            
            r.setStatus("N"); // Cancelar reserva
            dao.atualizar(r);
            
            Reserva rAtualizada = dao.obterPorId(reservaIdCriada);
            assertEquals("N", rAtualizada.getStatus());
            System.out.println("   Reserva " + reservaIdCriada + " atualizada para status: " + rAtualizada.getStatus());
        });
    }

    @Test
    @Order(8)
    @DisplayName("8. Deletar reserva")
    public void testeDeletarReserva() {
        assertDoesNotThrow(() -> {
            assertNotEquals(-1, reservaIdCriada, "Deve existir uma reserva criada");
            dao.deletar(reservaIdCriada);
            
            Reserva rDeletada = dao.obterPorId(reservaIdCriada);
            assertNull(rDeletada, "Reserva não deve existir após deleção");
            System.out.println("   Reserva " + reservaIdCriada + " deletada com sucesso");
        });
    }
}
