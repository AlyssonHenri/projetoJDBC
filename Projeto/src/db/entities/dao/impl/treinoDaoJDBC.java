package db.entities.dao.impl;

import db.DB;
import db.entities.treino;
import db.entities.dao.treinoDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class treinoDaoJDBC implements treinoDao {
    private Connection conn;

    public treinoDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void inserir(treino t, int c) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("insert into treino(nome, descricao, conta_cliente) values (?, ?, ?)" );
            st.setString(1, t.getNome());
            st.setString(2, t.getDescricao());
            st.setInt(3, c);
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public void atualizar(treino t) {
        PreparedStatement st = null;
        StringBuilder sql = new StringBuilder("update treino set ");
        boolean primeiroCampo = true;

        try {
            if (t.getNome() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("nome = ?");
                primeiroCampo = false;
            }
            if (t.getDuracao() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("duracao = ?");
                primeiroCampo = false;
            }

            sql.append(" where id = ?");

            st = conn.prepareStatement(sql.toString());
            int index = 1;

            if (t.getNome() != null) {
                st.setString(index++, t.getNome());
            }
            if (t.getDuracao() != null) {
                st.setInt(index++, Integer.parseInt(t.getDuracao()));
            }

            st.setInt(index, t.getId());

            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public void deletarPorId(treino t) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("delete from treino where id = ?");
            st.setInt(1, t.getId());
            st.executeUpdate();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public treino procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("select id, nome, duracao from treino where id = ?" );
            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                treino t = new treino();
                t.setId(rs.getInt("id"));
                t.setNome(rs.getString("nome"));
                t.setDuracao(String.valueOf(rs.getInt("duracao")));
                return t;
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
    public List<treino> procurarTodos(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement("select id, nome, duracao from treino were id = ?" );
            st.setInt(1, id);
            rs = st.executeQuery();
            List<treino> lista = new ArrayList<>();
            while (rs.next()) {
                treino t = new treino();
                t.setId(rs.getInt("id"));
                t.setNome(rs.getString("nome"));
                t.setDuracao(String.valueOf(rs.getInt("duracao")));
                lista.add(t);
            }
            return lista;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatment(st);
        }
    }
}
