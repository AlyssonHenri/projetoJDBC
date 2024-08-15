package db.entities.dao;

import db.entities.conta;
import db.entities.equipamento;
import db.entities.reserva;

import java.util.List;

public interface reservaDao {
    void criarReserva (conta c, reserva v, equipamento e);
    void editarReserva (reserva v);
    reserva procurarPorId (int id);
    List<reserva> listarTodas();
}
