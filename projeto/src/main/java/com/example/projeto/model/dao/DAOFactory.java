package com.example.projeto.model.dao;

import com.example.projeto.db.DB;
import com.example.projeto.model.dao.impl.ContaDaoJDBC;
import com.example.projeto.model.dao.impl.EquipamentoDaoJDBC;
import com.example.projeto.model.dao.impl.ReservaDaoJDBC;

public class DAOFactory {
    public static ContaDAO createContaDao(){
        return new ContaDaoJDBC(DB.getConnection());
    }

    public static EquipamentoDAO createEquipamentoDao(){
        return new EquipamentoDaoJDBC(DB.getConnection());
    }

    public static ReservaDAO createReservaDao() {
        return new ReservaDaoJDBC(DB.getConnection());
    }

}
