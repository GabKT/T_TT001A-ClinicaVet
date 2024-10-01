package gabkt.Dao;

import gabkt.model.Veterinario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDao extends DAO {

    public void addVeterinario(Veterinario veterinario) {
        String query = "INSERT INTO veterinarios (id, nome, cpf, telefone, crmv, especialidade) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, veterinario.getId());
            statement.setString(2, veterinario.getNome());
            statement.setLong(3, veterinario.getCpf());
            statement.setLong(4, veterinario.getTelefone());
            statement.setString(5, veterinario.getCrmv());
            statement.setString(6, veterinario.getEspecialidade());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Veterinario getVeterinarioById(String id) {
        String query = "SELECT * FROM veterinarios WHERE id = ?";
        Veterinario veterinario = null;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                veterinario = new Veterinario();
                veterinario.setId(resultSet.getString("id"));
                veterinario.setNome(resultSet.getString("nome"));
                veterinario.setCpf(resultSet.getLong("cpf"));
                veterinario.setTelefone(resultSet.getLong("telefone"));
                veterinario.setCrmv(resultSet.getString("crmv"));
                veterinario.setEspecialidade(resultSet.getString("especialidade"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return veterinario;
    }

    public void updateVeterinario(Veterinario veterinario) {
        String query = "UPDATE veterinarios SET nome = ?, cpf = ?, telefone = ?, crmv = ?, especialidade = ? WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, veterinario.getNome());
            statement.setLong(2, veterinario.getCpf());
            statement.setLong(3, veterinario.getTelefone());
            statement.setString(4, veterinario.getCrmv());
            statement.setString(5, veterinario.getEspecialidade());
            statement.setString(6, veterinario.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVeterinario(String id) {
        String query = "DELETE FROM veterinarios WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Veterinario> getAllVeterinarios() {
        String query = "SELECT * FROM veterinarios";
        List<Veterinario> veterinarios = new ArrayList<>();
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Veterinario veterinario = new Veterinario();
                veterinario.setId(resultSet.getString("id"));
                veterinario.setNome(resultSet.getString("nome"));
                veterinario.setCpf(resultSet.getLong("cpf"));
                veterinario.setTelefone(resultSet.getLong("telefone"));
                veterinario.setCrmv(resultSet.getString("crmv"));
                veterinario.setEspecialidade(resultSet.getString("especialidade"));
                veterinarios.add(veterinario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return veterinarios;
    }
}
