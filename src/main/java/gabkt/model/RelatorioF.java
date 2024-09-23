package gabkt.model;

import java.util.Date;

public class RelatorioF {
    private String id;
    private Date periodo;
    private Double valorTotal;

    public RelatorioF() {
    }

    public RelatorioF(String id, Date periodo, Double valorTotal) {
        this.id = id;
        this.periodo = periodo;
        this.valorTotal = valorTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Date periodo) {
        this.periodo = periodo;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void gerarRelatorio() {
    }

    public void visualizarRelatorio() {
    }

}
