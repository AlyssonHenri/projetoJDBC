package db.entities.dao;


import db.DB;
import db.entities.dao.impl.contaDaoJDBC;
import db.entities.dao.impl.equipamentoDaoJDBC;

public class daoFactory {
    public static contaDao createContaDao(){
        return new contaDaoJDBC(DB.getConnection());
    }

    public static equipamentoDao createEquipamentoDao(){
        return new equipamentoDaoJDBC(DB.getConnection());
    }
}
