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

        // Inserir na tabela cliente
        String sqlCliente = "INSERT INTO cliente (id, endereco, email) VALUES (?, ?, ?)";

        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlCliente)) {
            statement.setInt(1, pessoaId); // Usa o ID da pessoa
            statement.setString(2, cliente.getEndereco());
            statement.setString(3, cliente.getEmail());

            statement.executeUpdate(); // Executa a inserção

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cliente getClienteById(String id) {
        String query = "SELECT * FROM clientes WHERE id = ?";
        Cliente cliente = null;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                cliente = new Cliente();
                cliente.setId(resultSet.getString("id"));
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
        String query = "UPDATE clientes SET nome = ?, cpf = ?, telefone = ?, endereco = ?, email = ? WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, cliente.getNome());
            statement.setLong(2, cliente.getCpf());
            statement.setLong(3, cliente.getTelefone());
            statement.setString(4, cliente.getEndereco());
            statement.setString(5, cliente.getEmail());
            statement.setString(6, cliente.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCliente(String id) {
        String query = "DELETE FROM clientes WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cliente> getAllClientes() {
        String query = "SELECT * FROM clientes";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(resultSet.getString("id"));
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
