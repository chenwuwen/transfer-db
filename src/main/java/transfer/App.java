package transfer;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import transfer.business.User;
import transfer.old.OldDB;
import transfer.util.DBConnection;
import transfer.xin.NewDB;

/**
 * 运行该类main方法
 */
public class App {

	public static void main(String[] args) {
//		封装数据库信息
		DB newDb = new NewDB();
		DB oldDb = new OldDB();
//		声明新旧数据库连接对象
		DBConnection newDbConnection = null;
		DBConnection oldDbConnection = null;

		Page page = new Page();
		try {
//			mysql8.0之前的驱动名称
//			Class.forName("com.mysql.jdbc.Driver");
//			mysql8.0 之后的驱动名称,并且不需要建立ssl连接的，你需要显示关闭。最后你需要设置CST
			Class.forName("com.mysql.cj.jdbc.Driver");

			newDbConnection = new DBConnection(newDb);
			oldDbConnection = new DBConnection(oldDb);

			Business exec = new User();

			for (int i = 1;; i++) {
//				此处建议加上排序,建议是按照插入时间排序,如果主键是自增的话,也可按照id排序
				String querySQL = User.querySQL + " limit ?,? ";
				page.setPageNo(i);
				List<Map<String, Object>> datas = oldDbConnection.executeQuery(querySQL, null, oldDbConnection, page);
//				System.out.println(datas);
//				oldDbConnection.executeUpdate(Demo.insterSQL, Demo.getArgs(), oldDbConnection);
				newDbConnection.executeUpdate(User.insertSQL, exec.buildArgs(datas), newDbConnection);
//				休眠1秒,实际应用时注释掉
				TimeUnit.SECONDS.sleep(1);
			}

		} catch (CompleteException e) {
//			此异常表示数据已查询完成,(当前查询表已查完)
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				newDbConnection.getConnection().close();
				oldDbConnection.getConnection().close();
			} catch (SQLException e) {
				System.out.println("====关闭数据库连接的异常====");
				e.printStackTrace();
			}

		}

	}
}
