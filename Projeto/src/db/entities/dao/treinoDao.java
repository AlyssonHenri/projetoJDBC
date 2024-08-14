package db.entities.dao;

import db.entities.treino;

import java.util.List;

public interface treinoDao {
    void inserir (treino t);
    void atualizar (treino t);
    void deletarPorId(treino t);
    treino porcurarPorId(int id);
    List<treino> procurarTodos();
}
