package test;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TesteConexao {
    public static void main(String[] args) {
        System.out.println("=== TESTE DE CONEXÃO E CRIAÇÃO DO BANCO ===\n");
        
        // Teste 1: Verificar conexão
        System.out.println("1. Testando conexão com o banco de dados...");
        Connection conn = Conexao.getConexao();
        
        if (conn != null) {
            System.out.println("   ✓ Conexão estabelecida com sucesso!");
            
            try {
                // Teste 2: Verificar tabelas existentes
                System.out.println("\n2. Verificando tabelas criadas...");
                Statement stmt = conn.createStatement();
                
                String[] tabelas = {"pessoa", "pousada", "quarto", "reserva", 
                                   "pessoa_fisica", "pessoa_juridica", "funcionario"};
                
                for (String tabela : tabelas) {
                    ResultSet rs = stmt.executeQuery(
                        "SELECT EXISTS (SELECT FROM information_schema.tables " +
                        "WHERE table_schema = 'public' AND table_name = '" + tabela + "')"
                    );
                    
                    if (rs.next() && rs.getBoolean(1)) {
                        System.out.println("   ✓ Tabela '" + tabela + "' existe");
                    } else {
                        System.out.println("   ✗ Tabela '" + tabela + "' NÃO existe");
                    }
                    rs.close();
                }
                
                // Teste 3: Verificar estrutura da tabela pousada
                System.out.println("\n3. Verificando estrutura da tabela 'pousada'...");
                ResultSet rs = stmt.executeQuery(
                    "SELECT column_name, data_type FROM information_schema.columns " +
                    "WHERE table_name = 'pousada' ORDER BY ordinal_position"
                );
                
                int colunas = 0;
                while (rs.next()) {
                    System.out.println("   - " + rs.getString("column_name") + 
                                     " (" + rs.getString("data_type") + ")");
                    colunas++;
                }
                
                if (colunas > 0) {
                    System.out.println("   ✓ Estrutura da tabela verificada (" + colunas + " colunas)");
                } else {
                    System.out.println("   ✗ Tabela 'pousada' não encontrada ou vazia");
                }
                
                rs.close();
                
                // Teste 4: Verificar dados existentes
                System.out.println("\n4. Verificando dados existentes...");
                
                // Verificar pousadas
                rs = stmt.executeQuery("SELECT COUNT(*) FROM pousada");
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("   - Pousadas cadastradas: " + count);
                    if (count > 0) {
                        ResultSet rsPousadas = stmt.executeQuery(
                            "SELECT pou_id, pou_nome, pou_cid FROM pousada LIMIT 3"
                        );
                        while (rsPousadas.next()) {
                            System.out.println("     • ID: " + rsPousadas.getInt("pou_id") + 
                                             " - " + rsPousadas.getString("pou_nome") + 
                                             " (" + rsPousadas.getString("pou_cid") + ")");
                        }
                        rsPousadas.close();
                    }
                }
                rs.close();
                
                // Verificar quartos
                rs = stmt.executeQuery("SELECT COUNT(*) FROM quarto");
                if (rs.next()) {
                    System.out.println("   - Quartos cadastrados: " + rs.getInt(1));
                }
                rs.close();
                
                // Verificar reservas
                rs = stmt.executeQuery("SELECT COUNT(*) FROM reserva");
                if (rs.next()) {
                    System.out.println("   - Reservas cadastradas: " + rs.getInt(1));
                }
                rs.close();
                
                // Verificar pessoas
                rs = stmt.executeQuery("SELECT COUNT(*) FROM pessoa");
                if (rs.next()) {
                    System.out.println("   - Pessoas cadastradas: " + rs.getInt(1));
                }
                rs.close();
                
                stmt.close();
                conn.close();
                
                System.out.println("\n=== TESTE CONCLUÍDO COM SUCESSO ===");
                
            } catch (Exception e) {
                System.out.println("   ✗ Erro ao verificar tabelas: " + e.getMessage());
                System.out.println("\n⚠ ATENÇÃO: O banco 'pousadas' existe, mas as tabelas podem não estar criadas.");
                System.out.println("   Execute o script SQL primeiro: src/db/postgree-pousda.sql");
            }
            
        } else {
            System.out.println("   ✗ Falha na conexão!");
            System.out.println("\n⚠ Verifique:");
            System.out.println("   1. PostgreSQL está rodando?");
            System.out.println("   2. O banco 'pousadas' existe? (crie com: CREATE DATABASE pousadas;)");
            System.out.println("   3. Usuário 'postgres' e senha '1234' estão corretos?");
        }
    }
}
