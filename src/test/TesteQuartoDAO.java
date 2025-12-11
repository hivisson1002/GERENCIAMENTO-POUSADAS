package test;

import dao.QuartoDAO;
import dto.Quarto;
import dto.QuartoDeluxe;
import dto.QuartoPresidencial;
import dto.QuartoStandard;
import java.util.List;

public class TesteQuartoDAO {
    public static void main(String[] args) {
        System.out.println("=== TESTE DA CLASSE QuartoDAO ===\n");
        
        QuartoDAO dao = new QuartoDAO();
        int totalTestes = 0;
        int testesPassaram = 0;
        
        // ID da pousada para testes (usando pousada existente ID 4)
        int idPousadaTeste = 4;
        
        // Teste 1: Adicionar Quarto Standard
        System.out.println("Teste 1: Adicionar Quarto Standard");
        try {
            Quarto quarto = new QuartoStandard(idPousadaTeste, "Quarto Standard 999", 2, 150);
            dao.adicionarQuarto(quarto);
            System.out.println("   ✓ Quarto Standard adicionado no banco de dados");
            testesPassaram++;
        } catch (Exception e) {
            System.out.println("   ✗ Erro ao adicionar: " + e.getMessage());
        }
        totalTestes++;
        
        // Teste 2: Adicionar Quarto Deluxe
        System.out.println("\nTeste 2: Adicionar Quarto Deluxe");
        try {
            Quarto quarto = new QuartoDeluxe(idPousadaTeste, "Quarto Deluxe 998", 2, 250, true);
            dao.adicionarQuarto(quarto);
            System.out.println("   ✓ Quarto Deluxe adicionado no banco de dados");
            testesPassaram++;
        } catch (Exception e) {
            System.out.println("   ✗ Erro ao adicionar: " + e.getMessage());
        }
        totalTestes++;
        
        // Teste 3: Adicionar Quarto Presidencial
        System.out.println("\nTeste 3: Adicionar Quarto Presidencial");
        try {
            Quarto quarto = new QuartoPresidencial(idPousadaTeste, "Quarto Presidencial 997", 4, 500, true, true);
            dao.adicionarQuarto(quarto);
            System.out.println("   ✓ Quarto Presidencial adicionado no banco de dados");
            testesPassaram++;
        } catch (Exception e) {
            System.out.println("   ✗ Erro ao adicionar: " + e.getMessage());
        }
        totalTestes++;
        
        // Teste 4: Listar Quartos por Pousada
        System.out.println("\nTeste 4: Listar Quartos por Pousada");
        try {
            List<Quarto> quartos = dao.listarQuartosPorPousada(idPousadaTeste);
            if (quartos != null && quartos.size() > 0) {
                System.out.println("   ✓ Total de quartos da pousada " + idPousadaTeste + ": " + quartos.size());
                System.out.println("   Listagem dos quartos:");
                for (int i = 0; i < Math.min(5, quartos.size()); i++) {
                    Quarto q = quartos.get(i);
                    System.out.println("     [" + q.getId() + "] " + q.getNome() + 
                                     " - R$" + q.getValor_dia() + "/dia - " + q.getCamas() + " camas");
                }
                testesPassaram++;
            } else {
                System.out.println("   ✗ Nenhum quarto encontrado");
            }
        } catch (Exception e) {
            System.out.println("   ✗ Erro ao listar: " + e.getMessage());
            e.printStackTrace();
        }
        totalTestes++;
        
        // Teste 5: Verificar tipos de quartos
        System.out.println("\nTeste 5: Verificar Polimorfismo dos Quartos");
        try {
            List<Quarto> quartos = dao.listarQuartosPorPousada(idPousadaTeste);
            if (quartos != null && quartos.size() > 0) {
                System.out.println("   ✓ Testando polimorfismo:");
                for (int i = 0; i < Math.min(3, quartos.size()); i++) {
                    Quarto q = quartos.get(i);
                    System.out.println("     - " + q.getNome() + ": " + q.getDescricaoTipo());
                }
                testesPassaram++;
            }
        } catch (Exception e) {
            System.out.println("   ✗ Erro: " + e.getMessage());
        }
        totalTestes++;
        
        // Teste 6: Testar valores dos quartos
        System.out.println("\nTeste 6: Verificar Valores e Atributos dos Quartos");
        try {
            List<Quarto> quartos = dao.listarQuartosPorPousada(idPousadaTeste);
            if (quartos != null && quartos.size() > 0) {
                Quarto q = quartos.get(0);
                boolean validacao = q.getId() > 0 && 
                                   q.getPousada() == idPousadaTeste &&
                                   q.getNome() != null &&
                                   q.getCamas() > 0 &&
                                   q.getValor_dia() > 0;
                
                if (validacao) {
                    System.out.println("   ✓ Atributos do quarto estão corretos:");
                    System.out.println("     ID: " + q.getId());
                    System.out.println("     Pousada: " + q.getPousada());
                    System.out.println("     Nome: " + q.getNome());
                    System.out.println("     Camas: " + q.getCamas());
                    System.out.println("     Valor/dia: R$" + q.getValor_dia());
                    testesPassaram++;
                } else {
                    System.out.println("   ✗ Erro: atributos inválidos");
                }
            }
        } catch (Exception e) {
            System.out.println("   ✗ Erro: " + e.getMessage());
        }
        totalTestes++;
        
        // Teste 7: Deletar Quarto
        System.out.println("\nTeste 7: Deletar Quarto");
        try {
            List<Quarto> quartos = dao.listarQuartosPorPousada(idPousadaTeste);
            int totalAntes = quartos.size();
            
            if (quartos.size() > 0) {
                // Pegar o último quarto para deletar
                Quarto quartoParaDeletar = quartos.get(quartos.size() - 1);
                int idParaDeletar = quartoParaDeletar.getId();
                
                // Criar uma pousada mock para passar no método
                dto.Pousada pousadaMock = new dto.Pousada();
                pousadaMock.setId(idPousadaTeste);
                
                dao.deletarQuarto(idParaDeletar, pousadaMock);
                
                // Verificar se foi deletado
                List<Quarto> quartosDepois = dao.listarQuartosPorPousada(idPousadaTeste);
                int totalDepois = quartosDepois.size();
                
                if (totalDepois == totalAntes - 1) {
                    System.out.println("   ✓ Quarto deletado com sucesso");
                    System.out.println("     Total antes: " + totalAntes);
                    System.out.println("     Total depois: " + totalDepois);
                    testesPassaram++;
                } else {
                    System.out.println("   ✗ Erro: quarto não foi deletado");
                }
            }
        } catch (Exception e) {
            System.out.println("   ✗ Erro ao deletar: " + e.getMessage());
        }
        totalTestes++;
        
        // Resumo dos testes
        System.out.println("\n" + "=".repeat(50));
        System.out.println("RESUMO DOS TESTES - QuartoDAO");
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
