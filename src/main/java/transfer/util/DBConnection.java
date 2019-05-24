package transfer.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import transfer.CompleteException;
import transfer.DB;
import transfer.Page;

public class DBConnection {

	private DB db;
	private Connection con;

	public DBConnection(DB db) {
		super();
		this.db = db;
		init();
	}

	public Connection getConnection() {
		return con;
	}

	public void init() {
		try {
			con = DriverManager.getConnection(db.getUrl(), db.getUserName(), db.getPasswd());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<Map<String, Object>> executeQuery(String sql, Object[] bindArgs, DBConnection dbConnection,
			Page page) throws Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			/** 获取数据库连接池中的连接 **/
			connection = dbConnection.getConnection();
			preparedStatement = connection.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);

			if (bindArgs != null) {
				/** 设置sql占位符中的值 **/
				for (int i = 0; i < bindArgs.length; i++) {
					preparedStatement.setObject(i + 1, bindArgs[i]);
				}
			}
//			用于指定查询记录的起始位置
			preparedStatement.setInt(1, (page.getPageNo() - 1) * page.getPageSize());
//			用于指定查询数据所返回的记录数
			preparedStatement.setInt(2, page.getPageSize());

//			System.out.println(getExecSQL(sql, bindArgs));
			/** 执行sql语句，获取结果集 **/
			resultSet = preparedStatement.executeQuery();
			return getDatas(resultSet);
		} catch (Exception e) {
			throw e;
		} finally {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}

		}
	}

	public static int executeUpdate(String sql, List<LinkedList<?>> bindArgs, DBConnection dbConnection)
			throws SQLException {
		/** 影响的行数 **/
		int affectRowCount = -1;
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			/** 从数据库连接池中获取数据库连接 **/
			connection = dbConnection.getConnection();
			sql = getExecSQL(sql, bindArgs);
//			System.out.println(sql);
			/** 执行SQL预编译 **/
			preparedStatement = connection.prepareStatement(sql.toString());
			/** 设置不自动提交，以便于在出现异常的时候数据库回滚 **/
			connection.setAutoCommit(false);

			System.out.println(sql);

			/** 执行sql **/
			affectRowCount = preparedStatement.executeUpdate();
			connection.commit();

			System.out.println("成功" + "执行" + "了" + affectRowCount + "行");
			System.out.println();
		} catch (Exception e) {
			if (connection != null) {
				connection.rollback();
			}
			e.printStackTrace();
			throw e;
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}

		}
		return affectRowCount;
	}

	private static String getExecSQL(String sql, List<LinkedList<?>> bindArgs) {
		StringBuilder sb = new StringBuilder();
		System.out.println("准备插入" + bindArgs.size() + "条数据");
		String c = " ";
		for (List list : bindArgs) {
			sb.append(c);
			sb.append(" ( ");
			String d = " ";
			for (Object o : list) {
				sb.append(d);

				if (o instanceof String) {
					sb.append("'" + o + "'");
				} else {
					sb.append(o);
				}

				d = " , ";
			}

			sb.append(" ) ");
			c = " , ";
		}

		return sql + sb.toString();
	}

	private static List<Map<String, Object>> getDatas(ResultSet resultSet) throws Exception {
		List<Map<String, Object>> datas = new ArrayList<>();
		/** 获取结果集的数据结构对象 **/
		ResultSetMetaData metaData = resultSet.getMetaData();
		while (resultSet.next()) {
			Map<String, Object> rowMap = new HashMap<>();
			for (int i = 1; i <= metaData.getColumnCount(); i++) {
				rowMap.put(metaData.getColumnName(i), resultSet.getObject(i));
			}
			datas.add(rowMap);
		}
		System.out.println("成功查询到了" + datas.size() + "行数据");
		if (datas.size() < 1) {
			throw new CompleteException("已全部查询完");
		}
		for (int i = 0; i < datas.size(); i++) {
			Map<String, Object> map = datas.get(i);
			System.out.println("第" + (i + 1) + "行：" + map);
		}
		return datas;
	}
}
