package gabkt.model;

import java.util.Date;

public class Exame {
    private int id;
    private String tipo;
    private Date data;
    private String resultado;

    public Exame() {
    }

    public Exame(int id, String tipo, Date data, String resultado) {
        this.id = id;
        this.tipo = tipo;
        this.data = data;
        this.resultado = resultado;
    }

    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public Date getData() {
        return data;
    }

    public String getResultado() {
        return resultado;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public void agendarExame() {
    }

    public void registrarResultado() {
    }
}
