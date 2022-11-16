/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package poly.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import poly.Responsitory.DBcontext;


/**
 *
 * @author Phong
 */
public final class JDBCHelper {

    public static ResultSet executeQuery(String sql, Object... args) throws Exception {
        Connection connection = null;
        PreparedStatement preparedstatement = null;
        ResultSet rs = null;
        connection = DBcontext.getConnection();

        try {
            preparedstatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedstatement.setObject(i + 1, args[i]);
            }
            rs = preparedstatement.executeQuery();

        } catch (SQLException ex) {
            System.out.println("Lỗi truy vấn câu lệnh: " + sql);
            Logger.getLogger(JDBCHelper.class.getName()).log(Level.SEVERE, null, ex);

        }
        return rs;
    }

    public static Integer executeUpdate(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement preparedstatement = null;
        Integer affectedRows = null;

        try {
            connection = DBcontext.getConnection();
            preparedstatement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                preparedstatement.setObject(i + 1, args[i]);
            }
            affectedRows = preparedstatement.executeUpdate();

        } catch (Exception ex) {
            System.out.println("Lỗi thực thi câu lệnh: " + sql);
            Logger.getLogger(JDBCHelper.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            release(preparedstatement, connection);
        }

        return affectedRows;
    }

    private static void release(Statement statement, Connection connection) {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println("Không đóng được Statement");
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e2) {
                System.out.println("Không đóng được Connection");
            }
        }
    }

    private static void release(ResultSet resultSet,
            Statement statement, Connection connection) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println("Không đóng được ResultSet");
            }
        }
        release(statement, connection);
    }

}
