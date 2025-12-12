package dao;

import java.sql.*;
import dto.PessoaFisica;
import conexao.Conexao;
import exception.DadosInvalidosException;

public class PessoaFisicaDAO extends PessoaDAO {
    
    public static boolean cadastrarPessoaFisica(PessoaFisica pf) throws DadosInvalidosException {
        if (pf == null || pf.getUser() == null) {
            throw new DadosInvalidosException("PessoaFisica ou user não pode ser nulo");
        }
        
        if (existeUsuario(pf.getUser())) {
            System.out.println("Erro: Usuário já cadastrado.");
            return false;
        }

        String sqlPessoa = "INSERT INTO pessoa (user, nome, tel) VALUES (?, ?, ?)";
        String sqlPessoaFisica = "INSERT INTO pessoa_fisica (pf_user, pf_cpf, pf_sexo) VALUES (?, ?, ?)";

        Connection conn = null;
        try {
            conn = Conexao.getConexao();
            conn.setAutoCommit(false);

            // Inserir na tabela Pessoa
            try (PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa)) {
                psPessoa.setString(1, pf.getUser());
                psPessoa.setString(2, pf.getNome());
                psPessoa.setString(3, pf.getTel());
                psPessoa.executeUpdate();
            }

            // Inserir na tabela PessoaFisica
            try (PreparedStatement psPessoaFisica = conn.prepareStatement(sqlPessoaFisica)) {
                psPessoaFisica.setString(1, pf.getUser());
                psPessoaFisica.setString(2, pf.getCpf());
                psPessoaFisica.setString(3, pf.getSexo());
                psPessoaFisica.executeUpdate();
            }

            conn.commit();
            System.out.println("Pessoa Física cadastrada com sucesso!");
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("ERRO no rollback: " + ex.getMessage());
                }
            }
            System.out.println("ERRO ao cadastrar Pessoa Física: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("ERRO ao fechar conexão: " + e.getMessage());
                }
            }
        }
    }

    public static PessoaFisica buscarPorUser(String user) {
        String sql = "SELECT p.user, p.nome, p.tel, pf.pf_cpf, pf.pf_sexo " +
                     "FROM pessoa p " +
                     "JOIN pessoa_fisica pf ON p.user = pf.pf_user " +
                     "WHERE p.user = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PessoaFisica(
                        rs.getString("user"),
                        rs.getString("nome"),
                        rs.getString("tel"),
                        rs.getString("pf_cpf"),
                        rs.getString("pf_sexo")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO ao buscar Pessoa Física: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}