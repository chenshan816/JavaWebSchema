package common.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import common.utils.CollectionUtil;
import common.utils.PropsUtil;
/**
 * ��ݿ����������
 * ����sql��ƴ��
 * @author cs
 *
 */
public class DatabaseHelper {
	//日记
	private static final Logger LOGGER=LoggerFactory.getLogger(DatabaseHelper.class);
	//查询
	private static final QueryRunner QUERY_RUNNER;
	//使用ThreadLocal 存放连接，保证每个连接有自己独立的连接
	private static final ThreadLocal<Connection> CONNECTION_HOLDER;
	//数据源
	private static final BasicDataSource DATA_SOURCE;
	
	private static final String DRIVER;
	private static final String URL;
	private static final String USERNAME;
	private static final String PASSWORD;
	
	static {
		CONNECTION_HOLDER = new ThreadLocal<Connection>();
		
		QUERY_RUNNER=new QueryRunner();
		
		Properties conf = PropsUtil.loadProps("smart.properties");
		DRIVER =conf.getProperty("smart.framework.jdbc.driver");
		URL = conf.getProperty("smart.framework.jdbc.url");	
		USERNAME = conf.getProperty("smart.framework.jdbc.username");
		PASSWORD = conf.getProperty("smart.framework.jdbc.password");
		
		DATA_SOURCE = new BasicDataSource();
		DATA_SOURCE.setDriverClassName(DRIVER);
		DATA_SOURCE.setUrl(URL);
		DATA_SOURCE.setUsername(USERNAME);
		DATA_SOURCE.setPassword(PASSWORD);
	}
	
	public static DataSource getDataSource() {
		return DATA_SOURCE;
	}
	
	/**
	 * 获取连接
	 */
	public static Connection getConnection(){
		Connection conn = CONNECTION_HOLDER.get();
		if(conn == null){
			try{
				conn = DATA_SOURCE.getConnection();
			}catch(SQLException e){
				LOGGER.error("get connection failure",e);
				throw new RuntimeException(e);
			}finally{
				CONNECTION_HOLDER.set(conn);
			}
		}
		return conn;
	}
	/**
	 * 查询所有数据
	 */
	public static <T> List<T> queryEntityList(Class<T> entityClass,String sql,Object... params){
		List<T> entityList;
		try{
			Connection conn = getConnection();
			entityList = QUERY_RUNNER.query(conn, sql,new BeanListHandler<T> (entityClass),params);
		}catch(SQLException e){
			LOGGER.error("query entity list failure",e);
			throw new RuntimeException(e);
		}
		return entityList;
	}
	/**
	 * 查询对象
	 */
	public static <T> T queryEntity(Class<T> entityClass,String sql,Object... params){
		T entity;
		try{
			Connection conn = getConnection();
			entity = QUERY_RUNNER.query(conn, sql,new BeanHandler<T> (entityClass),params);
		}catch(SQLException e){
			LOGGER.error("query entity list failure",e);
			throw new RuntimeException(e);
		}
		return entity;
	}
	/**
     * 查询并返回单个列值
     */
    public static <T> T query(String sql, Object... params) {
        T obj;
        try {
            Connection conn = getConnection();
            obj = QUERY_RUNNER.query(conn, sql, new ScalarHandler<T>(), params);
        } catch (SQLException e) {
            LOGGER.error("query failure", e);
            throw new RuntimeException(e);
        }
        return obj;
    }

    /**
     * 查询并返回多个列值
     */
    public static <T> List<T> queryList(String sql, Object... params) {
        List<T> list;
        try {
            Connection conn = getConnection();
            list = QUERY_RUNNER.query(conn, sql, new ColumnListHandler<T>(), params);
        } catch (SQLException e) {
            LOGGER.error("query list failure", e);
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * 查询并返回多个列值（具有唯一性）
     */
    public static <T> Set<T> querySet(String sql, Object... params) {
        Collection<T> valueList = queryList(sql, params);
        return new LinkedHashSet<T>(valueList);
    }

    /**
     * 查询并返回数组
     */
    public static Object[] queryArray(String sql, Object... params) {
        Object[] resultArray;
        try {
            Connection conn = getConnection();
            resultArray = QUERY_RUNNER.query(conn, sql, new ArrayHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("query array failure", e);
            throw new RuntimeException(e);
        }
        return resultArray;
    }

    /**
     * 查询并返回数组列表
     */
    public static List<Object[]> queryArrayList(String sql, Object... params) {
        List<Object[]> resultArrayList;
        try {
            Connection conn = getConnection();
            resultArrayList = QUERY_RUNNER.query(conn, sql, new ArrayListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("query array list failure", e);
            throw new RuntimeException(e);
        }
        return resultArrayList;
    }

    /**
     * 查询并返回结果集映射（列名 => 列值）
     */
    public static Map<String, Object> queryMap(String sql, Object... params) {
        Map<String, Object> resultMap;
        try {
            Connection conn = getConnection();
            resultMap = QUERY_RUNNER.query(conn, sql, new MapHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("query map failure", e);
            throw new RuntimeException(e);
        }
        return resultMap;
    }

    /**
     * 查询并返回结果集映射列表（列名 => 列值）
     */
    public static List<Map<String, Object>> queryMapList(String sql, Object... params) {
        List<Map<String, Object>> resultMapList;
        try {
            Connection conn = getConnection();
            resultMapList = QUERY_RUNNER.query(conn, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            LOGGER.error("query map list failure", e);
            throw new RuntimeException(e);
        }
        return resultMapList;
    }
	/**
	 * 执行查询
	 */
	public static List<Map<String,Object>> executeQuery(String sql,Object ...params){
		List<Map<String,Object>> result;
		try{
			Connection conn = getConnection();
			result = QUERY_RUNNER.query(conn,sql,new MapListHandler(),params);
		}catch(Exception e){
			LOGGER.error("execute query failure",e);
			throw new RuntimeException(e);
		}
		return result;
	}
	/**
	 * 执行更新
	 */
	public static int executeUpdate(String sql,Object... params){
		int rows=0;
		try{
			Connection conn = getConnection();
			rows=QUERY_RUNNER.update(conn, sql, params);
		}catch(SQLException e){
			LOGGER.error("execute update failure",e);
			throw new RuntimeException(e);
		}
		return rows;
	}
	/**
	 * 
	 * 根据ID获取对象 
	 */
	public static <T> T getEntityById(Class<T> entityClass,long id){
		String sql = "SELECT * FROM "+getTableName(entityClass)+" WHERE id = ?";
		return queryEntity(entityClass,sql,id);
	}
	/**
	 * 获取List
	 */
	public static <T> List<T> getEntityList(Class<T> entityClass){
		String sql = "SELECT * FROM " +getTableName(entityClass);
		return queryEntityList(entityClass,sql);
	}
	/**
	 * 插入对象
	 */
	public static <T> boolean insertEntity(Class<T> entityClass,Map<String,Object> fieldMap){
		if(CollectionUtil.isEmpty(fieldMap)){
			LOGGER.error("can not insert entity:fieldMap is empty");
			return false;
		}
		String sql = "INSERT INTO"+getTableName(entityClass);
		StringBuilder columns = new StringBuilder("(");
		StringBuilder values = new StringBuilder("(");
		for(String fieldName:fieldMap.keySet()){
			columns.append(fieldName).append(", ");
			values.append("?, ");
		}
		columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
		values.replace(values.lastIndexOf(", "), values.length(), ")");
		sql += columns + "VALUES" + values;
		
		Object[] params = fieldMap.values().toArray();
		return executeUpdate(sql, params) == 1;
	}
	/**
	 * 更新对象
	 */
	public static <T> boolean updateEntity(Class<T> entityClass,long id,Map<String,Object> fieldMap){
		if(CollectionUtil.isEmpty(fieldMap)){
			LOGGER.error("can not insert entity:fieldMap is empty");
			return false;
		}
		
		String sql = "UPTATE"+getTableName(entityClass)+"SET";
		StringBuilder columns = new StringBuilder();
		for(String fieldName:fieldMap.keySet()){
			columns.append(fieldName).append("=?, ");
		}
		sql += columns.substring(0,columns.lastIndexOf(", "))+" WHERE id =?";
		List<Object> paramList = new ArrayList<Object>();
		paramList.addAll(fieldMap.values());
		paramList.add(id);
		Object[] params = paramList.toArray();
		
		return executeUpdate(sql, params) ==1;
	}
	/**
	 * 删除对象
	 */
	public static <T> boolean deleteEntity(Class<T> entityClass,long id){
		String sql = "DELETE FROM " + getTableName(entityClass)+" WHERE id=?";
		return executeUpdate(sql, id)==1;
	}
	
	private static String getTableName(Class<?> entityClass){
		return entityClass.getSimpleName();
	}
	/**
	 * 载入Sql文件
	 */
	public static void executeSqlFile(String filePath){
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		try{
			String sql;
			while((sql=reader.readLine()) != null){
				DatabaseHelper.executeUpdate(sql);
			}
		}catch(Exception e){
			LOGGER.error("execute sql file failure",e);
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 开启事务
	 */
	public static void beginTransaction(){
		Connection conn = getConnection();
		if(conn != null){
			try {
				conn.setAutoCommit(false);
			} catch (SQLException e) {
				LOGGER.error("begin transcation failure",e);
				throw new RuntimeException();
			}finally{
				CONNECTION_HOLDER.set(conn);
			}
		}
	}
	
	/**
	 * 提交事务
	 */
	public static void commitTransaction(){
		Connection conn = getConnection();
		if(conn != null){
			try {
				conn.commit();
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("commit transcation failure",e);
				throw new RuntimeException();
			}finally{
				CONNECTION_HOLDER.remove();
			}
		}
	}
	
	/**
	 * 回滚事务
	 */
	public static void rollbackTransaction(){
		Connection conn = getConnection();
		if(conn != null){
			try {
				conn.rollback();
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("rollback transcation failure",e);
				throw new RuntimeException();
			}finally{
				CONNECTION_HOLDER.remove();
			}
		}
	}
	
}
