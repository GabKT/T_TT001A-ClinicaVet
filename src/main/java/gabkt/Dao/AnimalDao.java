package gabkt.Dao;

import gabkt.model.Animal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDao extends DAO {

    public void addAnimal(Animal animal) {
        String query = "INSERT INTO animal (nome, especie, raca, idade, peso, dono) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, animal.getNome());
            statement.setString(2, animal.getEspecie());
            statement.setString(3, animal.getRaca());
            statement.setInt(4, animal.getIdade());
            statement.setDouble(5, animal.getPeso());
            statement.setInt(6, animal.getDono());
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
                animal.setDono(resultSet.getInt("dono"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animal;
    }

    public void updateAnimal(Animal animal) {
        String query = "UPDATE animal SET nome = ?, especie = ?, raca = ?, idade = ?, peso = ?, dono = ? WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, animal.getNome());
            statement.setString(2, animal.getEspecie());
            statement.setString(3, animal.getRaca());
            statement.setInt(4, animal.getIdade());
            statement.setDouble(5, animal.getPeso());
            statement.setInt(6, animal.getDono());
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
        String query = "SELECT * FROM animal";
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
                animal.setDono(resultSet.getInt("dono"));
                animais.add(animal);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animais;
    }

    public Animal getAnimalByName(String name, int dono) {
        String query = "SELECT * FROM animal WHERE nome = ? AND dono = ?";
        Animal animal = null;

        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, dono);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                animal = new Animal();
                animal.setId(resultSet.getInt("id"));
                animal.setNome(resultSet.getString("nome"));
                animal.setEspecie(resultSet.getString("especie"));
                animal.setRaca(resultSet.getString("raca"));
                animal.setIdade(resultSet.getInt("idade"));
                animal.setPeso(resultSet.getDouble("peso"));
                animal.setDono(resultSet.getInt("dono"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return animal;
    }
}
