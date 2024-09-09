package com.example.projeto.model.dao.impl;

import com.example.projeto.db.DB;
import com.example.projeto.model.dao.ContaDAO;
import com.example.projeto.model.entities.Conta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContaDaoJDBC implements ContaDAO{
    private Connection conn = null;

    public ContaDaoJDBC(Connection conn){this.conn = conn;}

    @Override
    public void inserirCliente(Conta c) {
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("insert into conta(nome,foto,telefone,e_mail,cpf,endereco,login,senha,tipo_conta,data_registro,mensalidade_cliente) values(?,?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, c.getNome());
            st.setBytes(2,c.getFoto());
            st.setString(3,c.getTelefone());
            st.setString(4,c.getE_mail());
            st.setString(5,c.getCpf());
            st.setString(6,c.getEndereco());
            st.setString(7,c.getLogin());
            st.setString(8,c.getSenha());
            st.setString(9,c.getTipo_conta());
            st.setDate(10, (Date) c.getData_registro());
            st.setFloat(11,c.getMensalidade_cliente());
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
            DB.closeStatement(st);
        }
    }

    @Override
    public void inserirFuncionario(Conta c) {
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
            DB.closeStatement(st);
        }
    }

    @Override
    public void atualizar(Conta c) {
        PreparedStatement st = null;
        StringBuilder sql = new StringBuilder("update conta set ");
        List<Object> parametros = new ArrayList<>();

        try {
            // Construção da query
            if (c.getNome() != null && !c.getNome().isEmpty()) {
                sql.append("nome = ?, ");
                parametros.add(c.getNome());
            }
            if (c.getTelefone() != null && !c.getTelefone().isEmpty()) {
                sql.append("telefone = ?, ");
                parametros.add(c.getTelefone());
            }
            if (c.getE_mail() != null && !c.getE_mail().isEmpty()) {
                sql.append("e_mail = ?, ");
                parametros.add(c.getE_mail());
            }
            if (c.getCpf() != null && !c.getCpf().isEmpty()) {
                sql.append("cpf = ?, ");
                parametros.add(c.getCpf());
            }
            if (c.getEndereco() != null && !c.getEndereco().isEmpty()) {
                sql.append("endereco = ?, ");
                parametros.add(c.getEndereco());
            }
            if (c.getLogin() != null && !c.getLogin().isEmpty()) {
                sql.append("login = ?, ");
                parametros.add(c.getLogin());
            }
            if (c.getSenha() != null && !c.getSenha().isEmpty()) {
                sql.append("senha = ?, ");
                parametros.add(c.getSenha());
            }
            if (c.getTipo_conta() != null && !c.getTipo_conta().isEmpty()) {
                sql.append("tipo_conta = ?, ");
                parametros.add(c.getTipo_conta());
            }
            if (c.getData_registro() != null) {
                sql.append("data_registro = ?, ");
                parametros.add(new java.sql.Date(c.getData_registro().getTime()));
            }
            if (c.getSalario_funcionario() != 0.0) {
                sql.append("salario_funcionario = ?, ");
                parametros.add(c.getSalario_funcionario());
            }
            if (c.getInicio_expediente_funcionario() != null && !c.getInicio_expediente_funcionario().isEmpty() && c.getTipo_conta().equals("funcionario")) {
                sql.append("inicio_expediente_funcionario = ?, ");
                parametros.add(Time.valueOf(c.getInicio_expediente_funcionario()));
            }
            if (c.getFim_expediente_funcionario() != null && !c.getFim_expediente_funcionario().isEmpty() && c.getTipo_conta().equals("funcionario")) {
                sql.append("fim_expediente_funcionario = ?, ");
                parametros.add(Time.valueOf(c.getFim_expediente_funcionario()));
            }
            if (c.getMensalidade_cliente() != 0.0) {
                sql.append("mensalidade_cliente = ?, ");
                parametros.add(c.getMensalidade_cliente());
            }

            // Remove a vírgula e o espaço extra no final da string
            if (sql.length() > 0) {
                sql.setLength(sql.length() - 2); // Remove ", " no final
            } else {
                System.out.println("Nenhuma alteração foi feita.");
                return;
            }

            sql.append(" where id = ?");
            parametros.add(c.getId());

            st = conn.prepareStatement(sql.toString());

            // Define os parâmetros
            for (int i = 0; i < parametros.size(); i++) {
                st.setObject(i + 1, parametros.get(i));
            }

            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar a conta: " + e.getMessage(), e);
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deletarPorId(Conta c) {
        PreparedStatement st = null;

        try {
            st = conn.prepareStatement("delete from conta where id = ?");
            st.setInt(1,c.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Conta procurarPorId(int id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select id,nome,tipo_conta,telefone,e_mail,cpf,endereco,data_registro,inicio_expediente_funcionario,fim_expediente_funcionario from conta where ID = ?");
            st.setInt(1,id);
            rs = st.executeQuery();
            if(rs.next()){
                Conta c;
                c = new Conta();
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
                return c;
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
    public Conta login(String login, String senha) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select id, nome, foto, telefone, e_mail, cpf, endereco, login, senha, tipo_conta, data_registro, mensalidade_cliente, salario_funcionario, inicio_expediente_funcionario, fim_expediente_funcionario " + "from conta where login = ? and senha = ?");
            st.setString(1, login);
            st.setString(2, senha);
            rs = st.executeQuery();

            if (rs.next()) {
                Conta c = new Conta();
                c.setId(rs.getInt("id"));
                c.setNome(rs.getString("nome"));
                c.setFoto(rs.getBytes("foto"));
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
            DB.closeStatement(st);
        }
        return null;
    }

    @Override
    public List<Conta> procurarTodos() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement("select id,nome,telefone,e_mail,cpf,endereco from conta");
            rs = st.executeQuery();
            List<Conta> lista = new ArrayList<>();
            while (rs.next()){
                Conta c = new Conta();
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
            DB.closeStatement(st);
        }
    }
}
