package gabkt.model;

import java.util.Date;
import java.util.TimeZone;

public class Consulta {
    private String id;
    private Date data;
    private TimeZone horario;
    private String tipo;
    private String status;

    public Consulta() {
    }

    public Consulta(String id, Date data, TimeZone horario, String tipo, String status) {
        this.id = id;
        this.data = data;
        this.horario = horario;
        this.tipo = tipo;
        this.status = status;
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
}
