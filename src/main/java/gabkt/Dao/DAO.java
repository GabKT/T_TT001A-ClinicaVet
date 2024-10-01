package gabkt.Dao;

import java.sql.*;

public class DAO {
    private static final String DB = "jdbc:postgresql://localhost:5433/clinicaVet";
    private static final String USER = "postgres";
    private static final String PASSWORD = "123";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(DB, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                throw new Exception("Erro ao conectar ao banco de dados");
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return connection;
    }

    protected void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}