package com.example.projeto.db;

import java.sql.*;

public class DB {
    private static Connection conn = null;

    public static Connection getConnection(){
        if(conn==null){
            try {
                conn = DriverManager.getConnection("jdbc:mysql://64.181.164.67:3306/projetoJDBC","username","password");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
    }

    public static void closeConnection(){
        if(conn!=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeStatement(Statement st){
        if(st!=null) {
            try {
                st.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeResultSet(ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
