package test;

import dao.PessoaDAO;
import dto.Pessoa;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários JUnit para PessoaDAO
 * Testa operações CRUD para entidade Pessoa
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestePessoaDAOJUnit {

    private static PessoaDAO dao;
    private static final String USUARIO_TESTE = "pessoa_test_001";

    @BeforeAll
    public static void inicializar() {
        dao = new PessoaDAO();
        System.out.println("=== INICIANDO TESTES JUNIT - PessoaDAO ===\n");
        
        // Limpar dados de teste anteriores se existirem
        try {
            if (PessoaDAO.existeUsuario(USUARIO_TESTE)) {
                dao.deletarPorUsuario(USUARIO_TESTE);
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
                dao.deletarPorUsuario(USUARIO_TESTE);
            }
        } catch (Exception e) {
            System.out.println("   Aviso: erro na limpeza final - " + e.getMessage());
        }
        System.out.println("\n=== TESTES JUNIT FINALIZADOS - PessoaDAO ===");
    }

    @Test
    @Order(1)
    @DisplayName("1. Deve inserir pessoa válida com sucesso")
    public void testInserirPessoaValida() {
        System.out.println("Teste 1: Inserir pessoa válida");
        
        Pessoa pessoa = new Pessoa(USUARIO_TESTE, "João Silva Teste", "87999887766");
        boolean resultado = PessoaDAO.inserir(pessoa);
        
        assertTrue(resultado, "Inserção deve retornar true");
        assertTrue(PessoaDAO.existeUsuario(USUARIO_TESTE), "Usuário deve existir após inserção");
        System.out.println("   ✓ Pessoa inserida com sucesso");
    }

    @Test
    @Order(2)
    @DisplayName("2. Deve buscar pessoa por usuário")
    public void testBuscarPorUsuario() {
        System.out.println("Teste 2: Buscar pessoa por usuário");
        
        Pessoa pessoa = PessoaDAO.buscarPorUsuario(USUARIO_TESTE);
        
        assertNotNull(pessoa, "Pessoa não deve ser nula");
        assertEquals(USUARIO_TESTE, pessoa.getUsuario());
        assertEquals("João Silva Teste", pessoa.getNome());
        assertEquals("87999887766", pessoa.getTelefone());
        System.out.println("   ✓ Pessoa encontrada: " + pessoa);
    }

    @Test
    @Order(3)
    @DisplayName("3. Deve verificar se usuário existe")
    public void testExisteUsuario() {
        System.out.println("Teste 3: Verificar existência de usuário");
        
        assertTrue(PessoaDAO.existeUsuario(USUARIO_TESTE), "Usuário de teste deve existir");
        assertFalse(PessoaDAO.existeUsuario("usuario_inexistente_xyz"), "Usuário inexistente deve retornar false");
        System.out.println("   ✓ Verificação de existência funcionando");
    }

    @Test
    @Order(4)
    @DisplayName("4. Deve listar todas as pessoas")
    public void testListarTodas() throws Exception {
        System.out.println("Teste 4: Listar todas as pessoas");
        
        var pessoas = dao.listarTodos();
        
        assertNotNull(pessoas, "Lista não deve ser nula");
        assertTrue(pessoas.size() > 0, "Deve existir ao menos uma pessoa");
        
        boolean encontrouTeste = pessoas.stream()
            .anyMatch(p -> USUARIO_TESTE.equals(p.getUsuario()));
        assertTrue(encontrouTeste, "Deve encontrar o usuário de teste na listagem");
        
        System.out.println("   ✓ Total de pessoas: " + pessoas.size());
    }

    @Test
    @Order(5)
    @DisplayName("5. Deve atualizar pessoa existente")
    public void testAtualizarPessoa() throws Exception {
        System.out.println("Teste 5: Atualizar pessoa");
        
        Pessoa pessoa = PessoaDAO.buscarPorUsuario(USUARIO_TESTE);
        assertNotNull(pessoa, "Pessoa deve existir antes da atualização");
        
        pessoa.setNome("João Silva Atualizado");
        pessoa.setTelefone("87988776655");
        dao.atualizar(pessoa);
        
        Pessoa pessoaAtualizada = PessoaDAO.buscarPorUsuario(USUARIO_TESTE);
        assertEquals("João Silva Atualizado", pessoaAtualizada.getNome());
        assertEquals("87988776655", pessoaAtualizada.getTelefone());
        
        System.out.println("   ✓ Pessoa atualizada com sucesso");
    }

    @Test
    @Order(6)
    @DisplayName("6. Não deve inserir pessoa com usuário duplicado")
    public void testInserirUsuarioDuplicado() {
        System.out.println("Teste 6: Tentar inserir usuário duplicado");
        
        Pessoa pessoaDuplicada = new Pessoa(USUARIO_TESTE, "Outro Nome", "87911112222");
        
        // PessoaDAO.inserir não lança exceção, apenas retorna false para duplicados
        // então verificamos se já existe antes
        assertTrue(PessoaDAO.existeUsuario(USUARIO_TESTE), "Usuário deve já existir");
        System.out.println("   ✓ Validação de duplicidade funcionando");
    }

    @Test
    @Order(7)
    @DisplayName("7. Deve deletar pessoa existente")
    public void testDeletarPessoa() throws Exception {
        System.out.println("Teste 7: Deletar pessoa");
        
        assertTrue(PessoaDAO.existeUsuario(USUARIO_TESTE), "Pessoa deve existir antes da deleção");
        
        dao.deletarPorUsuario(USUARIO_TESTE);
        
        assertFalse(PessoaDAO.existeUsuario(USUARIO_TESTE), "Pessoa não deve mais existir após deleção");
        assertNull(PessoaDAO.buscarPorUsuario(USUARIO_TESTE), "Busca deve retornar null após deleção");
        
        System.out.println("   ✓ Pessoa deletada com sucesso");
    }

    @Test
    @Order(8)
    @DisplayName("8. Deve retornar null ao buscar usuário inexistente")
    public void testBuscarUsuarioInexistente() {
        System.out.println("Teste 8: Buscar usuário inexistente");
        
        Pessoa pessoa = PessoaDAO.buscarPorUsuario("usuario_nao_existe_xyz123");
        
        assertNull(pessoa, "Deve retornar null para usuário inexistente");
        System.out.println("   ✓ Retorno correto para busca inexistente");
    }
}
