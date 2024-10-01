package gabkt.Dao;

import gabkt.model.RelatorioF;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelatorioFDao extends DAO {

    public void addRelatorioF(RelatorioF relatorio) {
        String query = "INSERT INTO relatorios_f (id, periodo, valor_total) VALUES (?, ?, ?)";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, relatorio.getId());
            statement.setDate(2, new java.sql.Date(relatorio.getPeriodo().getTime()));
            statement.setDouble(3, relatorio.getValorTotal());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public RelatorioF getRelatorioFById(String id) {
        String query = "SELECT * FROM relatorios_f WHERE id = ?";
        RelatorioF relatorio = null;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                relatorio = new RelatorioF();
                relatorio.setId(resultSet.getString("id"));
                relatorio.setPeriodo(resultSet.getDate("periodo"));
                relatorio.setValorTotal(resultSet.getDouble("valor_total"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatorio;
    }

    public void updateRelatorioF(RelatorioF relatorio) {
        String query = "UPDATE relatorios_f SET periodo = ?, valor_total = ? WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, new java.sql.Date(relatorio.getPeriodo().getTime()));
            statement.setDouble(2, relatorio.getValorTotal());
            statement.setString(3, relatorio.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRelatorioF(String id) {
        String query = "DELETE FROM relatorios_f WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<RelatorioF> getAllRelatoriosF() {
        String query = "SELECT * FROM relatorios_f";
        List<RelatorioF> relatorios = new ArrayList<>();
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                RelatorioF relatorio = new RelatorioF();
                relatorio.setId(resultSet.getString("id"));
                relatorio.setPeriodo(resultSet.getDate("periodo"));
                relatorio.setValorTotal(resultSet.getDouble("valor_total"));
                relatorios.add(relatorio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatorios;
    }
}
