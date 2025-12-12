package dao;

import conexao.Conexao;
import dto.Pessoa;
import dto.PessoaFisica;
import exception.DadosInvalidosException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaFisicaDAO extends BaseDAO implements IDAO<PessoaFisica> {
    
    private PessoaDAO pessoaDAO = new PessoaDAO();
    
    public static boolean cadastrarPessoaFisica(PessoaFisica pf) throws DadosInvalidosException {
        if (pf == null || pf.getUsuario() == null) {
            throw new DadosInvalidosException("PessoaFisica ou usuario não pode ser nulo");
        }
        
        if (PessoaDAO.existeUsuario(pf.getUsuario())) {
            System.out.println("Erro: Usuário já cadastrado.");
            return false;
        }

        // Primeiro insere pessoa, depois pessoa_fisica (ordem de FK)
        try {
            PessoaDAO.inserir(new Pessoa(pf.getUsuario(), pf.getNome(), pf.getTelefone()));
            
            String sql = "INSERT INTO pessoa_fisica (pf_usuario, pf_cpf, pf_sexo) VALUES (?, ?, ?)";
            try (Connection conn = Conexao.getConexao();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, pf.getUsuario());
                ps.setInt(2, pf.getCpf());
                ps.setString(3, pf.getSexo());
                ps.executeUpdate();
            }
            
            System.out.println("Pessoa Física cadastrada com sucesso!");
            return true;
        } catch (SQLException e) {
            System.out.println("ERRO ao cadastrar Pessoa Física: " + e.getMessage());
            return false;
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
                        rs.getString("usuario").trim(),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getInt("pf_cpf"),
                        rs.getString("pf_sexo").trim()
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO ao buscar Pessoa Física: " + e.getMessage());
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
        // Atualiza pessoa primeiro
        pessoaDAO.atualizar(new Pessoa(pf.getUsuario(), pf.getNome(), pf.getTelefone()));
        
        // Depois atualiza pessoa_fisica
        String sql = "UPDATE pessoa_fisica SET pf_cpf = ?, pf_sexo = ? WHERE pf_usuario = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pf.getCpf());
            ps.setString(2, pf.getSexo());
            ps.setString(3, pf.getUsuario());
            ps.executeUpdate();
        }
    }
    
    @Override
    public void deletar(int id) throws Exception {
        throw new UnsupportedOperationException("Use deletarPorUsuario(String) para PessoaFisica");
    }
    
    public static void deletarPorUsuario(String usuario) throws Exception {
        // Deleta pessoa_fisica primeiro (FK), depois pessoa
        String sql = "DELETE FROM pessoa_fisica WHERE pf_usuario = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            ps.executeUpdate();
        }
        
        PessoaDAO dao = new PessoaDAO();
        dao.deletarPorUsuario(usuario);
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
                    rs.getString("usuario").trim(),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getInt("pf_cpf"),
                    rs.getString("pf_sexo").trim()
                ));
            }
        }
        
        return pessoas;
    }
}
