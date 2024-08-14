package db.entities;

import java.util.Date;
import java.util.Objects;

public class equipamento {
    private int id;
    private String nome;
    private String tipo;
    private int status_equipamento; // Changed to int
    private Date data_registro;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getStatus() { // Changed return type to int
        return status_equipamento;
    }

    public void setStatus(boolean status) { // Set method converts boolean to int
        this.status_equipamento = status ? 1 : 0;
    }

    public Date getData_registro() {
        return data_registro;
    }

    public void setData_registro(Date data_criacao) {
        this.data_registro = data_criacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        equipamento that = (equipamento) o;
        return id == that.id && status_equipamento == that.status_equipamento && Objects.equals(nome, that.nome) && Objects.equals(tipo, that.tipo) && Objects.equals(data_registro, that.data_registro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, tipo, status_equipamento, data_registro);
    }

    @Override
    public String toString() {
        return "equipamento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", tipo='" + tipo + '\'' +
                ", status=" + status_equipamento +
                ", data_criacao=" + data_registro +
                '}';
    }
}
