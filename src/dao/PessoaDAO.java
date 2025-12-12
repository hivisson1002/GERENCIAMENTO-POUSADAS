package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dto.Pessoa;
import conexao.Conexao;

public class PessoaDAO {
    
    public static boolean existeUsuario(String user) {
        String sql = "SELECT 1 FROM Pessoa WHERE user = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, user);
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
        String sql = "INSERT INTO Pessoa (user, nome, tel) VALUES (?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, pessoa.getUser());
            ps.setString(2, pessoa.getNome());
            ps.setString(3, pessoa.getTel());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("ERRO ao inserir pessoa: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
    
    public static Pessoa buscarPorUser(String user) {
        String sql = "SELECT user, nome, tel FROM Pessoa WHERE user = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, user);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Pessoa(rs.getString("user"), rs.getString("nome"), rs.getString("tel"));
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO ao buscar pessoa: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}