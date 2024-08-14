package db.entities.dao;

import db.entities.conta;
import db.entities.equipamento;

import java.util.ArrayList;
import java.util.List;

public interface equipamentoDao {
    void inserir (equipamento e);
    void atualizar (equipamento e);
    void deletarPorId (equipamento e);
    equipamento procurarPorId (int id);
    List<equipamento> procurarTodos();
}
