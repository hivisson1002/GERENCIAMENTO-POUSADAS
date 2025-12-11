package dao;

import java.sql.Connection;
import conexao.Conexao;

/**
 * Classe base abstrata para todos os DAOs
 * Fornece acesso à conexão com o banco de dados
 */
public abstract class BaseDAO {
    protected Connection getConnection() {
        return Conexao.getConexao();
    }
}
