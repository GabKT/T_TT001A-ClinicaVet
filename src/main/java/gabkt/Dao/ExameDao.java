package gabkt.Dao;

import gabkt.model.Exame;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExameDao extends DAO {

    public void addExame(Exame exame) {
        String query = "INSERT INTO exame (tipo, data, resultado, consulta_id) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, exame.getTipo());
            statement.setDate(2, new java.sql.Date(exame.getData().getTime()));
            statement.setString(3, exame.getResultado());
            statement.setInt(4, exame.getConsulta());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Exame getExameById(int id) {
        String query = "SELECT * FROM exame WHERE id = ?";
        Exame exame = null;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                exame = new Exame();
                exame.setId(resultSet.getInt("id"));
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
        String query = "UPDATE exame SET tipo = ?, data = ?, resultado = ?, consulta_id = ? WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, exame.getTipo());
            statement.setDate(2, new java.sql.Date(exame.getData().getTime()));
            statement.setString(3, exame.getResultado());
            statement.setInt(4, exame.getConsulta());
            statement.setInt(5, exame.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExame(int id) {
        String query = "DELETE FROM exame WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Exame> getAllExames() {
        String query = "SELECT * FROM exame";
        List<Exame> exames = new ArrayList<>();
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Exame exame = new Exame();
                exame.setId(resultSet.getInt("id"));
                exame.setTipo(resultSet.getString("tipo"));
                exame.setData(resultSet.getDate("data"));
                exame.setResultado(resultSet.getString("resultado"));
                exame.setConsulta(resultSet.getInt("consulta_id"));
                exames.add(exame);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exames;
    }
}
