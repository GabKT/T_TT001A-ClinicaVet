package gabkt.Dao;

import gabkt.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao extends DAO {

    public void addCliente(Cliente cliente) throws SQLException {
        String sqlPessoa = "INSERT INTO pessoa (nome, cpf, telefone) VALUES (?, ?, ?) RETURNING id";
        int pessoaId;

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlPessoa)) {
            statement.setString(1, cliente.getNome());
            statement.setLong(2, cliente.getCpf());
            statement.setLong(3, cliente.getTelefone());

            // Executa a inserção e obtém o ID gerado
            ResultSet generatedKeys = statement.executeQuery();
            if (generatedKeys.next()) {
                pessoaId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Falha ao inserir pessoa, ID não foi gerado.");
            }
        }

        String sqlCliente = "INSERT INTO cliente (id, endereco, email) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlCliente)) {
            statement.setInt(1, pessoaId);
            statement.setString(2, cliente.getEndereco());
            statement.setString(3, cliente.getEmail());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cliente getClienteById(int id) {
        String query = "SELECT p.nome, p.cpf, p.telefone, c.endereco, c.email " +
                "FROM pessoa p JOIN cliente c ON p.id = c.id " +
                "WHERE c.id = ?";
        Cliente cliente = null;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cliente = new Cliente();
                cliente.setId(id);
                cliente.setNome(resultSet.getString("nome"));
                cliente.setCpf(resultSet.getLong("cpf"));
                cliente.setTelefone(resultSet.getLong("telefone"));
                cliente.setEndereco(resultSet.getString("endereco"));
                cliente.setEmail(resultSet.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    public void updateCliente(Cliente cliente) {
        String query = "UPDATE pessoa SET nome = ?, cpf = ?, telefone = ? " +
                "WHERE id = ?; " +
                "UPDATE cliente SET endereco = ?, email = ? WHERE id = ?";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, cliente.getNome());
            statement.setLong(2, cliente.getCpf());
            statement.setLong(3, cliente.getTelefone());
            statement.setInt(4, cliente.getId());

            statement.setString(5, cliente.getEndereco());
            statement.setString(6, cliente.getEmail());
            statement.setInt(7, cliente.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCliente(int id) {
        String deleteClienteQuery = "DELETE FROM cliente WHERE id = ?";
        String deletePessoaQuery = "DELETE FROM pessoa WHERE id = ?";

        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement clienteStmt = connection.prepareStatement(deleteClienteQuery);
                    PreparedStatement pessoaStmt = connection.prepareStatement(deletePessoaQuery)) {

                clienteStmt.setInt(1, id);
                clienteStmt.executeUpdate();

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

    public List<Cliente> getAllClientes() {
        String query = "SELECT p.id, p.nome, p.cpf, p.telefone, \n" + //
                "       c.id, c.endereco, c.email\n" + //
                "FROM pessoa p\n" + //
                "JOIN cliente c ON p.id = c.id;\n";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getInt("id"));
                cliente.setNome(resultSet.getString("nome"));
                cliente.setCpf(resultSet.getLong("cpf"));
                cliente.setTelefone(resultSet.getLong("telefone"));
                cliente.setEndereco(resultSet.getString("endereco"));
                cliente.setEmail(resultSet.getString("email"));
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
}
