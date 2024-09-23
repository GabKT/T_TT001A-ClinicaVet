package gabkt.model;

public class Cliente extends Pessoa {
    private String endereco;
    private String email;

    public Cliente() {
        super();
    }

    public Cliente(String nome, long cpf, long telefone) {
        super(nome, cpf, telefone);
    }

    public Cliente(String nome, long cpf, long telefone, String endereco, String email) {
        super(nome, cpf, telefone);
        this.endereco = endereco;
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void cadastrar() {
    }

    @Override
    public void atualizarDados() {
    }

    public void visualizarInformacaoPet() {
    }
}
