package db.entities;

import java.util.Objects;

public class treino {
    private int id;
    private String nome;
    private String duracao;
    private String descricao;
    private int conta_cliente;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDuracao() {
        return duracao;
    }

    public void setDuracao(String duracao) {
        this.duracao = duracao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getConta_cliente() {
        return conta_cliente;
    }

    public void setConta_cliente(int conta_cliente) {
        this.conta_cliente = conta_cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        treino treino = (treino) o;
        return id == treino.id && conta_cliente == treino.conta_cliente && Objects.equals(nome, treino.nome) && Objects.equals(duracao, treino.duracao) && Objects.equals(descricao, treino.descricao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, duracao, descricao, conta_cliente);
    }

    @Override
    public String toString() {
        return "treino{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", duracao='" + duracao + '\'' +
                ", descricao='" + descricao + '\'' +
                ", conta_cliente=" + conta_cliente +
                '}';
    }
}
