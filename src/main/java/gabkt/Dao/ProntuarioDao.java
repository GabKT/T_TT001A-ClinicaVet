package gabkt.Dao;

import gabkt.model.Prontuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProntuarioDao extends DAO {

    public void addProntuario(Prontuario prontuario) {
        String query = "INSERT INTO prontuario (data_consulta, diagnostico, tratamento, observacoes, animal) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(prontuario.getDataConsulta().getTime()));
            statement.setString(2, prontuario.getDiagnostico());
            statement.setString(3, prontuario.getTratamento());
            statement.setString(4, prontuario.getObservacoes());
            statement.setInt(5, prontuario.getAnimal());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Prontuario getProntuarioById(int id) {
        String query = "SELECT * FROM prontuario WHERE id = ?";
        Prontuario prontuario = null;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                prontuario = new Prontuario();
                prontuario.setId(resultSet.getInt("id"));
                prontuario.setDataConsulta(resultSet.getDate("data_consulta"));
                prontuario.setDiagnostico(resultSet.getString("diagnostico"));
                prontuario.setTratamento(resultSet.getString("tratamento"));
                prontuario.setObservacoes(resultSet.getString("observacoes"));
                prontuario.setAnimal(resultSet.getInt("animal"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prontuario;
    }

    public void updateProntuario(Prontuario prontuario) {
        String query = "UPDATE prontuario SET data_consulta = ?, diagnostico = ?, tratamento = ?, observacoes = ?, animal = ? WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(prontuario.getDataConsulta().getTime()));
            statement.setString(2, prontuario.getDiagnostico());
            statement.setString(3, prontuario.getTratamento());
            statement.setString(4, prontuario.getObservacoes());
            statement.setInt(5, prontuario.getAnimal());
            statement.setInt(6, prontuario.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProntuario(int id) {
        String query = "DELETE FROM prontuario WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Prontuario> getAllProntuarios() {
        String query = "SELECT * FROM prontuario";
        List<Prontuario> prontuarios = new ArrayList<>();
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Prontuario prontuario = new Prontuario();
                prontuario.setId(resultSet.getInt("id"));
                prontuario.setDataConsulta(resultSet.getDate("data_consulta"));
                prontuario.setDiagnostico(resultSet.getString("diagnostico"));
                prontuario.setTratamento(resultSet.getString("tratamento"));
                prontuario.setObservacoes(resultSet.getString("observacoes"));
                prontuario.setAnimal(resultSet.getInt("animal"));
                prontuarios.add(prontuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prontuarios;
    }
}
