package net.ys;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 根据mysql数据库直接生成select *对应的字段列表
 * User: NMY
 * Date: 17-5-10
 */
public class GenerateSelectAll {

    static Statement statement = null;
    static ResultSet rs = null;

    public static void generateFields(String dbName, String tableName) throws SQLException, IOException {
        String sql = "SELECT COLUMN_NAME FROM information_schema.`COLUMNS` WHERE TABLE_NAME = '%s' AND TABLE_SCHEMA='%s'";
        String columnName;
        statement = BeanMain.connection.createStatement();
        rs = statement.executeQuery(String.format(sql, tableName, dbName));
        StringBuffer sb = new StringBuffer("SELECT ");
        while (rs.next()) {
            columnName = rs.getString("COLUMN_NAME").toLowerCase();
            sb.append("`" + columnName + "`, ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" FROM `" + tableName + "` WHERE 1=1");
        System.out.println(sb.toString());
    }
}
