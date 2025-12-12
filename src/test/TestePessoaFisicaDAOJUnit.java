package test;

import dao.PessoaFisicaDAO;
import dao.PessoaDAO;
import dto.PessoaFisica;
import exception.DadosInvalidosException;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários JUnit para PessoaFisicaDAO
 * Testa operações CRUD e validações para PessoaFisica
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestePessoaFisicaDAOJUnit {

    private static PessoaFisicaDAO dao;
    private static final String USUARIO_TESTE = "pf_test_001";

    @BeforeAll
    public static void inicializar() {
        dao = new PessoaFisicaDAO();
        System.out.println("=== INICIANDO TESTES JUNIT - PessoaFisicaDAO ===\n");
        
        // Limpar dados de teste anteriores se existirem
        try {
            if (PessoaDAO.existeUsuario(USUARIO_TESTE)) {
                PessoaFisicaDAO.deletarPorUsuario(USUARIO_TESTE);
                System.out.println("   Limpeza: usuário de teste removido");
            }
        } catch (Exception e) {
            System.out.println("   Aviso: erro na limpeza inicial - " + e.getMessage());
        }
    }

    @AfterAll
    public static void finalizar() {
        // Limpar após testes
        try {
            if (PessoaDAO.existeUsuario(USUARIO_TESTE)) {
                PessoaFisicaDAO.deletarPorUsuario(USUARIO_TESTE);
            }
        } catch (Exception e) {
            System.out.println("   Aviso: erro na limpeza final - " + e.getMessage());
        }
        System.out.println("\n=== TESTES JUNIT FINALIZADOS - PessoaFisicaDAO ===");
    }

    @Test
    @Order(1)
    @DisplayName("1. Deve cadastrar pessoa física válida com sucesso")
    public void testCadastrarPessoaFisicaValida() {
        System.out.println("Teste 1: Cadastrar pessoa física válida");
        
        assertDoesNotThrow(() -> {
            PessoaFisica pf = new PessoaFisica(USUARIO_TESTE, "Maria Santos", "87988887777", 123456789, "F");
            boolean resultado = PessoaFisicaDAO.cadastrarPessoaFisica(pf);
            
            assertTrue(resultado, "Cadastro deve retornar true");
            assertTrue(PessoaDAO.existeUsuario(USUARIO_TESTE), "Usuário deve existir após cadastro");
            System.out.println("   ✓ Pessoa física cadastrada com sucesso");
        });
    }

    @Test
    @Order(2)
    @DisplayName("2. Deve buscar pessoa física por usuário")
    public void testBuscarPorUsuario() {
        System.out.println("Teste 2: Buscar pessoa física por usuário");
        
        assertDoesNotThrow(() -> {
            PessoaFisica pf = PessoaFisicaDAO.buscarPorUsuario(USUARIO_TESTE);
            
            assertNotNull(pf, "Pessoa física não deve ser nula");
            assertEquals(USUARIO_TESTE, pf.getUsuario());
            assertEquals("Maria Santos", pf.getNome());
            assertEquals("87988887777", pf.getTelefone());
            assertEquals(123456789, pf.getCpf());
            assertEquals("F", pf.getSexo());
            System.out.println("   ✓ Pessoa física encontrada: " + pf);
        });
    }

    @Test
    @Order(3)
    @DisplayName("3. Deve listar todas as pessoas físicas")
    public void testListarTodas() throws Exception {
        System.out.println("Teste 3: Listar todas as pessoas físicas");
        
        var pessoasFisicas = dao.listarTodos();
        
        assertNotNull(pessoasFisicas, "Lista não deve ser nula");
        assertTrue(pessoasFisicas.size() > 0, "Deve existir ao menos uma pessoa física");
        
        boolean encontrouTeste = pessoasFisicas.stream()
            .anyMatch(pf -> USUARIO_TESTE.equals(pf.getUsuario()));
        assertTrue(encontrouTeste, "Deve encontrar o usuário de teste na listagem");
        
        System.out.println("   ✓ Total de pessoas físicas: " + pessoasFisicas.size());
    }

    @Test
    @Order(4)
    @DisplayName("4. Deve atualizar pessoa física existente")
    public void testAtualizarPessoaFisica() throws Exception {
        System.out.println("Teste 4: Atualizar pessoa física");
        
        PessoaFisica pf = PessoaFisicaDAO.buscarPorUsuario(USUARIO_TESTE);
        assertNotNull(pf, "Pessoa física deve existir antes da atualização");
        
        pf.setNome("Maria Santos Atualizada");
        pf.setTelefone("87977776666");
        pf.setCpf(987654321);
        pf.setSexo("F");
        
        dao.atualizar(pf);
        
        PessoaFisica pfAtualizada = PessoaFisicaDAO.buscarPorUsuario(USUARIO_TESTE);
        assertEquals("Maria Santos Atualizada", pfAtualizada.getNome());
        assertEquals("87977776666", pfAtualizada.getTelefone());
        assertEquals(987654321, pfAtualizada.getCpf());
        
        System.out.println("   ✓ Pessoa física atualizada com sucesso");
    }

    @Test
    @Order(5)
    @DisplayName("5. Não deve cadastrar pessoa física com usuário duplicado")
    public void testCadastrarUsuarioDuplicado() {
        System.out.println("Teste 5: Tentar cadastrar usuário duplicado");
        
        assertDoesNotThrow(() -> {
            PessoaFisica pfDuplicada = new PessoaFisica(USUARIO_TESTE, "Outro Nome", "87911112222", 111222333, "M");
            boolean resultado = PessoaFisicaDAO.cadastrarPessoaFisica(pfDuplicada);
            
            assertFalse(resultado, "Não deve permitir cadastro com usuário duplicado");
            System.out.println("   ✓ Validação de duplicidade funcionando");
        });
    }

    @Test
    @Order(6)
    @DisplayName("6. Deve lançar exceção ao cadastrar pessoa física nula")
    public void testCadastrarPessoaFisicaNula() {
        System.out.println("Teste 6: Tentar cadastrar pessoa física nula");
        
        assertThrows(DadosInvalidosException.class, () -> {
            PessoaFisicaDAO.cadastrarPessoaFisica(null);
        }, "Deve lançar DadosInvalidosException para pessoa física nula");
        
        System.out.println("   ✓ Exceção lançada corretamente");
    }

    @Test
    @Order(7)
    @DisplayName("7. Deve lançar exceção ao cadastrar com usuário nulo")
    public void testCadastrarUsuarioNulo() {
        System.out.println("Teste 7: Tentar cadastrar com usuário nulo");
        
        assertThrows(DadosInvalidosException.class, () -> {
            PessoaFisica pf = new PessoaFisica(null, "Nome Teste", "87900001111", 123456, "M");
            PessoaFisicaDAO.cadastrarPessoaFisica(pf);
        }, "Deve lançar DadosInvalidosException para usuário nulo");
        
        System.out.println("   ✓ Validação de usuário nulo funcionando");
    }

    @Test
    @Order(8)
    @DisplayName("8. Deve validar sexo corretamente")
    public void testValidarSexo() {
        System.out.println("Teste 8: Validar campo sexo");
        
        PessoaFisica pf1 = new PessoaFisica("user_temp1", "Nome1", "87900001111", 123, "M");
        assertEquals("M", pf1.getSexo());
        
        PessoaFisica pf2 = new PessoaFisica("user_temp2", "Nome2", "87900001112", 124, "F");
        assertEquals("F", pf2.getSexo());
        
        // Testar valor inválido - setSexo deve ignorar valores diferentes de M/F
        PessoaFisica pf3 = new PessoaFisica("user_temp3", "Nome3", "87900001113", 125, "F");
        pf3.setSexo("X");
        // Se setSexo rejeita, mantém o valor anterior (F)
        assertEquals("F", pf3.getSexo(), "Sexo inválido não deve alterar valor anterior");
        
        System.out.println("   ✓ Validação de sexo funcionando");
    }

    @Test
    @Order(9)
    @DisplayName("9. Deve deletar pessoa física existente")
    public void testDeletarPessoaFisica() {
        System.out.println("Teste 9: Deletar pessoa física");
        
        assertDoesNotThrow(() -> {
            assertTrue(PessoaDAO.existeUsuario(USUARIO_TESTE), "Pessoa física deve existir antes da deleção");
            
            PessoaFisicaDAO.deletarPorUsuario(USUARIO_TESTE);
            
            assertFalse(PessoaDAO.existeUsuario(USUARIO_TESTE), "Pessoa física não deve mais existir após deleção");
            assertNull(PessoaFisicaDAO.buscarPorUsuario(USUARIO_TESTE), "Busca deve retornar null após deleção");
            
            System.out.println("   ✓ Pessoa física deletada com sucesso");
        });
    }

    @Test
    @Order(10)
    @DisplayName("10. Deve retornar null ao buscar usuário inexistente")
    public void testBuscarUsuarioInexistente() {
        System.out.println("Teste 10: Buscar usuário inexistente");
        
        assertDoesNotThrow(() -> {
            PessoaFisica pf = PessoaFisicaDAO.buscarPorUsuario("usuario_nao_existe_xyz789");
            
            assertNull(pf, "Deve retornar null para usuário inexistente");
            System.out.println("   ✓ Retorno correto para busca inexistente");
        });
    }
}
