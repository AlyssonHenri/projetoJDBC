package com.example.projeto.model.dao;

import com.example.projeto.model.entities.Equipamento;

import java.sql.SQLException;
import java.util.List;

public interface EquipamentoDAO {
    void inserir (Equipamento e);
    void atualizar (Equipamento e, int alt) throws SQLException;
    void deletarPorId (Equipamento e);
    Equipamento procurarPorId (int id);
    List<Equipamento> procurarTodos();
}
