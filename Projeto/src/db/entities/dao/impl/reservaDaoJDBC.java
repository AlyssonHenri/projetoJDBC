package db.entities.dao.impl;

import db.DB;
import db.entities.conta;
import db.entities.dao.reservaDao;
import db.entities.equipamento;
import db.entities.reserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class reservaDaoJDBC implements reservaDao {
    private Connection conn;

    public reservaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void criarReserva(conta c, reserva v, equipamento e) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            // Verifica se já existe uma reserva ativa (status = 1) no mesmo dia e com sobreposição de horário
            st = conn.prepareStatement("select id from reservas where data_reserva = ? and equipamento = ? and status = 1 and " + "((hora_inicio < ? and hora_fim > ?) or (hora_inicio < ? and hora_fim > ?))" );
            java.sql.Date dataAtual = new java.sql.Date(System.currentTimeMillis());
            st.setDate(1, dataAtual);
            st.setInt(2, e.getId());
            st.setTime(3, Time.valueOf(v.getHora_fim()));
            st.setTime(4, Time.valueOf(v.getHora_inicio()));
            st.setTime(5, Time.valueOf(v.getHora_fim()));
            st.setTime(6, Time.valueOf(v.getHora_inicio()));
            rs = st.executeQuery();

            if (rs.next()) {
                throw new RuntimeException("Já existe uma reserva ativa para esse horário.");
            }

            // Se não existe uma reserva ativa, insere a nova reserva
            st = conn.prepareStatement("insert into reservas(data_reserva, hora_inicio, hora_fim, status, conta_cliente, equipamento) values(?, ?, ?, ?, ?, ?)" );
            st.setDate(1, dataAtual);
            st.setTime(2, Time.valueOf(v.getHora_inicio()));
            st.setTime(3, Time.valueOf(v.getHora_fim()));
            st.setInt(4, 1); // Status = 1 (ativo)
            st.setInt(5, c.getId());
            st.setInt(6, e.getId());
            st.executeUpdate();
        } catch (SQLException err) {
            throw new RuntimeException(err);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatment(st);
        }
    }


    @Override
    public void editarReserva(reserva v) {
        PreparedStatement st = null;
        StringBuilder sql = new StringBuilder("UPDATE reservas SET ");
        boolean primeiroCampo = true;

        try {
            if (v.getData_reserva() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("data_reserva = ?");
                primeiroCampo = false;
            }
            if (v.getHora_inicio() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("hora_inicio = ?");
                primeiroCampo = false;
            }
            if (v.getHora_fim() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("hora_fim = ?");
                primeiroCampo = false;
            }

            sql.append(primeiroCampo ? "" : ", ").append("status = ?");


            sql.append(" WHERE id = ?");

            st = conn.prepareStatement(sql.toString());
            int index = 1;

            if (v.getData_reserva() != null) {
                st.setDate(index++, Date.valueOf(v.getData_reserva()));
            }
            if (v.getHora_inicio() != null) {
                st.setTime(index++, Time.valueOf(v.getHora_inicio()));
            }
            if (v.getHora_fim() != null) {
                st.setTime(index++, Time.valueOf(v.getHora_fim()));
            }

            st.setInt(index++, v.getStatus());
            st.setInt(index, v.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DB.closeStatment(st);
        }
    }


    @Override
    public reserva procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("select id, data_reserva, hora_inicio, hora_fim, status from reservas where id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                reserva r = new reserva();
                r.setId(rs.getInt("id"));
                r.setData_reserva(rs.getString("data_reserva"));
                r.setHora_inicio(rs.getString("hora_inicio"));
                r.setHora_fim(rs.getString("hora_fim"));
                r.setStatus(rs.getInt("status"));
                return r;
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatment(st);
        }
        return null;
    }

    @Override
    public List<reserva> listarTodas() {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("select id, data_reserva, hora_inicio, hora_fim, status from reservas");
            rs = st.executeQuery();
            List<reserva> lista = new ArrayList<>();
            while (rs.next()) {
                reserva v = new reserva();
                v.setId(rs.getInt("id"));
                v.setData_reserva(rs.getString("data_reserva"));
                v.setHora_inicio(rs.getString("hora_inicio"));
                v.setHora_fim(rs.getString("hora_fim"));
                v.setStatus(rs.getInt("status"));
                lista.add(v);
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
