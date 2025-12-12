package dao;

import conexao.Conexao;
import dto.PessoaFisica;
import exception.DadosInvalidosException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO extends BaseDAO implements IDAO<PessoaFisica> {
    
    public static boolean cadastrarPessoaFisica(PessoaFisica pf) throws DadosInvalidosException {
        if (pf == null || pf.getUsuario() == null) {
            throw new DadosInvalidosException("PessoaFisica ou usuario não pode ser nulo");
        }
        
        if (PessoaDAO.existeUsuario(pf.getUsuario())) {
            System.out.println("Erro: Usuário já cadastrado.");
            return false;
        }

        String sqlPessoa = "INSERT INTO pessoa (usuario, nome, telefone) VALUES (?, ?, ?)";
        String sqlPessoaFisica = "INSERT INTO pessoa_fisica (pf_usuario, pf_cpf, pf_sexo) VALUES (?, ?, ?)";

        Connection conn = null;
        try {
            conn = Conexao.getConexao();
            conn.setAutoCommit(false);

            // Inserir na tabela Pessoa
            try (PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa)) {
                psPessoa.setString(1, pf.getUsuario());
                psPessoa.setString(2, pf.getNome());
                psPessoa.setString(3, pf.getTelefone());
                psPessoa.executeUpdate();
            }

            // Inserir na tabela PessoaFisica
            try (PreparedStatement psPessoaFisica = conn.prepareStatement(sqlPessoaFisica)) {
                psPessoaFisica.setString(1, pf.getUsuario());
                psPessoaFisica.setInt(2, pf.getCpf());
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

    public static PessoaFisica buscarPorUsuario(String usuario) {
        String sql = "SELECT p.usuario, p.nome, p.telefone, pf.pf_cpf, pf.pf_sexo " +
                     "FROM pessoa p " +
                     "JOIN pessoa_fisica pf ON p.usuario = pf.pf_usuario " +
                     "WHERE p.usuario = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PessoaFisica(
                        rs.getString("usuario"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getInt("pf_cpf"),
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
    
    // Implementação da interface IDAO
    @Override
    public void adicionar(PessoaFisica pf) throws Exception {
        if (!cadastrarPessoaFisica(pf)) {
            throw new Exception("Falha ao adicionar pessoa física");
        }
    }
    
    @Override
    public PessoaFisica obterPorId(int id) throws Exception {
        throw new UnsupportedOperationException("Use buscarPorUsuario(String) para PessoaFisica");
    }
    
    @Override
    public void atualizar(PessoaFisica pf) throws Exception {
        String sqlPessoa = "UPDATE pessoa SET nome = ?, telefone = ? WHERE usuario = ?";
        String sqlPessoaFisica = "UPDATE pessoa_fisica SET pf_cpf = ?, pf_sexo = ? WHERE pf_usuario = ?";
        
        Connection conn = null;
        try {
            conn = Conexao.getConexao();
            conn.setAutoCommit(false);
            
            // Atualizar tabela Pessoa
            try (PreparedStatement ps = conn.prepareStatement(sqlPessoa)) {
                ps.setString(1, pf.getNome());
                ps.setString(2, pf.getTelefone());
                ps.setString(3, pf.getUsuario());
                ps.executeUpdate();
            }
            
            // Atualizar tabela PessoaFisica
            try (PreparedStatement ps = conn.prepareStatement(sqlPessoaFisica)) {
                ps.setInt(1, pf.getCpf());
                ps.setString(2, pf.getSexo());
                ps.setString(3, pf.getUsuario());
                ps.executeUpdate();
            }
            
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("ERRO no rollback: " + ex.getMessage());
                }
            }
            throw new Exception("Erro ao atualizar pessoa física: " + e.getMessage());
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
    
    @Override
    public void deletar(int id) throws Exception {
        throw new UnsupportedOperationException("Use deletarPorUsuario(String) para PessoaFisica");
    }
    
    public static void deletarPorUsuario(String usuario) throws Exception {
        // A exclusão em pessoa_fisica vai em cascata quando deletar de pessoa
        String sql = "DELETE FROM pessoa WHERE usuario = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario);
            
            if (ps.executeUpdate() == 0) {
                throw new Exception("Pessoa física não encontrada para exclusão");
            }
        }
    }
    
    @Override
    public List<PessoaFisica> listarTodos() throws Exception {
        List<PessoaFisica> pessoas = new ArrayList<>();
        String sql = "SELECT p.usuario, p.nome, p.telefone, pf.pf_cpf, pf.pf_sexo " +
                     "FROM pessoa p " +
                     "JOIN pessoa_fisica pf ON p.usuario = pf.pf_usuario";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                pessoas.add(new PessoaFisica(
                    rs.getString("usuario"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getInt("pf_cpf"),
                    rs.getString("pf_sexo")
                ));
            }
        }
        
        return pessoas;
    }
}