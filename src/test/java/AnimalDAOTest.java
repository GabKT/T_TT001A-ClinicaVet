import static org.junit.Assert.assertEquals;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gabkt.Dao.AnimalDao;
import gabkt.model.Animal;

public class AnimalDAOTest {

    public AnimalDao animalDAO;

    @Before
    public void setUp() throws SQLException {
        // Instanciando o DAO que já gerencia a conexão
        animalDAO = new AnimalDao();

    }

    @After
    public void tearDown() throws SQLException {
        // Você pode colocar aqui qualquer limpeza extra, se necessário
    }

}
