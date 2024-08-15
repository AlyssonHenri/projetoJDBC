package db.entities.dao;

import db.entities.equipamento;
import db.entities.treino;
import db.entities.uso_de_equipamento;

import java.util.List;

public interface uso_de_equipamentoDao {
    void inserir (int treino, int equipamento, uso_de_equipamento u);
    void remover (int treino, int equipamento);
    void atualizar (int treino, int equipamento, uso_de_equipamento e);
    uso_de_equipamento procurarPorId(int id);
    List<equipamento> listar (int id);
}
