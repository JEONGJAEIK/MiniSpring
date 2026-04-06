package com.createspring.spring.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSourceTransactionManager {
    private static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();
    // TODO 향 후 변수를 전달받는 방식으로 변경 필요
    private static final String URL = "jdbc:mysql://localhost:3306/spring?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=Asia/Seoul";
    private static final String USER = "root";
    private static final String PASSWORD = "456456aa";

    /**
     * 커넥션 획득 스레드로컬에 커넥션이 있으면 그대로 활용한다.
     */
    public static Connection getConnection() throws SQLException {
        Connection con = connectionHolder.get();
        if (con != null) {
            return con;
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * 인터셉터 호출용 메서드 트랜잭션 시작
     */
    public static void begin() throws SQLException {
        Connection con = getConnection();
        con.setAutoCommit(false);
        connectionHolder.set(con);
    }

    /**
     * 인터셉터 호출용 메서드 커밋
     */
    public static void commit() throws SQLException {
        connectionHolder.get().commit();
    }

    /**
     * 인터셉터 호출용 메서드 롤백
     */
    public static void rollback() throws SQLException {
        connectionHolder.get().rollback();
    }

    /**
     * 인터셉터 호출용 메서드 해제
     */
    public static void close() {
        Connection con = connectionHolder.get();
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("커넥션이 조졌다");
            }
        }
    }
}
