package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public static Connection getConexao() {
        try {
            Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5433/pousadas",
                "postgres",
                "1234"
            );
            return conn;
        } catch (SQLException e) {
            System.out.println("Erro ao se conectar com o banco: " + e.getMessage());
            return null;
        }
    }
}
