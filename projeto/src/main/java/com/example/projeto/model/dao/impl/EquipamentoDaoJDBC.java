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
            st = conn.prepareStatement("insert into equipamento(nome,tipo,imagem,status_equipamento,data_registro) values (?,?,?,?,?)");
            st.setString(1, e.getNome());
            st.setString(2, e.getTipo());
            st.setBytes(3, e.getImagem());
            st.setString(4, String.valueOf(e.getStatus_equipamento()));
            st.setDate(5, (Date) e.getData_registro());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void atualizar(Equipamento e) {
        PreparedStatement st = null;
        StringBuilder sql = new StringBuilder("update equipamento set ");
        boolean primeiroCampo = true;

        try {
            if (e.getNome() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("nome = ?");
                primeiroCampo = false;
            }
            if (e.getTipo() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("tipo = ?");
                primeiroCampo = false;
            }

            sql.append(primeiroCampo ? "" : ", ").append("status_equipamento = ?");

            sql.append(" where id = ?");

            st = conn.prepareStatement(sql.toString());
            int index = 1;

            if (e.getNome() != null) {
                st.setString(index++, e.getNome());
            }
            if (e.getTipo() != null) {
                st.setString(index++, e.getTipo());
            }

            st.setInt(index, e.getId());

            st.executeUpdate();
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } finally {
            DB.closeStatement(st);
        }
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
