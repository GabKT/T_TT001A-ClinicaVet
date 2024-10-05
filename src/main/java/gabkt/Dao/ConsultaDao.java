
package gabkt.Dao;

import gabkt.model.Consulta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDao extends DAO {

    public void inserirConsulta(Consulta consulta) {
        String sql = "INSERT INTO consulta (data, horario, tipo, status, animal, cliente, veterinario) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, new java.sql.Date(consulta.getData().getTime()));
            statement.setTime(2, consulta.getHorario());
            statement.setString(3, consulta.getTipo());
            statement.setString(4, consulta.getStatus());
            statement.setInt(5, consulta.getAnimal());
            statement.setInt(6, consulta.getCliente());
            statement.setInt(7, consulta.getVeterinario());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Consulta buscarConsultaPorId(int id) {
        String sql = "SELECT * FROM consulta WHERE id = ?";
        Consulta consulta = null;

        try (Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                consulta.setData(rs.getDate("data"));
                consulta.setHorario(rs.getTime("horario"));
                consulta.setTipo(rs.getString("tipo"));
                consulta.setStatus(rs.getString("status"));
                consulta.setAnimal(rs.getInt("animal"));
                consulta.setCliente(rs.getInt("cliente"));
                consulta.setVeterinario(rs.getInt("veterinario"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consulta;
    }

    public void atualizarConsulta(Consulta consulta) {
        String sql = "UPDATE consulta SET data = ?, horario = ?, tipo = ?, status = ?, animal = ?, cliente = ?, veterinario = ? "
                +
                "WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(consulta.getData().getTime()));
            stmt.setTime(2, consulta.getHorario());
            stmt.setString(3, consulta.getTipo());
            stmt.setString(4, consulta.getStatus());
            stmt.setInt(5, consulta.getAnimal());
            stmt.setInt(6, consulta.getCliente());
            stmt.setInt(7, consulta.getVeterinario());
            stmt.setInt(8, consulta.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluirConsulta(int id) {
        String sql = "DELETE FROM consulta WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Consulta> listarConsultas() throws SQLException {
        String sql = "SELECT * FROM consulta";
        List<Consulta> consultas = new ArrayList<>();

        try (Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Consulta consulta = new Consulta();
                consulta.setId(rs.getInt("id"));
                consulta.setData(rs.getDate("data"));
                consulta.setHorario(rs.getTime("horario"));
                consulta.setTipo(rs.getString("tipo"));
                consulta.setStatus(rs.getString("status"));
                consulta.setAnimal(rs.getInt("animal"));
                consulta.setCliente(rs.getInt("cliente"));
                consulta.setVeterinario(rs.getInt("veterinario"));
                consultas.add(consulta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return consultas;
    }
}