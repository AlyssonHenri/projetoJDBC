package com.example.projeto.model.dao.impl;

import com.example.projeto.db.DB;
import com.example.projeto.model.dao.ReservaDAO;
import com.example.projeto.model.entities.Conta;
import com.example.projeto.model.entities.Equipamento;
import com.example.projeto.model.entities.Reserva;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDaoJDBC implements ReservaDAO {
    private Connection conn;

    public ReservaDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    public void criarReserva(Conta c, Reserva v, Equipamento e) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = this.conn.prepareStatement("select id from reservas where data_reserva = ? and equipamento = ? and status = 1 and ((hora_inicio < ? and hora_fim > ?) or (hora_inicio < ? and hora_fim > ?))");
            Date dataAtual = new Date(System.currentTimeMillis());
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

            st = this.conn.prepareStatement("insert into reservas(data_reserva, hora_inicio, hora_fim, status, conta_cliente, equipamento) values(?, ?, ?, ?, ?, ?)");
            st.setDate(1, dataAtual);
            st.setTime(2, Time.valueOf(v.getHora_inicio()));
            st.setTime(3, Time.valueOf(v.getHora_fim()));
            st.setInt(4, 1);
            st.setInt(5, c.getId());
            st.setInt(6, e.getId());
            st.executeUpdate();
        } catch (SQLException var10) {
            throw new RuntimeException(var10);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

    }

    public void editarReserva(Reserva v) {
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
            st = this.conn.prepareStatement(sql.toString());
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
        } catch (SQLException var9) {
            throw new RuntimeException(var9);
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void deletarPorId(Reserva var1) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("delete from reservas where id = ?");
            st.setInt(1,var1.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DB.closeStatement(st);
        }
    }

    public Reserva procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        Reserva var5;
        try {
            st = this.conn.prepareStatement("select id, data_reserva, equipamento, conta_cliente, hora_inicio, hora_fim, status from reservas where id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (!rs.next()) {
                return null;
            }

            Reserva r = new Reserva();
            r.setId(rs.getInt("id"));
            r.setEquipamento(rs.getInt("equipamento"));
            r.setConta_cliente(rs.getInt("conta_cliente"));
            r.setData_reserva(rs.getString("data_reserva"));
            r.setHora_inicio(rs.getString("hora_inicio"));
            r.setHora_fim(rs.getString("hora_fim"));
            r.setStatus(rs.getInt("status"));
            var5 = r;
        } catch (SQLException var9) {
            throw new RuntimeException(var9);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

        return var5;
    }

    public List<Reserva> listarTodas() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = this.conn.prepareStatement("select id, equipamento, conta_cliente, data_reserva, hora_inicio, hora_fim, status from reservas");
            rs = st.executeQuery();
            List<Reserva> lista = new ArrayList();

            while(rs.next()) {
                Reserva v = new Reserva();
                v.setId(rs.getInt("id"));
                v.setEquipamento(rs.getInt("equipamento"));
                v.setConta_cliente(rs.getInt("conta_cliente"));
                v.setData_reserva(rs.getString("data_reserva"));
                v.setHora_inicio(rs.getString("hora_inicio"));
                v.setHora_fim(rs.getString("hora_fim"));
                v.setStatus(rs.getInt("status"));
                lista.add(v);
            }

            ArrayList var10 = (ArrayList) lista;
            return var10;
        } catch (SQLException var8) {
            throw new RuntimeException(var8);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }
}
