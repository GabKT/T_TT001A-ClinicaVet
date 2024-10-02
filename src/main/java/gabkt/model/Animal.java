package gabkt.model;

import java.util.List;

public class Animal {
    private int id;
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private double peso;
    private int dono;
    private List<Prontuario> historicoMedico;

    public Animal() {
    }

    public Animal(int id, String nome, String especie, String raca, int idade, double peso, int dono,
            List<Prontuario> historicoMedico) {
        this.id = id;
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.peso = peso;
        this.dono = dono;
        this.historicoMedico = historicoMedico;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaca() {
        return raca;
    }

    public int getIdade() {
        return idade;
    }

    public double getPeso() {
        return peso;
    }

    public List<Prontuario> getHistoricoMedico() {
        return historicoMedico;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setHistoricoMedico(List<Prontuario> historicoMedico) {
        this.historicoMedico = historicoMedico;
    }

    public void atualizarDados() {
    }

    public void visualizarHistoricoMedico() {
    }

    public int getDono() {
        return dono;
    }

    public void setDono(int dono) {
        this.dono = dono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "\nAnimal [id=" + id + ", nome=" + nome + ", especie=" + especie + ", raca=" + raca + ", idade=" + idade
                + ", peso=" + peso + ", dono=" + dono + "]\n";
    }

}
