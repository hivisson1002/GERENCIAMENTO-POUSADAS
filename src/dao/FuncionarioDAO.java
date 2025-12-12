package dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import dto.Funcionario;
import conexao.Conexao;
import exception.DadosInvalidosException;

public class FuncionarioDAO extends BaseDAO implements IDAO<Funcionario> {
    
    public static boolean cadastrarFuncionario(Funcionario func) throws DadosInvalidosException {
        if (func == null || func.getUsuario() == null) {
            throw new DadosInvalidosException("Funcionario ou usuario não pode ser nulo");
        }
        
        if (PessoaDAO.existeUsuario(func.getUsuario())) {
            System.out.println("Erro: Usuário já cadastrado.");
            return false;
        }

        String sqlPessoa = "INSERT INTO pessoa (usuario, nome, telefone) VALUES (?, ?, ?)";
        String sqlFuncionario = "INSERT INTO funcionario (fun_usuario, fun_cpf, fun_sexo, fun_cargo, fun_salario) VALUES (?, ?, ?, ?, ?)";

        Connection conn = null;
        try {
            conn = Conexao.getConexao();
            conn.setAutoCommit(false);

            // Inserir na tabela Pessoa
            try (PreparedStatement psPessoa = conn.prepareStatement(sqlPessoa)) {
                psPessoa.setString(1, func.getUsuario());
                psPessoa.setString(2, func.getNome());
                psPessoa.setString(3, func.getTelefone());
                psPessoa.executeUpdate();
            }

            // Inserir na tabela Funcionario
            try (PreparedStatement psFuncionario = conn.prepareStatement(sqlFuncionario)) {
                psFuncionario.setString(1, func.getUsuario());
                psFuncionario.setInt(2, func.getCpf());
                psFuncionario.setString(3, func.getSexo());
                psFuncionario.setString(4, func.getCargo());
                psFuncionario.setInt(5, func.getSalario());
                psFuncionario.executeUpdate();
            }

            conn.commit();
            System.out.println("Funcionário cadastrado com sucesso!");
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    System.out.println("ERRO no rollback: " + ex.getMessage());
                }
            }
            System.out.println("ERRO ao cadastrar Funcionário: " + e.getMessage());
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

    public static Funcionario buscarPorUsuario(String usuario) {
        String sql = "SELECT p.usuario, p.nome, p.telefone, f.fun_cpf, f.fun_sexo, f.fun_cargo, f.fun_salario " +
                     "FROM pessoa p " +
                     "JOIN funcionario f ON p.usuario = f.fun_usuario " +
                     "WHERE p.usuario = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Funcionario(
                        rs.getString("usuario"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getInt("fun_cpf"),
                        rs.getString("fun_sexo"),
                        rs.getString("fun_cargo"),
                        rs.getInt("fun_salario")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("ERRO ao buscar Funcionário: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // Implementação da interface IDAO
    @Override
    public void adicionar(Funcionario func) throws Exception {
        if (!cadastrarFuncionario(func)) {
            throw new Exception("Falha ao adicionar funcionário");
        }
    }
    
    @Override
    public Funcionario obterPorId(int id) throws Exception {
        throw new UnsupportedOperationException("Use buscarPorUsuario(String) para Funcionario");
    }
    
    @Override
    public void atualizar(Funcionario func) throws Exception {
        String sqlPessoa = "UPDATE pessoa SET nome = ?, telefone = ? WHERE usuario = ?";
        String sqlFuncionario = "UPDATE funcionario SET fun_cpf = ?, fun_sexo = ?, fun_cargo = ?, fun_salario = ? WHERE fun_usuario = ?";
        
        Connection conn = null;
        try {
            conn = Conexao.getConexao();
            conn.setAutoCommit(false);
            
            // Atualizar tabela Pessoa
            try (PreparedStatement ps = conn.prepareStatement(sqlPessoa)) {
                ps.setString(1, func.getNome());
                ps.setString(2, func.getTelefone());
                ps.setString(3, func.getUsuario());
                ps.executeUpdate();
            }
            
            // Atualizar tabela Funcionario
            try (PreparedStatement ps = conn.prepareStatement(sqlFuncionario)) {
                ps.setInt(1, func.getCpf());
                ps.setString(2, func.getSexo());
                ps.setString(3, func.getCargo());
                ps.setInt(4, func.getSalario());
                ps.setString(5, func.getUsuario());
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
            throw new Exception("Erro ao atualizar funcionário: " + e.getMessage());
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
        throw new UnsupportedOperationException("Use deletarPorUsuario(String) para Funcionario");
    }
    
    public static void deletarPorUsuario(String usuario) throws Exception {
        // A exclusão em funcionario vai em cascata quando deletar de pessoa
        String sql = "DELETE FROM pessoa WHERE usuario = ?";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, usuario);
            
            if (ps.executeUpdate() == 0) {
                throw new Exception("Funcionário não encontrado para exclusão");
            }
        }
    }
    
    @Override
    public List<Funcionario> listarTodos() throws Exception {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT p.usuario, p.nome, p.telefone, f.fun_cpf, f.fun_sexo, f.fun_cargo, f.fun_salario " +
                     "FROM pessoa p " +
                     "JOIN funcionario f ON p.usuario = f.fun_usuario";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                funcionarios.add(new Funcionario(
                    rs.getString("usuario"),
                    rs.getString("nome"),
                    rs.getString("telefone"),
                    rs.getInt("fun_cpf"),
                    rs.getString("fun_sexo"),
                    rs.getString("fun_cargo"),
                    rs.getInt("fun_salario")
                ));
            }
        }
        
        return funcionarios;
    }
}
