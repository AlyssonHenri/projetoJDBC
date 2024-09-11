package com.example.projeto.model.entities;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class Equipamento {
    private int id;
    private String nome;
    private byte[] imagem;
    private String tipo;
    private int status_equipamento;
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

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getStatus_equipamento() {
        return status_equipamento;
    }

    public void setStatus_equipamento(int status_equipamento) {
        this.status_equipamento = status_equipamento;
    }

    public Date getData_registro() {
        return data_registro;
    }

    public void setData_registro(Date data_registro) {
        this.data_registro = data_registro;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipamento that = (Equipamento) o;
        return id == that.id && status_equipamento == that.status_equipamento && Objects.equals(nome, that.nome) && Arrays.equals(imagem, that.imagem) && Objects.equals(tipo, that.tipo) && Objects.equals(data_registro, that.data_registro);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, nome, tipo, status_equipamento, data_registro);
        result = 31 * result + Arrays.hashCode(imagem);
        return result;
    }

    @Override
    public String toString() {
        return "Equipamento{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", imagem=" + Arrays.toString(imagem) +
                ", tipo='" + tipo + '\'' +
                ", status_equipamento=" + status_equipamento +
                ", data_registro=" + data_registro +
                '}';
    }
}
