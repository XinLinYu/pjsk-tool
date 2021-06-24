package com.projectsekai.config;

import java.sql.*;

/**
 * @author XinlindeYu
 * @version 1.0
 * @ClassName DBConfig
 * @description
 * @date 2021/6/24 0024 上午 10:03
 **/
public class DBConfig {
    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://47.108.207.77:3306/projectsekai?serverTimezone=GMT%2b8&useSSL=false", "root", "zjp19971113106");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("创建连接成功");
        return connection;
    }

    public static void closeAll(ResultSet resultSet, Statement statement, Connection connection) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (resultSet != null) {
            statement.close();
        }
        if (resultSet != null) {
            connection.close();
        }
    }
}
