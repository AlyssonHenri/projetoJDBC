package com.example.projeto.model.dao.impl;

import com.example.projeto.db.DB;
import com.example.projeto.model.dao.EquipamentoDAO;
import com.example.projeto.model.entities.Equipamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipamentoDaoJDBC implements EquipamentoDAO {
    private Connection conn;
    public EquipamentoDaoJDBC(Connection conn) { this.conn = conn; }

    @Override
    public void inserir(Equipamento e) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("insert into equipamento(nome,tipo,status_equipamento,data_registro) values (?,?,?,?)");
            st.setString(1, e.getNome());
            st.setString(2, e.getTipo());
            st.setString(3, String.valueOf(e.getStatus_equipamento()));
            st.setDate(4, (Date) e.getData_registro());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void atualizar(Equipamento e, int alt) throws SQLException {
        PreparedStatement st = null;

        if(alt != 1){
            String sql = "update equipamento set nome = ?, tipo = ? where id = ?";

            List<Object> parametros = new ArrayList<>();

            parametros.add(e.getNome());
            parametros.add(e.getTipo());
            parametros.add(e.getId());

            st = conn.prepareStatement(sql);

            for (int i = 0; i < parametros.size(); i++) {
                st.setObject(i + 1, parametros.get(i));
            }

        }else{
            String sql = "update equipamento set status_equipamento = ? where id = ?";

            List<Object> parametros = new ArrayList<>();

            parametros.add(e.getStatus_equipamento());
            parametros.add(e.getId());

            st = conn.prepareStatement(sql);

            for (int i = 0; i < parametros.size(); i++) {
                st.setObject(i + 1, parametros.get(i));
            }
        }
        st.executeUpdate();
        DB.closeStatement(st);

    }

    @Override
    public void deletarPorId(Equipamento e) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("delete from equipamento where id = ?");
            st.setInt(1,e.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Equipamento procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("select id, nome, tipo, status_equipamento, data_registro from equipamento where id = ?");
            st.setInt(1,id);
            rs = st.executeQuery();
            if(rs.next()){
                Equipamento e;
                e = new Equipamento();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));
                e.setTipo(rs.getString("tipo"));
                e.setData_registro(rs.getDate("data_registro"));
                return e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
        return null;
    }

    @Override
    public List<Equipamento> procurarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("select id, nome, tipo, status_equipamento, data_registro from equipamento");
            rs = st.executeQuery();
            List<Equipamento> lista = new ArrayList<>();
            while(rs.next()){
                Equipamento e = new Equipamento();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));
                e.setStatus_equipamento(rs.getByte("status_equipamento"));
                e.setTipo(rs.getString("tipo"));
                e.setData_registro(rs.getDate("data_registro"));
                lista.add(e);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }
}
