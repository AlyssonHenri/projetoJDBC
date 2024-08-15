package db.entities.dao;

import db.entities.treino;

import java.util.List;

public interface treinoDao {
    void inserir (treino t, int c);
    void atualizar (treino t);
    void deletarPorId(treino t);
    treino procurarPorId(int id);
    List<treino> procurarTodos(int id);
}
