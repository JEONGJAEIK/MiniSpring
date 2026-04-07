package com.createspring.spring.transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 트랜잭션 동기화 매니저
 */
public class TransactionSynchronizationManager {

    /**
     * 커넥션 보관함
     */
    public static final ThreadLocal<Connection> connectionHolder = new ThreadLocal<>();

    /**
     * 트랜잭션이벤트리스너의 콜백 태스크 보관함
     */
    private static final ThreadLocal<List<Runnable>> eventHolder = new ThreadLocal<>();

    public static Connection getConnection() {
        return connectionHolder.get();
    }

    public static void setConnection(Connection con) {
        connectionHolder.set(con);
    }


    public static void commit() throws SQLException {
        connectionHolder.get().commit();
        invokeSynchronizations();
    }

    public static void rollback() throws SQLException {
        connectionHolder.get().rollback();
        eventHolder.remove();
    }

    public static void clear() {
        connectionHolder.remove();
    }

    /**
     * AFTER_COMMIT 콜백을 등록한다.
     */
    public static void registerSynchronization(Runnable callback) {
        if (eventHolder.get() == null) {
            eventHolder.set(new ArrayList<>());
        }
        eventHolder.get().add(callback);
    }

    /**
     * 등록된 콜백을 실행하고 정리한다.
     */
    private static void invokeSynchronizations() {
        List<Runnable> callbacks = eventHolder.get();
        if (callbacks != null) {
            for (Runnable callback : callbacks) {
                callback.run();
            }
            eventHolder.remove();
        }
    }
}
