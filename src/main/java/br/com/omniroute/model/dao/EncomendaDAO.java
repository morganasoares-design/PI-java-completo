
package br.com.omniroute.model.dao;

import br.com.omniroute.model.Encomenda;
import br.com.omniroute.util.DB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EncomendaDAO {

    // CADASTRAR ENCOMENDA
    public void inserir(Encomenda e) throws SQLException {

        String sql = "INSERT INTO Encomenda "
                + "(cliente, endereco, numero, bairro, cidade, cep, uf, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = DB.get();
             PreparedStatement p = c.prepareStatement(sql)) {

            set(p, e);
            p.executeUpdate();
        }
    }

    // PREENCHER OS CAMPOS DO SQL
    private void set(PreparedStatement p, Encomenda e)
            throws SQLException {

        p.setString(1, e.getCliente());
        p.setString(2, e.getEndereco());
        p.setString(3, e.getNumero());
        p.setString(4, e.getBairro());
        p.setString(5, e.getCidade());
        p.setString(6, e.getCep());
        p.setString(7, e.getUf());
        p.setString(8, e.getStatus());
    }

    // LISTAR ENCOMENDAS
    public List<Encomenda> listar() throws SQLException {

        List<Encomenda> lista = new ArrayList<>();

        String sql = "SELECT * FROM Encomenda ORDER BY id DESC";

        try (Connection c = DB.get();
             Statement s = c.createStatement();
             ResultSet r = s.executeQuery(sql)) {

            while (r.next()) {

                Encomenda e = new Encomenda(
                        r.getString("cliente"),
                        r.getString("endereco"),
                        r.getString("numero"),
                        r.getString("bairro"),
                        r.getString("cidade"),
                        r.getString("cep"),
                        r.getString("uf"),
                        r.getString("status")
                );

                e.setId(r.getInt("id"));

                lista.add(e);
            }
        }

        return lista;
    }

    // ATUALIZAR ENCOMENDA
    public void atualizar(int id, Encomenda e)
            throws SQLException {

        String sql = "UPDATE Encomenda SET "
                + "cliente=?, endereco=?, numero=?, bairro=?, "
                + "cidade=?, cep=?, uf=?, status=? "
                + "WHERE id=?";

        try (Connection c = DB.get();
             PreparedStatement p = c.prepareStatement(sql)) {

            set(p, e);
            p.setInt(9, id);

            p.executeUpdate();
        }
    }

    // EXCLUIR ENCOMENDA
    public void excluir(int id) throws SQLException {

        String sql = "DELETE FROM Encomenda WHERE id=?";

        try (Connection c = DB.get();
             PreparedStatement p = c.prepareStatement(sql)) {

            p.setInt(1, id);
            p.executeUpdate();
        }
    }
}
