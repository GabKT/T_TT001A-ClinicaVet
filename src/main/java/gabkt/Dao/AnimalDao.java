package gabkt.Dao;

import gabkt.model.Animal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDao extends DAO {

    public void addAnimal(Animal animal) {
        String query = "INSERT INTO animal (id, nome, especie, raca, idade, peso, dono_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, animal.getId());
            statement.setString(2, animal.getNome());
            statement.setString(3, animal.getEspecie());
            statement.setString(4, animal.getRaca());
            statement.setInt(5, animal.getIdade());
            statement.setDouble(6, animal.getPeso());
            statement.setInt(7, animal.getDono().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Animal getAnimalById(int id) {
        String query = "SELECT * FROM animal WHERE id = ?";
        Animal animal = null;
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                animal = new Animal();
                animal.setId(resultSet.getInt("id"));
                animal.setNome(resultSet.getString("nome"));
                animal.setEspecie(resultSet.getString("especie"));
                animal.setRaca(resultSet.getString("raca"));
                animal.setIdade(resultSet.getInt("idade"));
                animal.setPeso(resultSet.getDouble("peso"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animal;
    }

    public void updateAnimal(Animal animal) {
        String query = "UPDATE animal SET nome = ?, especie = ?, raca = ?, idade = ?, peso = ?, dono_id = ? WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, animal.getNome());
            statement.setString(2, animal.getEspecie());
            statement.setString(3, animal.getRaca());
            statement.setInt(4, animal.getIdade());
            statement.setDouble(5, animal.getPeso());
            statement.setInt(6, animal.getDono().getId());
            statement.setInt(7, animal.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAnimal(int id) {
        String query = "DELETE FROM animal WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Animal> getAllAnimals() {
        String query = "SELECT * FROM animais";
        List<Animal> animais = new ArrayList<>();
        try (Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                Animal animal = new Animal();
                animal.setId(resultSet.getInt("id"));
                animal.setNome(resultSet.getString("nome"));
                animal.setEspecie(resultSet.getString("especie"));
                animal.setRaca(resultSet.getString("raca"));
                animal.setIdade(resultSet.getInt("idade"));
                animal.setPeso(resultSet.getDouble("peso"));
                animais.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animais;
    }
}
