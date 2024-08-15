package db.entities.dao;


import db.DB;
import db.entities.dao.impl.contaDaoJDBC;
import db.entities.dao.impl.equipamentoDaoJDBC;
import db.entities.dao.impl.reservaDaoJDBC;
import db.entities.dao.impl.treinoDaoJDBC;

public class daoFactory {
    public static contaDao createContaDao(){
        return new contaDaoJDBC(DB.getConnection());
    }

    public static equipamentoDao createEquipamentoDao(){
        return new equipamentoDaoJDBC(DB.getConnection());
    }

    public static treinoDao createTreinoDao() {
        return new treinoDaoJDBC(DB.getConnection());
    }

    public static reservaDao createReservaDao() {
        return new reservaDaoJDBC(DB.getConnection());
    }
}
