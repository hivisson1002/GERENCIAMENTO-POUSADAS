package test;

import dao.PousadaDAO;
import dto.Pousada;
import java.util.List;

public class TestePousadaDAO {
    public static void main(String[] args) {
        System.out.println("=== TESTE DA CLASSE PousadaDAO ===\n");
        
        PousadaDAO dao = new PousadaDAO();
        int totalTestes = 0;
        int testesPassaram = 0;
        
        // Teste 1: Adicionar Pousada
        System.out.println("Teste 1: Adicionar Pousada");
        try {
            Pousada pousada = new Pousada();
            pousada.setNome("Pousada Teste DAO");
            pousada.setEndereco("Rua Teste, 123");
            pousada.setBairro("Centro");
            pousada.setCidade("Maceió");
            pousada.setEstado("AL");
            pousada.setTelefone("(82) 9999-8888");
            pousada.setEstrelas(4);
            pousada.setObservacao("Pousada para testes");
            pousada.setSite("www.teste.com.br");
            pousada.setStatus("S");
            
            dao.adicionarPousada(pousada);
            System.out.println("   ✓ Pousada adicionada no banco de dados");
            testesPassaram++;
        } catch (Exception e) {
            System.out.println("   ✗ Erro ao adicionar: " + e.getMessage());
        }
        totalTestes++;
        
        // Teste 2: Listar Todas as Pousadas
        System.out.println("\nTeste 2: Listar Todas as Pousadas");
        try {
            List<Pousada> pousadas = dao.listarTodas();
            if (pousadas != null && pousadas.size() > 0) {
                System.out.println("   ✓ Total de pousadas encontradas: " + pousadas.size());
                System.out.println("   Primeiras 3 pousadas:");
                for (int i = 0; i < Math.min(3, pousadas.size()); i++) {
                    Pousada p = pousadas.get(i);
                    System.out.println("     [" + p.getId() + "] " + p.getNome() + " - " + p.getCidade());
                }
                testesPassaram++;
            } else {
                System.out.println("   ✗ Nenhuma pousada encontrada");
            }
        } catch (Exception e) {
            System.out.println("   ✗ Erro ao listar: " + e.getMessage());
        }
        totalTestes++;
        
        // Teste 3: Buscar Pousada por ID
        System.out.println("\nTeste 3: Buscar Pousada por ID");
        try {
            List<Pousada> pousadas = dao.listarTodas();
            if (pousadas.size() > 0) {
                int idTeste = pousadas.get(0).getId();
                Pousada pousada = dao.obterPousadaPorId(idTeste);
                
                if (pousada != null) {
                    System.out.println("   ✓ Pousada encontrada:");
                    System.out.println("     ID: " + pousada.getId());
                    System.out.println("     Nome: " + pousada.getNome());
                    System.out.println("     Cidade: " + pousada.getCidade());
                    System.out.println("     Estrelas: " + pousada.getEstrelas());
                    testesPassaram++;
                } else {
                    System.out.println("   ✗ Pousada não encontrada");
                }
            }
        } catch (Exception e) {
            System.out.println("   ✗ Erro ao buscar: " + e.getMessage());
        }
        totalTestes++;
        
        // Teste 4: Verificar se Pousada Existe
        System.out.println("\nTeste 4: Verificar se Pousada Existe");
        try {
            List<Pousada> pousadas = dao.listarTodas();
            if (pousadas.size() > 0) {
                int idTeste = pousadas.get(0).getId();
                boolean existe = dao.pousadaExiste(idTeste);
                
                if (existe) {
                    System.out.println("   ✓ Pousada ID " + idTeste + " existe no banco");
                    testesPassaram++;
                } else {
                    System.out.println("   ✗ Pousada não encontrada");
                }
                
                // Testar ID inexistente
                boolean naoExiste = dao.pousadaExiste(99999);
                if (!naoExiste) {
                    System.out.println("   ✓ ID 99999 não existe (correto)");
                }
            }
        } catch (Exception e) {
            System.out.println("   ✗ Erro ao verificar: " + e.getMessage());
        }
        totalTestes++;
        
        // Teste 5: Atualizar Pousada
        System.out.println("\nTeste 5: Atualizar Pousada");
        try {
            List<Pousada> pousadas = dao.listarTodas();
            if (pousadas.size() > 0) {
                int idTeste = pousadas.get(0).getId();
                Pousada pousadaAtualizada = new Pousada();
                pousadaAtualizada.setNome("Pousada Atualizada via DAO");
                pousadaAtualizada.setEstrelas(5);
                
                dao.atualizarPousada(idTeste, pousadaAtualizada);
                
                // Verificar se foi atualizado
                Pousada pousadaVerificacao = dao.obterPousadaPorId(idTeste);
                if (pousadaVerificacao.getNome().equals("Pousada Atualizada via DAO")) {
                    System.out.println("   ✓ Pousada atualizada com sucesso");
                    System.out.println("     Novo nome: " + pousadaVerificacao.getNome());
                    testesPassaram++;
                } else {
                    System.out.println("   ✗ Erro: nome não foi atualizado");
                }
            }
        } catch (Exception e) {
            System.out.println("   ✗ Erro ao atualizar: " + e.getMessage());
        }
        totalTestes++;
        
        // Teste 6: Deletar Pousada
        System.out.println("\nTeste 6: Deletar Pousada");
        try {
            List<Pousada> pousadas = dao.listarTodas();
            int totalAntes = pousadas.size();
            
            if (pousadas.size() > 0) {
                // Pegar a última pousada para deletar
                int idParaDeletar = pousadas.get(pousadas.size() - 1).getId();
                
                dao.deletarPousada(idParaDeletar);
                
                // Verificar se foi deletado
                List<Pousada> pousadasDepois = dao.listarTodas();
                int totalDepois = pousadasDepois.size();
                
                if (totalDepois == totalAntes - 1) {
                    System.out.println("   ✓ Pousada deletada com sucesso");
                    System.out.println("     Total antes: " + totalAntes);
                    System.out.println("     Total depois: " + totalDepois);
                    testesPassaram++;
                } else {
                    System.out.println("   ✗ Erro: pousada não foi deletada");
                }
            }
        } catch (Exception e) {
            System.out.println("   ✗ Erro ao deletar: " + e.getMessage());
        }
        totalTestes++;
        
        // Teste 7: Mostrar Pousadas (método de listagem)
        System.out.println("\nTeste 7: Método mostrarPousadas()");
        try {
            System.out.println("   Listagem de pousadas:");
            dao.mostrarPousadas();
            System.out.println("   ✓ Método executado sem erros");
            testesPassaram++;
        } catch (Exception e) {
            System.out.println("   ✗ Erro ao mostrar: " + e.getMessage());
        }
        totalTestes++;
        
        // Resumo dos testes
        System.out.println("\n" + "=".repeat(50));
        System.out.println("RESUMO DOS TESTES - PousadaDAO");
        System.out.println("=".repeat(50));
        System.out.println("Total de testes: " + totalTestes);
        System.out.println("Testes passaram: " + testesPassaram);
        System.out.println("Testes falharam: " + (totalTestes - testesPassaram));
        System.out.println("Taxa de sucesso: " + (testesPassaram * 100 / totalTestes) + "%");
        
        if (testesPassaram == totalTestes) {
            System.out.println("\n✓✓✓ TODOS OS TESTES PASSARAM! ✓✓✓");
        } else {
            System.out.println("\n⚠ ALGUNS TESTES FALHARAM ⚠");
        }
    }
}
