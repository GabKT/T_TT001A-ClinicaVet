package gabkt.model;

import java.sql.Time;
import java.util.Date;

public class Consulta {
    private int id;
    private Date data;
    private Time horario;
    private String tipo;
    private String status;
    private int animal;
    private int cliente;
    private int veterinario;

    public Consulta() {
    }

    public Consulta(Date data, Time horario, String tipo, String status, int animal, int cliente,
            int veterinario) {
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.status = status;
        this.animal = animal;
        this.cliente = cliente;
        this.veterinario = veterinario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Time getHorario() {
        return horario;
    }

    public void setHorario(Time horario) {
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

    public int getAnimal() {
        return animal;
    }

    public void setAnimal(int animal) {
        this.animal = animal;
    }

    public int getCliente() {
        return cliente;
    }

    public void setCliente(int cliente) {
        this.cliente = cliente;
    }

    public int getVeterinario() {
        return veterinario;
    }

    public void setVeterinario(int veterinario) {
        this.veterinario = veterinario;
    }

    @Override
    public String toString() {
        return "\nConsulta [id=" + id + ", data=" + data + ", horario=" + horario + ", tipo=" + tipo + ", status="
                + status + ", animal=" + animal + ", cliente=" + cliente + ", veterinario=" + veterinario + "]\n";
    }

}

/*
 * 
 * 
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