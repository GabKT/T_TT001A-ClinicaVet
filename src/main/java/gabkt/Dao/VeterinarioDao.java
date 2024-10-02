package gabkt.Dao;

import gabkt.model.Veterinario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDao extends DAO {

    public void addVeterinario(Veterinario veterinario) {
        String sql = "INSERT INTO Pessoa (nome, cpf, telefone) VALUES (?, ?, ?) RETURNING id";

        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, veterinario.getNome());
            stmt.setLong(2, veterinario.getCpf());
            stmt.setLong(3, veterinario.getTelefone());

            int pessoaId;
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pessoaId = rs.getInt("id");
                } else {
                    throw new SQLException("Erro ao gerar ID para Pessoa.");
                }
            }

            String sqlVeterinario = "INSERT INTO Veterinario (id, crmv, especialidade) VALUES (?, ?, ?)";
            try (PreparedStatement stmtVeterinario = conn.prepareStatement(sqlVeterinario)) {
                stmtVeterinario.setInt(1, pessoaId);
                stmtVeterinario.setString(2, veterinario.getCrmv());
                stmtVeterinario.setString(3, veterinario.getEspecialidade());
                stmtVeterinario.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateVeterinario(Veterinario veterinario) {
        String query = "UPDATE pessoa SET nome = ?, cpf = ?, telefone = ? WHERE id = ?; UPDATE veterinario SET crmv = ?, especialidade = ? WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, veterinario.getNome());
            statement.setLong(2, veterinario.getCpf());
            statement.setLong(3, veterinario.getTelefone());

            statement.setInt(4, veterinario.getId());
            statement.setString(5, veterinario.getCrmv());
            statement.setString(6, veterinario.getEspecialidade());
            statement.setInt(7, veterinario.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteVeterinario(int id) {
        String deletePessoaQuery = "DELETE FROM pessoa WHERE id = ?";
        String deleteVeterinarioQuery = "DELETE FROM veterinario WHERE id = ?";
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (PreparedStatement veterinarioStmt = connection.prepareStatement(deleteVeterinarioQuery);
                    PreparedStatement pessoaStmt = connection.prepareStatement(deletePessoaQuery)) {

                veterinarioStmt.setInt(1, id);
                veterinarioStmt.executeUpdate();

                pessoaStmt.setInt(1, id);
                pessoaStmt.executeUpdate();

                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Veterinario getVeterinarioById(int id) {
        String query = "SELECT p.nome, p.cpf, p.telefone, v.crmv, v.especialidade FROM pessoa p JOIN veterinario v on p.id = v.id WHERE p.id = ?";
        Veterinario veterinario = null;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                veterinario = new Veterinario();
                veterinario.setId(id);
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

    public Veterinario getVeterinarioByCpf(Long cpf) {
        String query = "SELECT p.id, p.nome, p.cpf, p.telefone, v.crmv, v.especialidade FROM pessoa p JOIN veterinario v on p.id = v.id WHERE p.cpf = ?";
        Veterinario veterinario = null;
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, cpf);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                veterinario = new Veterinario();
                veterinario.setId(resultSet.getInt("id"));
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

    public List<Veterinario> getAllVeterinarios() {
        String query = "SELECT p.id, p.nome, p.cpf, p.telefone, v.crmv, v.especialidade " +
                "FROM veterinario v " +
                "JOIN pessoa p ON v.id = p.id";
        List<Veterinario> veterinarios = new ArrayList<>();
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Veterinario veterinario = new Veterinario();
                veterinario.setId(resultSet.getInt("id"));
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
