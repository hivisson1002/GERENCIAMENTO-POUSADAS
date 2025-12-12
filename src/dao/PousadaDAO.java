package dao;

import conexao.Conexao;
import dto.Pousada;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PousadaDAO extends BaseDAO implements IDAO<Pousada> {
    
    public void mostrarPousadas() {
        String sql = "SELECT * FROM pousada";
        PreparedStatement ps = null;

        try {
            Connection conn = getConnection();
            ps = conn.prepareStatement(sql);
            ResultSet resultado = ps.executeQuery();
            while (resultado.next()) {
                String codigo = resultado.getString("pou_id");
                String nome = resultado.getString("pou_nome");
                System.out.println("Pousada " + codigo + ": " + nome);
            }

            ps.close();
        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    public Pousada obterPousadaPorId(int id) {
        Pousada pousada = null;
        String query = "SELECT * FROM pousada WHERE pou_id = ?";
    
        try (Connection connection = Conexao.getConexao();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
    
            if (resultSet.next()) {
                pousada = new Pousada();
                pousada.setId(resultSet.getInt("pou_id"));
                pousada.setNome(resultSet.getString("pou_nome"));
                pousada.setEndereco(resultSet.getString("pou_end"));
                pousada.setBairro(resultSet.getString("pou_bairro"));
                pousada.setCidade(resultSet.getString("pou_cid"));
                pousada.setEstado(resultSet.getString("pou_estado"));
                pousada.setTelefone(resultSet.getString("pou_tel"));
                pousada.setEstrelas(resultSet.getInt("pou_estrelas"));
                pousada.setObservacao(resultSet.getString("pou_obs"));
                pousada.setSite(resultSet.getString("pou_site"));
                pousada.setStatus(resultSet.getString("pou_status"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao obter pousada: " + e.getMessage());
        }
    
        return pousada;
    }
     
    // Implementação da interface IDAO
    @Override
    public void adicionar(Pousada pousada) throws Exception {
        adicionarPousada(pousada);
    }
    
    @Override
    public Pousada obterPorId(int id) throws Exception {
        return obterPousadaPorId(id);
    }
    
    @Override
    public void atualizar(Pousada pousada) throws Exception {
        atualizarPousada(pousada.getId(), pousada);
    }
    
    @Override
    public void deletar(int id) throws Exception {
        deletarPousada(id);
    }
    
    @Override
    public List<Pousada> listarTodos() throws Exception {
        return listarTodas();
    }
    
    // Métodos específicos da classe
    public void adicionarPousada(Pousada pousada) {
        String sql = "INSERT INTO pousada (pou_nome, pou_end, pou_bairro, pou_cid, pou_estado, pou_tel, pou_estrelas, pou_obs, pou_site, pou_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pousada.getNome());
            ps.setString(2, pousada.getEndereco());
            ps.setString(3, pousada.getBairro());
            ps.setString(4, pousada.getCidade());
            ps.setString(5, pousada.getEstado());
            ps.setString(6, pousada.getTelefone());
            ps.setInt(7, pousada.getEstrelas());
            ps.setString(8, pousada.getObservacao());
            ps.setString(9, pousada.getSite());
            ps.setString(10, pousada.getStatus());

            ps.executeUpdate();
            System.out.println("Pousada adicionada com sucesso!");

        } catch (Exception e) {
            System.out.println("ERRO: " + e.getMessage());
        }
    }

    public boolean pousadaExiste(int id) {
        String sql = "SELECT COUNT(*) FROM pousada WHERE pou_id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao verificar a existência da pousada: " + e.getMessage());
        }
        return false;
    }
    
    public void atualizarPousada(int id, Pousada pousada) {
        StringBuilder sql = new StringBuilder("UPDATE pousada SET ");
        List<Object> params = new ArrayList<>();
    
        if (pousada.getNome() != null && !pousada.getNome().isEmpty()) {
            sql.append("pou_nome = ?, ");
            params.add(pousada.getNome());
        }
        if (pousada.getEndereco() != null && !pousada.getEndereco().isEmpty()) {
            sql.append("pou_end = ?, ");
            params.add(pousada.getEndereco());
        }
        if (pousada.getBairro() != null && !pousada.getBairro().isEmpty()) {
            sql.append("pou_bairro = ?, ");
            params.add(pousada.getBairro());
        }
        if (pousada.getCidade() != null && !pousada.getCidade().isEmpty()) {
            sql.append("pou_cid = ?, ");
            params.add(pousada.getCidade());
        }
        if (pousada.getEstado() != null && !pousada.getEstado().isEmpty()) {
            sql.append("pou_estado = ?, ");
            params.add(pousada.getEstado());
        }
        if (pousada.getTelefone() != null && !pousada.getTelefone().isEmpty()) {
            sql.append("pou_tel = ?, ");
            params.add(pousada.getTelefone());
        }
        if (pousada.getEstrelas() >= 1 && pousada.getEstrelas() <= 5) {
            sql.append("pou_estrelas = ?, ");
            params.add(pousada.getEstrelas());
        }
        if (pousada.getObservacao() != null && !pousada.getObservacao().isEmpty()) {
            sql.append("pou_obs = ?, ");
            params.add(pousada.getObservacao());
        }
        if (pousada.getSite() != null && !pousada.getSite().isEmpty()) {
            sql.append("pou_site = ?, ");
            params.add(pousada.getSite());
        }
        if (pousada.getStatus() != null && !pousada.getStatus().isEmpty()) {
            sql.append("pou_status = ?, ");
            params.add(pousada.getStatus());
        }
    
        if (params.isEmpty()) {
            System.out.println("Nenhum campo para atualizar.");
            return;
        }
    
        sql.setLength(sql.length() - 2);
        sql.append(" WHERE pou_id = ?");
        params.add(id);
    
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
    
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
    
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Pousada atualizada com sucesso!");
            } else {
                System.out.println("Nenhuma pousada foi atualizada. Verifique o ID fornecido.");
            }
    
        } catch (SQLException e) {
            System.out.println("ERRO ao atualizar pousada: " + e.getMessage());
        }
    }
    
    public void deletarPousada(int id) {
        if (!pousadaExiste(id)) {
            System.out.println("Pousada com ID " + id + " não encontrada.");
            return;
        }
    
        String sql = "DELETE FROM pousada WHERE pou_id = ?";
    
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
    
            ps.setInt(1, id);
    
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Pousada deletada com sucesso!");
            } else {
                System.out.println("Nenhuma pousada foi deletada. Verifique o ID fornecido.");
            }
    
        } catch (SQLException e) {
            System.out.println("ERRO ao deletar pousada: " + e.getMessage());
        }
    }
    
    public List<Pousada> listarTodas() {
        List<Pousada> pousadas = new ArrayList<>();
        String sql = "SELECT * FROM pousada";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Pousada pousada = new Pousada();
                pousada.setId(rs.getInt("pou_id"));
                pousada.setNome(rs.getString("pou_nome"));
                pousada.setEndereco(rs.getString("pou_end"));
                pousada.setBairro(rs.getString("pou_bairro"));
                pousada.setCidade(rs.getString("pou_cid"));
                pousada.setEstado(rs.getString("pou_estado"));
                pousada.setTelefone(rs.getString("pou_tel"));
                pousada.setEstrelas(rs.getInt("pou_estrelas"));
                pousada.setObservacao(rs.getString("pou_obs"));
                pousada.setSite(rs.getString("pou_site"));
                pousada.setStatus(rs.getString("pou_status"));
                pousadas.add(pousada);
            }
        } catch (SQLException e) {
            System.out.println("ERRO ao listar pousadas: " + e.getMessage());
        }
        
        return pousadas;
    }
}
