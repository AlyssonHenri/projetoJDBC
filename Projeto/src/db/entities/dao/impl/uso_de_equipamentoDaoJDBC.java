package db.entities.dao.impl;

import db.DB;
import db.entities.dao.uso_de_equipamentoDao;
import db.entities.equipamento;
import db.entities.reserva;
import db.entities.uso_de_equipamento;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class uso_de_equipamentoDaoJDBC  implements uso_de_equipamentoDao {
    private Connection conn;

    public uso_de_equipamentoDaoJDBC(Connection conn) {
        this.conn = conn;
    }
    @Override
    public void inserir(int treino, int equipamento, uso_de_equipamento u) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("insert into uso_de_equipamento(tempo_de_uso,treino,equipamento) values (?,?,?)");
            st.setString(1, u.getTempo_de_uso());
            st.setInt(2, treino);
            st.setInt(3, equipamento);
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public void remover(int treino, int equipamento) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("delete from uso_de_equipamento where treino = ? and where equipamento = ?");
            st.setInt(1,treino);
            st.setInt(2,equipamento);
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public void atualizar(int treino, int equipamento, uso_de_equipamento e) {
        PreparedStatement st = null;

        try {
            String sql = "UPDATE uso_de_equipamento SET tempo_de_uso = ? WHERE treino = ? AND equipamento = ?";
            st = conn.prepareStatement(sql);
            st.setString(1, e.getTempo_de_uso());
            st.setInt(2, treino);
            st.setInt(3, equipamento);
            int linhasAfetadas = st.executeUpdate();

            if (linhasAfetadas == 0) {
                System.out.println("Nenhuma atualização foi feita. Verifique os IDs fornecidos.");
            } else {
                System.out.println("Atualização realizada com sucesso.");
            }
        } catch (SQLException err) {
            throw new RuntimeException("Erro ao atualizar o uso do equipamento: " + err.getMessage(), err);
        } finally {
            DB.closeStatment(st);
        }
    }


    @Override
    public uso_de_equipamento procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("select tempo_de_uso from uso_de_equipamento where id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                uso_de_equipamento r = new uso_de_equipamento();
                r.setTempo_de_uso(rs.getString("id"));
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
    public List<equipamento> listar(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                    "SELECT e.id, e.nome, e.tipo, e.status_equipamento " +
                            "FROM equipamento e " +
                            "JOIN uso_de_equipamento u ON e.id = u.equipamento " +
                            "WHERE u.treino = ?;"
            );
            st.setInt(1, id); // Configurando o ID do treino
            rs = st.executeQuery();
            List<equipamento> lista = new ArrayList<>();
            while (rs.next()) {
                equipamento e = new equipamento();
                e.setId(rs.getInt("id"));
                e.setNome(rs.getString("nome"));
                e.setTipo(rs.getString("tipo"));
                e.setStatus(rs.getBoolean("status_equipamento"));
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

