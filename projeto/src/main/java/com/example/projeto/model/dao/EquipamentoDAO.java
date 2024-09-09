package com.example.projeto.model.dao;

import com.example.projeto.model.entities.Equipamento;

import java.util.List;

public interface EquipamentoDAO {
    void inserir (Equipamento e);
    void atualizar (Equipamento e);
    void deletarPorId (Equipamento e);
    Equipamento procurarPorId (int id);
    List<Equipamento> procurarTodos();
}
