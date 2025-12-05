package dao;

import conexao.Conexao;
import dto.Pousada;
import dto.Quarto;
import dto.QuartoStandard;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuartoDAO extends BaseDAO { 
    public List<Quarto> listarQuartosPorPousada(int idPousada) throws SQLException {
        String sql = "SELECT * FROM quarto WHERE qua_pou = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection conn = null;

        List<Quarto> listaQuartos = new ArrayList<>();

        try {
            conn = getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idPousada);
            rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("qua_id");
                int pousada = rs.getInt("qua_pou");
                String nome = rs.getString("qua_nome");
                int camas = rs.getInt("qua_camas");
                int valor_dia = rs.getInt("qua_valor_dia");
                
                boolean jacuzzi = false;
                boolean salaDeEstar = false;
                
                Quarto quarto = new QuartoStandard(pousada, nome, camas, valor_dia);
                quarto.setId(id);
                listaQuartos.add(quarto);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar quartos: " + e.getMessage());
            throw e; // Propaga a exceção para ser tratada no nível superior
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar ResultSet: " + e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar PreparedStatement: " + e.getMessage());
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Erro ao fechar Connection: " + e.getMessage());
                }
            }
        }

        return listaQuartos;
    }
    public void adicionarQuarto(Quarto quarto) {
        String sql = "INSERT INTO quarto (qua_pou, qua_nome, qua_camas, qua_valor_dia) VALUES (?, ?, ?, ?)";

        try (Connection connection = Conexao.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, quarto.getPousada());
            preparedStatement.setString(2, quarto.getNome());
            preparedStatement.setInt(3, quarto.getCamas());
            preparedStatement.setInt(4, quarto.getValor_dia());

            preparedStatement.executeUpdate();
           
        } catch (Exception e) {
            System.out.println("Erro ao adicionar quarto: " + e.getMessage());
        }
    }
    
    public void deletarQuarto(int id, Pousada pousada) {
        String sql = "DELETE FROM quarto WHERE qua_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();
            System.out.println("Quarto deletado com sucesso!");

            // Atualizar a lista de quartos da pousada
            pousada.getQuartos().removeIf(quarto -> quarto.getId() == id);
        } catch (SQLException e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }
}

