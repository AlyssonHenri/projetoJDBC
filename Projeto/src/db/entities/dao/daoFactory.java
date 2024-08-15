package db.entities.dao;


import db.DB;
import db.entities.dao.impl.*;

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

    public static uso_de_equipamentoDao createUsoDeEquipamentoDao() {
        return new uso_de_equipamentoDaoJDBC(DB.getConnection());
    }
}
