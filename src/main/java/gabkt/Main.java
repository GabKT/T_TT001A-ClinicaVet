package gabkt;

import java.sql.SQLException;
import java.util.List;

import gabkt.Dao.ClienteDao;
import gabkt.model.Cliente;

public class Main {
    public static void main(String[] args) {

        ClienteDao clienteDao = new ClienteDao();

        List<Cliente> listaClientes = clienteDao.getAllClientes();

        System.out.println(listaClientes.toString());

        /*
         * clienteDao.addCliente(cliente);
         * System.out.println("Cliente Inserido com sucesso!");
         */

    }
}