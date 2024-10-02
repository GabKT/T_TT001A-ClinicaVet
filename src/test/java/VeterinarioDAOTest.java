import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import gabkt.Dao.VeterinarioDao;
import gabkt.model.Veterinario;

import java.util.List;

public class VeterinarioDAOTest {

    private VeterinarioDao veterinarioDAO;

    @Before
    public void setUp() {
        veterinarioDAO = new VeterinarioDao();
        // Limpeza de dados antes de iniciar cada teste (se necessário)
    }

    @After
    public void tearDown() {
        // Limpeza dos dados após cada teste (se necessário)
        // Você pode deletar todos os registros de teste criados aqui para evitar
        // interferências.
    }

    @Test
    public void testAddVeterinario() throws Exception {
        Veterinario veterinario = new Veterinario();
        veterinario.setNome("Dr. Carlos");
        veterinario.setCpf(54356754376L);
        veterinario.setTelefone(988667722L);
        veterinario.setCrmv("CRMV3737");
        veterinario.setEspecialidade("Cirurgia");

        veterinarioDAO.addVeterinario(veterinario);

        Veterinario veterinarioTest = veterinarioDAO.getVeterinarioByCpf(veterinario.getCpf());

        assertNotNull(veterinarioTest);

        assertEquals(veterinario.getNome(), veterinarioTest.getNome());
        assertEquals(veterinario.getCpf(), veterinarioTest.getCpf());
        assertEquals(veterinario.getTelefone(), veterinarioTest.getTelefone());
        assertEquals(veterinario.getCrmv(), veterinarioTest.getCrmv());
        assertEquals(veterinario.getEspecialidade(), veterinarioTest.getEspecialidade());
    }

    @Test
    public void testUpdateVeterinario() throws Exception {

        Veterinario veterinario = veterinarioDAO.getVeterinarioById(3);

        veterinario.setNome("Dr. Pedro Atualizado");
        veterinario.setEspecialidade("Neurologia");
        veterinarioDAO.updateVeterinario(veterinario);

        Veterinario veterinarioTest = veterinarioDAO.getVeterinarioByCpf(veterinario.getCpf());

        assertNotNull(veterinarioTest);

        assertEquals("Dr. Pedro Atualizado", veterinarioTest.getNome());
        assertEquals(veterinario.getCpf(), veterinarioTest.getCpf());
        assertEquals(veterinario.getTelefone(), veterinarioTest.getTelefone());
        assertEquals(veterinario.getCrmv(), veterinarioTest.getCrmv());
        assertEquals("Neurologia", veterinarioTest.getEspecialidade());
    }

    @Test
    public void testDeleteVeterinario() throws Exception {

        Veterinario veterinario = new Veterinario();
        veterinario.setNome("Dr. Gerson");
        veterinario.setCpf(22233344455L);
        veterinario.setTelefone(912345678L);
        veterinario.setCrmv("CRMV9876");
        veterinario.setEspecialidade("Dermatologia");

        veterinarioDAO.addVeterinario(veterinario);

        Veterinario veterinarioAdicionado = veterinarioDAO.getVeterinarioByCpf(veterinario.getCpf());
        assertNotNull(veterinarioAdicionado);
        int idParaDeletar = veterinarioAdicionado.getId();

        veterinarioDAO.deleteVeterinario(idParaDeletar);

        Veterinario veterinarioDeletado = veterinarioDAO.getVeterinarioById(idParaDeletar);
        assertNull(veterinarioDeletado);
    }

}
