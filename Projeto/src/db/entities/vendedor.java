package db.entities;

import java.util.Date;
import java.util.Objects;

public class vendedor {
    private int id;
    private String nome;
    private String email;
    private Date dataNascimento;
    private double salario;
    private int idDepartamento;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        vendedor vendedor = (vendedor) o;
        return getId() == vendedor.getId() && Double.compare(vendedor.getSalario(), getSalario()) == 0 && getIdDepartamento() == vendedor.getIdDepartamento() && Objects.equals(getNome(), vendedor.getNome()) && Objects.equals(getEmail(), vendedor.getEmail()) && Objects.equals(getDataNascimento(), vendedor.getDataNascimento());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getEmail(), getDataNascimento(), getSalario(), getIdDepartamento());
    }

    @Override
    public String toString() {
        return "db.entities.vendedor{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", salario=" + salario +
                ", idDepartamento=" + idDepartamento +
                '}';
    }
}
