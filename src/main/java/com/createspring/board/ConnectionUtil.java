package com.createspring.board;

import com.createspring.spring.jdbc.DataSourceTransactionManager;

import java.sql.*;

/**
 * 커넥션 유틸 스레드로컬의 커넥션을 활용한다.
 */
public class ConnectionUtil {
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    private static final String URL = "jdbc:mysql://localhost:3306/spring?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul";
    private static final String USER = "root";
    private static final String PASSWORD = "456456aa";

    /**
     * 커넥션 획득 스레드로컬에 커넥션이 있으면 그대로 활용한다.
     */
    public Connection getConnection() throws SQLException {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager(URL, USER, PASSWORD);
        return dataSourceTransactionManager.getConnection();
    }

    /**
     * 커넥션 종료
     */
    public static void close(Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("결과가 조졌다");
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                System.out.println("쿼리가 조졌다");
            }
        }
    }

}
