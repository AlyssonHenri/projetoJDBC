package com.example.projeto.model.entities;


import java.sql.Date;
import java.util.Arrays;
import java.util.Objects;

public class Conta {
    private int id;
    private byte[] foto;
    private String nome;
    private String telefone;
    private String e_mail;
    private String cpf;
    private String endereco;
    private String login;
    private String senha;
    private String tipo_conta;
    private Date data_registro;
    private float salario_funcionario;
    private String inicio_expediente_funcionario;
    private String fim_expediente_funcionario;
    private float mensalidade_cliente;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo_conta() {
        return tipo_conta;
    }

    public void setTipo_conta(String tipo_conta) {
        this.tipo_conta = tipo_conta;
    }

    public Date getData_registro() {
        return data_registro;
    }

    public void setData_registro(Date data_registro) {
        this.data_registro = data_registro;
    }

    public float getSalario_funcionario() {
        return salario_funcionario;
    }

    public void setSalario_funcionario(float salario_funcionario) {
        this.salario_funcionario = salario_funcionario;
    }

    public String getInicio_expediente_funcionario() {
        return inicio_expediente_funcionario;
    }

    public void setInicio_expediente_funcionario(String inicio_expediente_funcionario) {
        this.inicio_expediente_funcionario = inicio_expediente_funcionario;
    }

    public String getFim_expediente_funcionario() {
        return fim_expediente_funcionario;
    }

    public void setFim_expediente_funcionario(String fim_expediente_funcionario) {
        this.fim_expediente_funcionario = fim_expediente_funcionario;
    }

    public float getMensalidade_cliente() {
        return mensalidade_cliente;
    }

    public void setMensalidade_cliente(float mensalidade_cliente) {
        this.mensalidade_cliente = mensalidade_cliente;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return id == conta.id && Float.compare(salario_funcionario, conta.salario_funcionario) == 0 && Float.compare(mensalidade_cliente, conta.mensalidade_cliente) == 0 && Arrays.equals(foto, conta.foto) && Objects.equals(nome, conta.nome) && Objects.equals(telefone, conta.telefone) && Objects.equals(e_mail, conta.e_mail) && Objects.equals(cpf, conta.cpf) && Objects.equals(endereco, conta.endereco) && Objects.equals(login, conta.login) && Objects.equals(senha, conta.senha) && Objects.equals(tipo_conta, conta.tipo_conta) && Objects.equals(data_registro, conta.data_registro) && Objects.equals(inicio_expediente_funcionario, conta.inicio_expediente_funcionario) && Objects.equals(fim_expediente_funcionario, conta.fim_expediente_funcionario);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, nome, telefone, e_mail, cpf, endereco, login, senha, tipo_conta, data_registro, salario_funcionario, inicio_expediente_funcionario, fim_expediente_funcionario, mensalidade_cliente);
        result = 31 * result + Arrays.hashCode(foto);
        return result;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id=" + id +
                ", foto=" + Arrays.toString(foto) +
                ", nome='" + nome + '\'' +
                ", telefone='" + telefone + '\'' +
                ", e_mail='" + e_mail + '\'' +
                ", cpf='" + cpf + '\'' +
                ", endereco='" + endereco + '\'' +
                ", login='" + login + '\'' +
                ", senha='" + senha + '\'' +
                ", tipo_conta='" + tipo_conta + '\'' +
                ", data_registro=" + data_registro +
                ", salario_funcionario=" + salario_funcionario +
                ", inicio_expediente_funcionario='" + inicio_expediente_funcionario + '\'' +
                ", fim_expediente_funcionario='" + fim_expediente_funcionario + '\'' +
                ", mensalidade_cliente=" + mensalidade_cliente +
                '}';
    }
}