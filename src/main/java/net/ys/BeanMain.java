package net.ys;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * User: NMY
 * Date: 17-6-12
 */
public class BeanMain {

    public static String dbName;
    public static String tableName;
    public static String url;
    public static String userName;
    public static String password;
    public static String beanPath;
    public static String mapperPath;
    public static String respMapperPath;
    public static Connection connection = null;

    static {
        try {
            InputStream in = BeanMain.class.getClassLoader().getResourceAsStream("config.properties");
            Properties properties = new Properties();
            properties.load(in);
            dbName = properties.getProperty("db.name");
            tableName = properties.getProperty("table.name");
            url = properties.getProperty("url");
            userName = properties.getProperty("user.name");
            password = properties.getProperty("password");
            beanPath = properties.getProperty("bean.path");
            mapperPath = properties.getProperty("mapper.path");
            respMapperPath = properties.getProperty("resp.mapper.path");

            File file = new File(BeanMain.beanPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File(BeanMain.mapperPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File(BeanMain.respMapperPath);
            if (!file.exists()) {
                file.mkdirs();
            }

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(BeanMain.url, BeanMain.userName, BeanMain.password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, SQLException {
        GenerateBean.generateBean(dbName);
        GenerateMapper.generateBean(dbName);
        GenerateBeanRespMapper.generateBean(dbName);
        GenerateSelectAll.generateFields(dbName, tableName);
        connection.close();
    }
}
