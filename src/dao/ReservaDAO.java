package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import dto.Reserva;
import conexao.Conexao;

public class ReservaDAO extends BaseDAO implements IDAO<Reserva> {
    
    @Override
    public void adicionar(Reserva reserva) throws Exception {
        String sql = "INSERT INTO reserva (res_pou, res_qua, res_pessoa, res_data_entrada, res_data_saida, res_status) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, reserva.getPousadaId());
            ps.setInt(2, reserva.getQuartoId());
            ps.setString(3, reserva.getUsuarioPessoa());
            ps.setDate(4, Date.valueOf(reserva.getDataEntrada()));
            ps.setDate(5, Date.valueOf(reserva.getDataSaida()));
            ps.setString(6, reserva.getStatus());
            
            int linhasAfetadas = ps.executeUpdate();
            
            if (linhasAfetadas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        reserva.setId(rs.getInt(1));
                    }
                }
                System.out.println("Reserva adicionada com sucesso! ID: " + reserva.getId());
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao adicionar reserva: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Reserva obterPorId(int id) throws Exception {
        String sql = "SELECT res_id, res_pou, res_qua, res_pessoa, res_data_entrada, res_data_saida, res_status " +
                     "FROM reserva WHERE res_id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Reserva(
                        rs.getInt("res_id"),
                        rs.getInt("res_pou"),
                        rs.getInt("res_qua"),
                        rs.getString("res_pessoa"),
                        rs.getDate("res_data_entrada").toLocalDate(),
                        rs.getDate("res_data_saida").toLocalDate(),
                        rs.getString("res_status")
                    );
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao buscar reserva: " + e.getMessage(), e);
        }
        
        return null;
    }
    
    @Override
    public void atualizar(Reserva reserva) throws Exception {
        String sql = "UPDATE reserva SET res_pou = ?, res_qua = ?, res_pessoa = ?, " +
                     "res_data_entrada = ?, res_data_saida = ?, res_status = ? WHERE res_id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, reserva.getPousadaId());
            ps.setInt(2, reserva.getQuartoId());
            ps.setString(3, reserva.getUsuarioPessoa());
            ps.setDate(4, Date.valueOf(reserva.getDataEntrada()));
            ps.setDate(5, Date.valueOf(reserva.getDataSaida()));
            ps.setString(6, reserva.getStatus());
            ps.setInt(7, reserva.getId());
            
            int linhasAfetadas = ps.executeUpdate();
            
            if (linhasAfetadas == 0) {
                throw new Exception("Reserva não encontrada para atualização");
            }
            
            System.out.println("Reserva atualizada com sucesso!");
        } catch (SQLException e) {
            throw new Exception("Erro ao atualizar reserva: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void deletar(int id) throws Exception {
        String sql = "DELETE FROM reserva WHERE res_id = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            int linhasAfetadas = ps.executeUpdate();
            
            if (linhasAfetadas == 0) {
                throw new Exception("Reserva não encontrada para exclusão");
            }
            
            System.out.println("Reserva deletada com sucesso!");
        } catch (SQLException e) {
            throw new Exception("Erro ao deletar reserva: " + e.getMessage(), e);
        }
    }
    
    @Override
    public List<Reserva> listarTodos() throws Exception {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT res_id, res_pou, res_qua, res_pessoa, res_data_entrada, res_data_saida, res_status " +
                     "FROM reserva ORDER BY res_data_entrada";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                reservas.add(new Reserva(
                    rs.getInt("res_id"),
                    rs.getInt("res_pou"),
                    rs.getInt("res_qua"),
                    rs.getString("res_pessoa"),
                    rs.getDate("res_data_entrada").toLocalDate(),
                    rs.getDate("res_data_saida").toLocalDate(),
                    rs.getString("res_status")
                ));
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao listar reservas: " + e.getMessage(), e);
        }
        
        return reservas;
    }
    
    /**
     * Lista reservas por pousada
     */
    public List<Reserva> listarPorPousada(int pousadaId) throws Exception {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT res_id, res_pou, res_qua, res_pessoa, res_data_entrada, res_data_saida, res_status " +
                     "FROM reserva WHERE res_pou = ? ORDER BY res_data_entrada";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, pousadaId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reservas.add(new Reserva(
                        rs.getInt("res_id"),
                        rs.getInt("res_pou"),
                        rs.getInt("res_qua"),
                        rs.getString("res_pessoa"),
                        rs.getDate("res_data_entrada").toLocalDate(),
                        rs.getDate("res_data_saida").toLocalDate(),
                        rs.getString("res_status")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao listar reservas por pousada: " + e.getMessage(), e);
        }
        
        return reservas;
    }
    
    /**
     * Lista reservas por quarto
     */
    public List<Reserva> listarPorQuarto(int quartoId) throws Exception {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT res_id, res_pou, res_qua, res_pessoa, res_data_entrada, res_data_saida, res_status " +
                     "FROM reserva WHERE res_qua = ? ORDER BY res_data_entrada";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, quartoId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reservas.add(new Reserva(
                        rs.getInt("res_id"),
                        rs.getInt("res_pou"),
                        rs.getInt("res_qua"),
                        rs.getString("res_pessoa"),
                        rs.getDate("res_data_entrada").toLocalDate(),
                        rs.getDate("res_data_saida").toLocalDate(),
                        rs.getString("res_status")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao listar reservas por quarto: " + e.getMessage(), e);
        }
        
        return reservas;
    }
    
    /**
     * Verifica se um quarto está disponível no período
     */
    public boolean verificarDisponibilidade(int quartoId, LocalDate dataEntrada, LocalDate dataSaida) throws Exception {
        String sql = "SELECT COUNT(*) FROM reserva " +
                     "WHERE res_qua = ? AND res_status = 'S' " +
                     "AND ((res_data_entrada <= ? AND res_data_saida >= ?) " +
                     "OR (res_data_entrada <= ? AND res_data_saida >= ?) " +
                     "OR (res_data_entrada >= ? AND res_data_saida <= ?))";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, quartoId);
            ps.setDate(2, Date.valueOf(dataEntrada));
            ps.setDate(3, Date.valueOf(dataEntrada));
            ps.setDate(4, Date.valueOf(dataSaida));
            ps.setDate(5, Date.valueOf(dataSaida));
            ps.setDate(6, Date.valueOf(dataEntrada));
            ps.setDate(7, Date.valueOf(dataSaida));
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) == 0; // Retorna true se não houver conflitos
                }
            }
        } catch (SQLException e) {
            throw new Exception("Erro ao verificar disponibilidade: " + e.getMessage(), e);
        }
        
        return false;
    }
}
