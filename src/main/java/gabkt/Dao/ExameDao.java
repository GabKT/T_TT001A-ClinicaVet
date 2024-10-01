package gabkt.Dao;

import gabkt.model.Exame;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExameDao extends DAO {

    public void addExame(Exame exame) {
        String query = "INSERT INTO exames (id, tipo, data, resultado) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, exame.getId());
            statement.setString(2, exame.getTipo());
            statement.setDate(3, new java.sql.Date(exame.getData().getTime()));
            statement.setString(4, exame.getResultado());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Exame getExameById(String id) {
        String query = "SELECT * FROM exames WHERE id = ?";
        Exame exame = null;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                exame = new Exame();
                exame.setId(resultSet.getString("id"));
                exame.setTipo(resultSet.getString("tipo"));
                exame.setData(resultSet.getDate("data"));
                exame.setResultado(resultSet.getString("resultado"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exame;
    }

    public void updateExame(Exame exame) {
        String query = "UPDATE exames SET tipo = ?, data = ?, resultado = ? WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, exame.getTipo());
            statement.setDate(2, new java.sql.Date(exame.getData().getTime()));
            statement.setString(3, exame.getResultado());
            statement.setString(4, exame.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExame(String id) {
        String query = "DELETE FROM exames WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Exame> getAllExames() {
        String query = "SELECT * FROM exames";
        List<Exame> exames = new ArrayList<>();
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Exame exame = new Exame();
                exame.setId(resultSet.getString("id"));
                exame.setTipo(resultSet.getString("tipo"));
                exame.setData(resultSet.getDate("data"));
                exame.setResultado(resultSet.getString("resultado"));
                exames.add(exame);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exames;
    }
}
