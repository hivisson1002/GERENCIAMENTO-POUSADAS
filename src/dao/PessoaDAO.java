package dao;

import conexao.Conexao;
import dto.Pessoa;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO extends BaseDAO implements IDAO<Pessoa> {
    
    public static boolean existeUsuario(String usuario) {
        String sql = "SELECT 1 FROM pessoa WHERE usuario = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("ERRO ao verificar usuário: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean inserir(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (usuario, nome, telefone) VALUES (?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, pessoa.getUsuario());
            ps.setString(2, pessoa.getNome());
            ps.setString(3, pessoa.getTelefone());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("ERRO ao inserir pessoa: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public static Pessoa buscarPorUsuario(String usuario) {
        String sql = "SELECT usuario, nome, telefone FROM pessoa WHERE usuario = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pessoa(rs.getString("usuario"), rs.getString("nome"), rs.getString("telefone"));
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO ao buscar pessoa: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // Implementação da interface IDAO
    @Override
    public void adicionar(Pessoa pessoa) throws Exception {
        if (!inserir(pessoa)) {
            throw new Exception("Falha ao adicionar pessoa");
        }
    }
    
    @Override
    public Pessoa obterPorId(int id) throws Exception {
        // Pessoa usa usuario (String) como chave, não int
        throw new UnsupportedOperationException("Use buscarPorUsuario(String) para Pessoa");
    }
    
    @Override
    public void atualizar(Pessoa pessoa) throws Exception {
        String sql = "UPDATE pessoa SET nome = ?, telefone = ? WHERE usuario = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, pessoa.getNome());
            ps.setString(2, pessoa.getTelefone());
            ps.setString(3, pessoa.getUsuario());
            
            if (ps.executeUpdate() == 0) {
                throw new Exception("Pessoa não encontrada para atualização");
            }
        }
    }
    
    @Override
    public void deletar(int id) throws Exception {
        // Pessoa usa usuario (String) como chave, não int
        throw new UnsupportedOperationException("Use deletarPorUsuario(String) para Pessoa");
    }
    
    public void deletarPorUsuario(String usuario) throws Exception {
        String sql = "DELETE FROM pessoa WHERE usuario = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario);
            
            if (ps.executeUpdate() == 0) {
                throw new Exception("Pessoa não encontrada para exclusão");
            }
        }
    }
    
    @Override
    public List<Pessoa> listarTodos() throws Exception {
        List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT usuario, nome, telefone FROM pessoa";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                pessoas.add(new Pessoa(
                    rs.getString("usuario"),
                    rs.getString("nome"),
                    rs.getString("telefone")
                ));
            }
        }
        
        return pessoas;
    }
}