package com.example.projeto.model.dao;

import com.example.projeto.model.entities.Conta;
import com.example.projeto.model.entities.Equipamento;
import com.example.projeto.model.entities.Reserva;

import java.sql.SQLException;
import java.util.List;

public interface ReservaDAO {
    void criarReserva(Conta var1, Reserva var2, Equipamento var3);
    void editarReserva(Reserva var1) throws SQLException;
    void deletarPorId (Reserva var1);
    Reserva procurarPorId(int var1);
    List<Reserva> listarTodas();

    List<Reserva> listarConta(Conta c);
}
