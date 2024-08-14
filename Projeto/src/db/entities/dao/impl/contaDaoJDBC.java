package db.entities.dao.impl;

import db.DB;
import db.entities.conta;
import db.entities.dao.contaDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class contaDaoJDBC implements contaDao {
    private Connection conn = null;

    public contaDaoJDBC(Connection conn){this.conn = conn;}

    @Override
    public void inserirCliente(conta c) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("insert into conta(nome,telefone,e_mail,cpf,endereco,login,senha,tipo_conta,data_registro,mensalidade_cliente) values(?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, c.getNome());
            st.setString(2,c.getTelefone());
            st.setString(3,c.getE_mail());
            st.setString(4,c.getCpf());
            st.setString(5,c.getEndereco());
            st.setString(6,c.getLogin());
            st.setString(7,c.getSenha());
            st.setString(8,c.getTipo_conta());
            st.setDate(9, (Date) c.getData_registro());
            st.setFloat(10,c.getMensalidade_cliente());
            int id = st.executeUpdate();
            if(id > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    c.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public void inserirFuncionario(conta c) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("insert into conta(nome,telefone,e_mail,cpf,endereco,login,senha,tipo_conta,data_registro,salario_funcionario,inicio_expediente_funcionario,fim_expediente_funcionario) values(?,?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, c.getNome());
            st.setString(2,c.getTelefone());
            st.setString(3,c.getE_mail());
            st.setString(4,c.getCpf());
            st.setString(5,c.getEndereco());
            st.setString(6,c.getLogin());
            st.setString(7,c.getSenha());
            st.setString(8,c.getTipo_conta());
            st.setDate(9, (Date) c.getData_registro());
            st.setFloat(10,c.getSalario_funcionario());
            st.setTime(11, Time.valueOf(c.getInicio_expediente_funcionario()));
            st.setTime(12, Time.valueOf(c.getFim_expediente_funcionario()));
            int id = st.executeUpdate();
            if (id > 0){
                ResultSet rs = st.getGeneratedKeys();
                if(rs.next()){
                    c.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public void atualizar(conta c) {
        PreparedStatement st = null;
        StringBuilder sql = new StringBuilder("UPDATE conta SET ");
        boolean primeiroCampo = true;

        try {
            if (c.getNome() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("nome = ?");
                primeiroCampo = false;
            }
            if (c.getTelefone() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("telefone = ?");
                primeiroCampo = false;
            }
            if (c.getE_mail() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("e_mail = ?");
                primeiroCampo = false;
            }
            if (c.getCpf() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("cpf = ?");
                primeiroCampo = false;
            }
            if (c.getEndereco() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("endereco = ?");
                primeiroCampo = false;
            }
            if (c.getLogin() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("login = ?");
                primeiroCampo = false;
            }
            if (c.getSenha() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("senha = ?");
                primeiroCampo = false;
            }
            if (c.getTipo_conta() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("tipo_conta = ?");
                primeiroCampo = false;
            }
            if (c.getData_registro() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("data_registro = ?");
                primeiroCampo = false;
            }
            if (c.getSalario_funcionario() != 0.0) {
                sql.append(primeiroCampo ? "" : ", ").append("salario_funcionario = ?");
                primeiroCampo = false;
            }
            if (c.getInicio_expediente_funcionario() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("inicio_expediente_funcionario = ?");
                primeiroCampo = false;
            }
            if (c.getFim_expediente_funcionario() != null) {
                sql.append(primeiroCampo ? "" : ", ").append("fim_expediente_funcionario = ?");
                primeiroCampo = false;
            }
            if (c.getMensalidade_cliente() != 0.0) {
                sql.append(primeiroCampo ? "" : ", ").append("mensalidade_cliente = ?");
                primeiroCampo = false;
            }

            sql.append(" WHERE id = ?");

            st = conn.prepareStatement(sql.toString());

            int index = 1;

            if (c.getNome() != null) {
                st.setString(index++, c.getNome());
            }
            if (c.getTelefone() != null) {
                st.setString(index++, c.getTelefone());
            }
            if (c.getE_mail() != null) {
                st.setString(index++, c.getE_mail());
            }
            if (c.getCpf() != null) {
                st.setString(index++, c.getCpf());
            }
            if (c.getEndereco() != null) {
                st.setString(index++, c.getEndereco());
            }
            if (c.getLogin() != null) {
                st.setString(index++, c.getLogin());
            }
            if (c.getSenha() != null) {
                st.setString(index++, c.getSenha());
            }
            if (c.getTipo_conta() != null) {
                st.setString(index++, c.getTipo_conta());
            }
            if (c.getData_registro() != null) {
                st.setDate(index++, new java.sql.Date(c.getData_registro().getTime()));
            }
            if (c.getSalario_funcionario() != 0.0) {
                st.setFloat(index++, c.getSalario_funcionario());
            }
            if (c.getInicio_expediente_funcionario() != null) {
                st.setTime(index++, Time.valueOf(c.getInicio_expediente_funcionario()));
            }
            if (c.getFim_expediente_funcionario() != null) {
                st.setTime(index++, Time.valueOf(c.getFim_expediente_funcionario()));
            }
            if (c.getMensalidade_cliente() != 0.0) {
                st.setFloat(index++, c.getMensalidade_cliente());
            }

            st.setInt(index, c.getId());

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatment(st);
        }
    }


    @Override
    public void deletarPorId(conta c) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("delete from conta where id = ?");
            st.setInt(1,c.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatment(st);
        }

    }

    @Override
    public conta procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select id,nome,tipo_conta,telefone,e_mail,cpf,endereco,data_registro,inicio_expediente_funcionario,fim_expediente_funcionario from conta where ID = ?");
            st.setInt(1,id);
            rs = st.executeQuery();
            if(rs.next()){
                conta c;
                c = new conta();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTipo_conta(rs.getString("tipo_conta"));
                c.setTelefone(rs.getString("telefone"));
                c.setE_mail(rs.getString("e_mail"));
                c.setCpf(rs.getString("cpf"));
                c.setEndereco(rs.getString("endereco"));
                c.setData_registro(rs.getDate("data_registro"));
                c.setInicio_expediente_funcionario(String.valueOf(rs.getTime("inicio_expediente_funcionario")));
                c.setFim_expediente_funcionario(String.valueOf(rs.getTime("fim_expediente_funcionario")));
                System.out.println(c.toString());
                return c;
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
    public conta login(String login, String senha) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "SELECT id, nome, telefone, e_mail, cpf, endereco, login, senha, tipo_conta, data_registro, mensalidade_cliente, salario_funcionario, inicio_expediente_funcionario, fim_expediente_funcionario " +
                            "FROM conta WHERE login = ? AND senha = ?"
            );
            st.setString(1, login);
            st.setString(2, senha);
            rs = st.executeQuery();

            if (rs.next()) {
                conta c = new conta();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setE_mail(rs.getString("e_mail"));
                c.setCpf(rs.getString("cpf"));
                c.setEndereco(rs.getString("endereco"));
                c.setLogin(rs.getString("login"));
                c.setSenha(rs.getString("senha"));
                c.setTipo_conta(rs.getString("tipo_conta"));
                c.setData_registro(rs.getDate("data_registro"));
                c.setMensalidade_cliente(rs.getFloat("mensalidade_cliente"));
                c.setSalario_funcionario(rs.getFloat("salario_funcionario"));
                c.setInicio_expediente_funcionario(rs.getString("inicio_expediente_funcionario"));
                c.setFim_expediente_funcionario(rs.getString("fim_expediente_funcionario"));
                return c;
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
    public List<conta> procurarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select id,nome,telefone,e_mail,cpf,endereco from conta");
            rs = st.executeQuery();
            List<conta> lista = new ArrayList<>();
            while (rs.next()){
                conta c = new conta();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setTelefone(rs.getString("telefone"));
                c.setE_mail(rs.getString("e_mail"));
                c.setCpf(rs.getString("cpf"));
                c.setEndereco(rs.getString("endereco"));
                lista.add(c);
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