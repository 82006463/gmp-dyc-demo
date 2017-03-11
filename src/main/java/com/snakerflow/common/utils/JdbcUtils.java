package com.snakerflow.common.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.Properties;

/**
 * Jdbc的工具类
 * @author yuqs
 * @since 1.0
 */
public class JdbcUtils {
    private static Properties databaseTypeMappings = getDefaultDatabaseTypeMappings();

    private static Properties getDefaultDatabaseTypeMappings() {
        Properties databaseTypeMappings = new Properties();
        databaseTypeMappings.setProperty("H2","h2");
        databaseTypeMappings.setProperty("MySQL","mysql");
        databaseTypeMappings.setProperty("Oracle","oracle");
        databaseTypeMappings.setProperty("PostgreSQL","postgres");
        databaseTypeMappings.setProperty("Microsoft SQL Server","mssql");
        return databaseTypeMappings;
    }

    /**
     * 根据连接对象获取数据库类型
     * @param conn 数据库连接
     * @return 类型
     * @throws Exception
     */
    public static String getDatabaseType(Connection conn) throws Exception {
        DatabaseMetaData databaseMetaData = conn.getMetaData();
        String databaseProductName = databaseMetaData.getDatabaseProductName();
        return databaseTypeMappings.getProperty(databaseProductName);
    }
}
