package gabkt.model;

import java.util.Date;
import java.util.TimeZone;

public class Consulta {
    private String id;
    private Date data;
    private TimeZone horario;
    private String tipo;
    private String status;
    private Animal animal;
    private Cliente cliente;
    private Veterinario veterinario;

    public Consulta() {
    }

    public Consulta(String id, Date data, TimeZone horario, String tipo, String status, Animal animal, Cliente cliente,
            Veterinario veterinario) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.status = status;
        this.animal = animal;
        this.cliente = cliente;
        this.veterinario = veterinario;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public TimeZone getHorario() {
        return horario;
    }

    public void setHorario(TimeZone horario) {
        this.horario = horario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void agendarConsulta() {
    }

    public void cancelarConsulta() {
    }

    public Animal getAnimal() {
        return animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Veterinario getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(Veterinario veterinario) {
        this.veterinario = veterinario;
    }
}

/*
 * 
 * package gabkt.dao;
 * 
 * import gabkt.model.Consulta;
 * import gabkt.model.Animal;
 * import gabkt.model.Cliente;
 * import gabkt.model.Veterinario;
 * 
 * import java.sql.Connection;
 * import java.sql.PreparedStatement;
 * import java.sql.ResultSet;
 * import java.sql.SQLException;
 * import java.util.ArrayList;
 * import java.util.List;
 * 
 * public class ConsultaDAO {
 * 
 * private Connection connection;
 * 
 * public ConsultaDAO(Connection connection) {
 * this.connection = connection;
 * }
 * 
 * public void inserirConsulta(Consulta consulta) throws SQLException {
 * String sql =
 * "INSERT INTO consultas (id, data, horario, tipo, status, animal_id, cliente_id, veterinario_id) "
 * +
 * "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
 * try (PreparedStatement stmt = connection.prepareStatement(sql)) {
 * stmt.setString(1, consulta.getId());
 * stmt.setDate(2, new java.sql.Date(consulta.getData().getTime()));
 * stmt.setString(3, consulta.getHorario().getID());
 * stmt.setString(4, consulta.getTipo());
 * stmt.setString(5, consulta.getStatus());
 * stmt.setString(6, consulta.getAnimal().getId());
 * stmt.setString(7, consulta.getCliente().getId());
 * stmt.setString(8, consulta.getVeterinario().getId());
 * stmt.executeUpdate();
 * }
 * }
 * 
 * public Consulta buscarConsultaPorId(String id) throws SQLException {
 * String sql = "SELECT * FROM consultas WHERE id = ?";
 * Consulta consulta = null;
 * 
 * try (PreparedStatement stmt = connection.prepareStatement(sql)) {
 * stmt.setString(1, id);
 * ResultSet rs = stmt.executeQuery();
 * 
 * if (rs.next()) {
 * consulta = new Consulta();
 * consulta.setId(rs.getString("id"));
 * consulta.setData(rs.getDate("data"));
 * consulta.setHorario(TimeZone.getTimeZone(rs.getString("horario")));
 * consulta.setTipo(rs.getString("tipo"));
 * consulta.setStatus(rs.getString("status"));
 * consulta.setAnimal(new Animal(rs.getString("animal_id"))); // Supondo que o
 * construtor Animal receba apenas ID
 * consulta.setCliente(new Cliente(rs.getString("cliente_id")));
 * consulta.setVeterinario(new Veterinario(rs.getString("veterinario_id")));
 * }
 * }
 * 
 * return consulta;
 * }
 * 
 * public void atualizarConsulta(Consulta consulta) throws SQLException {
 * String sql =
 * "UPDATE consultas SET data = ?, horario = ?, tipo = ?, status = ?, animal_id = ?, cliente_id = ?, veterinario_id = ? "
 * +
 * "WHERE id = ?";
 * try (PreparedStatement stmt = connection.prepareStatement(sql)) {
 * stmt.setDate(1, new java.sql.Date(consulta.getData().getTime()));
 * stmt.setString(2, consulta.getHorario().getID());
 * stmt.setString(3, consulta.getTipo());
 * stmt.setString(4, consulta.getStatus());
 * stmt.setString(5, consulta.getAnimal().getId());
 * stmt.setString(6, consulta.getCliente().getId());
 * stmt.setString(7, consulta.getVeterinario().getId());
 * stmt.setString(8, consulta.getId());
 * stmt.executeUpdate();
 * }
 * }
 * 
 * public void excluirConsulta(String id) throws SQLException {
 * String sql = "DELETE FROM consultas WHERE id = ?";
 * try (PreparedStatement stmt = connection.prepareStatement(sql)) {
 * stmt.setString(1, id);
 * stmt.executeUpdate();
 * }
 * }
 * 
 * public List<Consulta> listarConsultas() throws SQLException {
 * String sql = "SELECT * FROM consultas";
 * List<Consulta> consultas = new ArrayList<>();
 * 
 * try (PreparedStatement stmt = connection.prepareStatement(sql);
 * ResultSet rs = stmt.executeQuery()) {
 * 
 * while (rs.next()) {
 * Consulta consulta = new Consulta();
 * consulta.setId(rs.getString("id"));
 * consulta.setData(rs.getDate("data"));
 * consulta.setHorario(TimeZone.getTimeZone(rs.getString("horario")));
 * consulta.setTipo(rs.getString("tipo"));
 * consulta.setStatus(rs.getString("status"));
 * consulta.setAnimal(new Animal(rs.getString("animal_id")));
 * consulta.setCliente(new Cliente(rs.getString("cliente_id")));
 * consulta.setVeterinario(new Veterinario(rs.getString("veterinario_id")));
 * consultas.add(consulta);
 * }
 * }
 * 
 * return consultas;
 * }
 * }
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 * package model;
 * 
 * // DAO Implementation for H2 Database.
 * /**
 * 
 * @author Plinio Vilela
 * 
 * @date 16 de Agosto de 2021
 */
/*
 * import java.sql.*;
 * import java.text.SimpleDateFormat;
 * import java.util.logging.Level;
 * import java.util.logging.Logger;
 * 
 * public abstract class DAO {
 * public static final String DBURL = "jdbc:h2:./vet2021.db";
 * private static Connection con;
 * protected static SimpleDateFormat dateFormat = new
 * SimpleDateFormat("dd/MM/yyyy");
 * 
 * // Connect to SQLite
 * public static Connection getConnection() {
 * if (con == null) {
 * try {
 * con = DriverManager.getConnection(DBURL);
 * if (con != null) {
 * DatabaseMetaData meta = con.getMetaData();
 * }
 * } catch (SQLException e) {
 * System.err.println("Exception: " + e.getMessage());
 * }
 * }
 * return con;
 * }
 * 
 * protected ResultSet getResultSet(String query) {
 * Statement s;
 * ResultSet rs = null;
 * try {
 * s = (Statement) con.createStatement();
 * rs = s.executeQuery(query);
 * } catch (SQLException e) {
 * System.err.println("Exception: " + e.getMessage());
 * }
 * return rs;
 * }
 * 
 * protected int executeUpdate(PreparedStatement queryStatement) throws
 * SQLException {
 * int update;
 * update = queryStatement.executeUpdate();
 * return update;
 * }
 * 
 * protected int lastId(String tableName, String primaryKey) {
 * Statement s;
 * int lastId = -1;
 * try {
 * s = (Statement) con.createStatement();
 * ResultSet rs = s.executeQuery("SELECT MAX(" + primaryKey + ") AS id FROM " +
 * tableName);
 * if (rs.next()) {
 * lastId = rs.getInt("id");
 * }
 * } catch (SQLException e) {
 * System.err.println("Exception: " + e.getMessage());
 * }
 * return lastId;
 * }
 * 
 * public static void terminar() {
 * try {
 * (DAO.getConnection()).close();
 * } catch (SQLException e) {
 * System.err.println("Exception: " + e.getMessage());
 * }
 * }
 * 
 * // Create table SQLite
 * protected final boolean createTable() {
 * try {
 * PreparedStatement stmt;
 * // Table client:
 * stmt =
 * DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS cliente( \n"
 * + "id INTEGER PRIMARY KEY AUTO_INCREMENT, \n"
 * + "nome VARCHAR, \n"
 * + "endereco VARCHAR, \n"
 * + "cep VARCHAR, \n"
 * + "email VARCHAR, \n"
 * + "telefone VARCHAR); \n");
 * executeUpdate(stmt);
 * // Table animal:
 * stmt =
 * DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS animal( \n"
 * + "id INTEGER PRIMARY KEY AUTO_INCREMENT, \n"
 * + "nome VARCHAR, \n"
 * + "anoNasc INTEGER, \n"
 * + "sexo VARCHAR, \n"
 * + "id_especie INTEGER, \n"
 * + "id_cliente INTEGER); \n");
 * executeUpdate(stmt);
 * // Table species:
 * stmt =
 * DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS especie( \n"
 * + "id INTEGER PRIMARY KEY AUTO_INCREMENT, \n"
 * + "nome VARCHAR); \n");
 * executeUpdate(stmt);
 * // Table vet:
 * stmt =
 * DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS vet( \n"
 * + "id INTEGER PRIMARY KEY AUTO_INCREMENT, \n"
 * + "nome VARCHAR, \n"
 * + "email VARCHAR, \n"
 * + "telefone VARCHAR); \n");
 * executeUpdate(stmt);
 * // Table appointment:
 * stmt = DAO.getConnection().
 * prepareStatement("CREATE TABLE IF NOT EXISTS consulta( \n"
 * + "id INTEGER PRIMARY KEY AUTO_INCREMENT, \n"
 * + "data DATE, \n"
 * + "horario VARCHAR, \n"
 * + "comentario VARCHAR, \n"
 * + "id_animal INTEGER, \n"
 * + "id_vet INTEGER, \n"
 * + "id_tratamento INTEGER, \n"
 * + "terminado INTEGER); \n");
 * executeUpdate(stmt);
 * // Table exam:
 * stmt =
 * DAO.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS exame( \n"
 * + "id INTEGER PRIMARY KEY AUTO_INCREMENT, \n"
 * + "nome VARCHAR, \n"
 * + "id_consulta INTEGER); \n");
 * executeUpdate(stmt);
 * return true;
 * } catch (SQLException ex) {
 * Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
 * }
 * return false;
 * }
 * 
 * }
 * 
 */