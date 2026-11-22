
package br.com.omniroute.model.dao;

import br.com.omniroute.util.DB;
import java.sql.*;
import java.util.*;

public class RotaDAO {

    public void criar(String partida, String chegada, List<Integer> ids)
            throws SQLException {

        try (Connection c = DB.get()) {
            c.setAutoCommit(false);

            try {
                int id;

                try (PreparedStatement p = c.prepareStatement(
                        "INSERT INTO Rota(ponto_partida,ponto_chegada) VALUES(?,?)",
                        Statement.RETURN_GENERATED_KEYS)) {

                    p.setString(1, partida);
                    p.setString(2, chegada);
                    p.executeUpdate();

                    try (ResultSet r = p.getGeneratedKeys()) {
                        id = r.next() ? r.getInt(1) : 0;
                    }
                }

                try (PreparedStatement p = c.prepareStatement(
                        "INSERT INTO Rota_Encomenda(rota_id,encomenda_id) VALUES(?,?)")) {

                    for (int x : ids) {
                        p.setInt(1, id);
                        p.setInt(2, x);
                        p.addBatch();
                    }

                    p.executeBatch();
                }

                c.commit();

            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }

    public List<String[]> listar() throws SQLException {

        List<String[]> l = new ArrayList<>();

        String sql = "SELECT Rota.id, ponto_partida, ponto_chegada, " +
                     "COUNT(encomenda_id) qtd " +
                     "FROM Rota LEFT JOIN Rota_Encomenda " +
                     "ON Rota.id = rota_id GROUP BY Rota.id";

        try (Connection c = DB.get();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

            while (r.next()) {
                l.add(new String[]{
                    r.getString(1),
                    r.getString(2),
                    r.getString(3),
                    r.getString(4)
                });
            }
        }

        return l;
    }

    // EXCLUIR ROTA
    public void excluir(int id) throws SQLException {

        try (Connection c = DB.get()) {
            c.setAutoCommit(false);

            try {
                // Primeiro exclui os relacionamentos
                try (PreparedStatement p = c.prepareStatement(
                        "DELETE FROM Rota_Encomenda WHERE rota_id = ?")) {

                    p.setInt(1, id);
                    p.executeUpdate();
                }

                // Depois exclui a rota
                try (PreparedStatement p = c.prepareStatement(
                        "DELETE FROM Rota WHERE id = ?")) {

                    p.setInt(1, id);

                    int linhas = p.executeUpdate();

                    if (linhas == 0) {
                        throw new SQLException("Rota não encontrada.");
                    }
                }

                c.commit();

            } catch (SQLException e) {
                c.rollback();
                throw e;
            }
        }
    }
}
