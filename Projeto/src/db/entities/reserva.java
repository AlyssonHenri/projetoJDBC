package db.entities;

import java.util.Objects;

public class reserva {
    private int id;
    private String hora_inicio;
    private String hora_fim;
    private String data_reserva;
    private int status;
    private int conta_cliente;
    private int equipamento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fim() {
        return hora_fim;
    }

    public void setHora_fim(String hora_fim) {
        this.hora_fim = hora_fim;
    }

    public String getData_reserva() {
        return data_reserva;
    }

    public void setData_reserva(String data_reserva) {
        this.data_reserva = data_reserva;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getConta_cliente() {
        return conta_cliente;
    }

    public void setConta_cliente(int conta_cliente) {
        this.conta_cliente = conta_cliente;
    }

    public int getEquipamento() {
        return equipamento;
    }

    public void setEquipamento(int equipamento) {
        this.equipamento = equipamento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        reserva reserva = (reserva) o;
        return id == reserva.id && status == reserva.status && conta_cliente == reserva.conta_cliente && equipamento == reserva.equipamento && Objects.equals(hora_inicio, reserva.hora_inicio) && Objects.equals(hora_fim, reserva.hora_fim) && Objects.equals(data_reserva, reserva.data_reserva);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hora_inicio, hora_fim, data_reserva, status, conta_cliente, equipamento);
    }

    @Override
    public String toString() {
        return "reserva{" +
                "id=" + id +
                ", hora_inicio='" + hora_inicio + '\'' +
                ", hora_fim='" + hora_fim + '\'' +
                ", data_reserva='" + data_reserva + '\'' +
                ", status=" + status +
                ", conta_cliente=" + conta_cliente +
                ", equipamento=" + equipamento +
                '}';
    }
}
