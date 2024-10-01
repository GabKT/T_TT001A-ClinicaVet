
package gabkt.Dao;

import gabkt.model.Consulta;
import gabkt.model.Animal;
import gabkt.model.Cliente;
import gabkt.model.Veterinario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

public class ConsultaDao {

    private Connection connection;

    public ConsultaDao(Connection connection) {
        this.connection = connection;
    }

    public void inserirConsulta(Consulta consulta) throws SQLException {
        String sql = "INSERT INTO consultas (id, data, horario, tipo, status, animal_id, cliente_id, veterinario_id) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, consulta.getId());
            stmt.setDate(2, new java.sql.Date(consulta.getData().getTime()));
            stmt.setString(3, consulta.getHorario().getID());
            stmt.setString(4, consulta.getTipo());
            stmt.setString(5, consulta.getStatus());
            stmt.setString(6, consulta.getAnimal().getId());
            stmt.setString(7, consulta.getCliente().getId());
            stmt.setString(8, consulta.getVeterinario().getId());
            stmt.executeUpdate();
        }
    }

    public Consulta buscarConsultaPorId(String id) throws SQLException {
        String sql = "SELECT * FROM consultas WHERE id = ?";
        Consulta consulta = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                consulta = new Consulta();
                consulta.setId(rs.getString("id"));
                consulta.setData(rs.getDate("data"));
                consulta.setHorario(TimeZone.getTimeZone(rs.getString("horario")));
                consulta.setTipo(rs.getString("tipo"));
                consulta.setStatus(rs.getString("status"));
                consulta.setAnimal(new Animal(rs.getString("animal_id"), sql, sql, sql, 0, 0, null, null));
                consulta.setCliente(new Cliente(rs.getString("cliente_id"), sql, 0, 0, sql, sql));
                consulta.setVeterinario(new Veterinario(rs.getString("veterinario_id"), sql, 0, 0));
            }
        }

        return consulta;
    }

    public void atualizarConsulta(Consulta consulta) throws SQLException {
        String sql = "UPDATE consultas SET data = ?, horario = ?, tipo = ?, status = ?, animal_id = ?, cliente_id = ?, veterinario_id = ? "
                +
                "WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(consulta.getData().getTime()));
            stmt.setString(2, consulta.getHorario().getID());
            stmt.setString(3, consulta.getTipo());
            stmt.setString(4, consulta.getStatus());
            stmt.setString(5, consulta.getAnimal().getId());
            stmt.setString(6, consulta.getCliente().getId());
            stmt.setString(7, consulta.getVeterinario().getId());
            stmt.setString(8, consulta.getId());
            stmt.executeUpdate();
        }
    }

    public void excluirConsulta(String id) throws SQLException {
        String sql = "DELETE FROM consultas WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Consulta> listarConsultas() throws SQLException {
        String sql = "SELECT * FROM consultas";
        List<Consulta> consultas = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getString("id"));
                consulta.setData(rs.getDate("data"));
                consulta.setHorario(TimeZone.getTimeZone(rs.getString("horario")));
                consulta.setTipo(rs.getString("tipo"));
                consulta.setStatus(rs.getString("status"));
                consulta.setAnimal(new Animal(rs.getString("animal_id"), sql, sql, sql, 0, 0, null, null));
                consulta.setCliente(new Cliente(rs.getString("cliente_id"), sql, 0, 0, sql, sql));
                consulta.setVeterinario(new Veterinario(rs.getString("veterinario_id"), sql, 0, 0));
                consultas.add(consulta);
            }
        }

        return consultas;
    }
}