
package br.com.omniroute.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/omniroute";

    private static final String USUARIO = "postgres";

    // Substitua pela senha do seu PostgreSQL
    private static final String SENHA = "123456";

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public static void init() {

        String sql =
            "INSERT INTO usuario (nome, email, senha) " +
            "VALUES ('Administrador', 'admin@omniroute.com', '123456') " +
            "ON CONFLICT (email) DO NOTHING";

        try (Connection conexao = get();
             var comando = conexao.createStatement()) {

            comando.executeUpdate(sql);

        } catch (SQLException e) {
            throw new RuntimeException(
                "Falha ao iniciar banco PostgreSQL: "
                + e.getMessage(), e
            );
        }
    }
}
