package gabkt.model;

import java.util.Date;

public class Prontuario {
    private int id;
    private Date dataConsulta;
    private String diagnostico;
    private String tratamento;
    private String observacoes;
    private int animal;

    public Prontuario() {
    }

    public Prontuario(int id, Date dataConsulta, String diagnostico, String tratamento, String observacoes,
            int animal) {
        this.id = id;
        this.dataConsulta = dataConsulta;
        this.diagnostico = diagnostico;
        this.tratamento = tratamento;
        this.observacoes = observacoes;
        this.animal = animal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(Date dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamento() {
        return tratamento;
    }

    public void setTratamento(String tratamento) {
        this.tratamento = tratamento;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public void adicionarEntrada() {
    }

    public void visualizarProntuario() {
    }

    public int getAnimal() {
        return animal;
    }

    public void setAnimal(int animal) {
        this.animal = animal;
    }

    @Override
    public String toString() {
        return "\nProntuario [id=" + id + ", dataConsulta=" + dataConsulta + ", diagnostico=" + diagnostico
                + ", tratamento=" + tratamento + ", observacoes=" + observacoes + ", animal=" + animal + "]\n";
    }

}
