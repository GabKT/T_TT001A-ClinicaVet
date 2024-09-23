package gabkt.model;

public abstract class Pessoa {
    private String nome;
    private long cpf;
    private long telefone;

    public Pessoa() {
    }

    public Pessoa(String nome, long cpf, long telefone) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public long getCpf() {
        return cpf;
    }

    public long getTelefone() {
        return telefone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(long telefone) {
        this.telefone = telefone;
    }

    public abstract void cadastrar();

    public abstract void atualizarDados();
}
