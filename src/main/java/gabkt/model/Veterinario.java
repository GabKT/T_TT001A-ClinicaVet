package gabkt.model;

public class Veterinario extends Pessoa {
    private String crmv;
    private String especialidade;

    public Veterinario() {
        super();
    }

    public Veterinario(String nome, long cpf, long telefone) {
        super(nome, cpf, telefone);
    }

    public Veterinario(String nome, long cpf, long telefone, String crmv, String especialidade) {
        super(nome, cpf, telefone);
        this.crmv = crmv;
        this.especialidade = especialidade;
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

    @Override
    public void atualizarDados() {
    }

    @Override
    public void cadastrar() {
    }

    public void realizarAtendimento() {
    }

    public void prescreverTratamento() {
    }

    public void solicitarExame() {
    }
}
