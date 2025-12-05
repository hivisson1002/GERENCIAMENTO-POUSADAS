package dao;

import java.sql.Connection;
import conexao.Conexao;

public class BaseDAO {
    protected Connection getConnection() {
        return Conexao.getConexao();
    }
}
