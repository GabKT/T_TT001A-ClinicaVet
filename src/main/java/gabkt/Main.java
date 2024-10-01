package gabkt;

import java.sql.SQLException;

import gabkt.Dao.ClienteDao;
import gabkt.model.Cliente;

public class Main {
    public static void main(String[] args) {

        ClienteDao clienteDao = new ClienteDao();
        Cliente cliente = new Cliente("João da Silva", 12345678901L, 11987654321L, "Rua das Flores, 123",
                "joao@example.com");

        // Inserção do cliente
        try {
            clienteDao.addCliente(cliente);
            System.out.println("Cliente Inserido com sucesso!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}