package com.nextdoor.z.DAO;

import java.sql.*;

public class DBUtils {
    private static String driveClass;
    private static String url;
    private static String user;
    private static String password;
    static {
        driveClass="com.mysql.jdbc.Driver";
        url="jdbc:mysql:///nextdoor?serverTimezone=UTC&useSSL=false";
        user="root";
        password="wsZPS521";
    }

    //得到连接
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName(driveClass);
        return DriverManager.getConnection(url, user, password);
    }

    //关闭资源
    public static void closeAll(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection) {
        if (resultSet!=null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement!=null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection!=null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
