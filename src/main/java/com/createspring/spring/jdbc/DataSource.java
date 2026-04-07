package com.createspring.spring.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 사용자의 자격증명 관리
 * 사용자들은 데이터소스를 구현하여 빈으로 등록하면 된다.
 */
public interface DataSource {

    Connection getConnection() throws SQLException;

}
