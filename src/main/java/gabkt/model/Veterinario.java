package gabkt.model;

public class Veterinario extends Pessoa {
    private String crmv;
    private String especialidade;

    public Veterinario() {
        super();
    }

    public Veterinario(int id, String nome, long cpf, long telefone, String crmv, String especialidade) {
        super(id, nome, cpf, telefone);
        this.crmv = crmv;
        this.especialidade = especialidade;
    }

    public Veterinario(int id, String nome, long cpf, long telefone) {
        super(id, nome, cpf, telefone);
    }

    public String getCrmv() {
        return crmv;
    }

    public void setCrmv(String crmv) {
        this.crmv = crmv;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public void realizarAtendimento() {
    }

    public void prescreverTratamento() {
    }

    public void solicitarExame() {
    }

    @Override
    public String toString() {
        return "\nVeterinario [crmv=" + crmv + ", especialidade=" + especialidade + ", getNome()=" + getNome()
                + ", getCpf()=" + getCpf() + ", getTelefone()=" + getTelefone() + ", getId()=" + getId() + "]\n";
    }

}
