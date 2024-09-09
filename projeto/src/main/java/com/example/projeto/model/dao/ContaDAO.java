package com.example.projeto.model.dao;

import com.example.projeto.model.entities.Conta;

import java.util.List;

public interface ContaDAO {
    void inserirCliente (Conta c);
    void inserirFuncionario (Conta c);
    void atualizar (Conta c);
    void deletarPorId (Conta c);
    Conta procurarPorId(int id);
    Conta login(String login, String senha);
    List<Conta> procurarTodos();
}
