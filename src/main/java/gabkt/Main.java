package gabkt;

import java.sql.SQLException;
import java.util.List;

import gabkt.Dao.ClienteDao;
import gabkt.Dao.VeterinarioDao;
import gabkt.model.Cliente;
import gabkt.model.Veterinario;

public class Main {
    public static void main(String[] args) {

        ClienteDao clienteDao = new ClienteDao();

        List<Cliente> listaClientes = clienteDao.getAllClientes();

        System.out.println(listaClientes.toString());

        VeterinarioDao veterinarioDao = new VeterinarioDao();

        List<Veterinario> listaVet = veterinarioDao.getAllVeterinarios();

        System.out.println(listaVet);

        /*
         * clienteDao.addCliente(cliente);
         * System.out.println("Cliente Inserido com sucesso!");
         */

    }
}