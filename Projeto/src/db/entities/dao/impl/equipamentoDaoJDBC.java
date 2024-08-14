package db.entities.dao.impl;

import db.DB;
import db.entities.conta;
import db.entities.dao.equipamentoDao;
import db.entities.equipamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class equipamentoDaoJDBC implements equipamentoDao {
    private Connection conn;
    public equipamentoDaoJDBC(Connection conn) { this.conn = conn; }

    @Override
    public void inserir(equipamento e) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("insert into equipamento(nome,tipo,status_equipamento,data_registro) values (?,?,?,?)");
            st.setString(1, e.getNome());
            st.setString(2, e.getTipo());
            st.setString(3, String.valueOf(e.getStatus()));
            st.setString(4, String.valueOf(new Date(System.currentTimeMillis())));
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public void atualizar(equipamento e) {
        PreparedStatement st = null;
        StringBuilder sql = new StringBuilder("UPDATE equipamento SET ");
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

            sql.append(" WHERE id = ?");

            st = conn.prepareStatement(sql.toString());
            int index = 1;

            if (e.getNome() != null) {
                st.setString(index++, e.getNome());
            }
            if (e.getTipo() != null) {
                st.setString(index++, e.getTipo());
            }

            st.setInt(index++, e.getStatus());
            st.setInt(index, e.getId());

            st.executeUpdate();
        } catch (SQLException erro) {
            throw new RuntimeException(erro);
        } finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public void deletarPorId(equipamento e) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("delete from equipamento where id = ?");
            st.setInt(1,e.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DB.closeStatment(st);
        }

    }

    @Override
    public equipamento procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("select id, nome, tipo, status_equipamento, data_registro from equipamento where id = ?");
            st.setInt(1,id);
            rs = st.executeQuery();
            if(rs.next()){
                equipamento e;
                e = new equipamento();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));
                e.setTipo(rs.getString("tipo"));
                e.setStatus(rs.getBoolean("status_equipamento"));
                e.setData_registro(rs.getDate("data_registro"));
                return e;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatment(st);
        }
        return null;
    }

    @Override
    public List<equipamento> procurarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("select id, nome, tipo, status_equipamento, data_registro from equipamento where id = ?");
            rs = st.executeQuery();
            List<equipamento> lista = new ArrayList<>();
            while(rs.next()){
                equipamento e;
                e = new equipamento();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));
                e.setTipo(rs.getString("tipo"));
                e.setStatus(rs.getBoolean("status_equipamento"));
                e.setData_registro(rs.getDate("data_registro"));
                lista.add(e);
            }
            return lista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatment(st);
        }
    }
}