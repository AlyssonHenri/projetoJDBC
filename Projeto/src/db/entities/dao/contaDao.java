package db.entities.dao;

import db.entities.conta;

import java.util.List;

public interface contaDao {
    void inserirCliente (conta c);
    void inserirFuncionario (conta c);
    void atualizar (conta c);
    void deletarPorId (conta c);
    conta procurarPorId(int id);
    conta login(String login, String senha);
    List<conta> procurarTodos();
}
