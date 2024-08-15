package db;

import java.sql.*;

public class DB {
    private static Connection conn = null;

    public static Connection getConnection(){
        if(conn == null){
            try {
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/projetojdbc","root","37215489");
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return conn;
    }

    public static void closeConnection(){
        if(conn != null){
            try{
                conn.close();
            } catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeResultSet(ResultSet rs){
        if(rs != null){
            try{
                rs.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeStatment(Statement st){
        if(st != null){
            try{
                st.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
    }
}
