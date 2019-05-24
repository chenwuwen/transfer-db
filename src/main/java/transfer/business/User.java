package transfer.business;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import transfer.Business;

public class User implements Business {

	public static String querySQL = "select * from user";

	public static String insertSQL = "insert into user (id ,name,age,sex) values ";

	public List<LinkedList<?>> getArgs() {
		List list = new ArrayList<LinkedList<?>>();
		for (int i = 1; i < 10; i++) {
			List list2 = new LinkedList();
			list2.add(i);
			list2.add("小明" + i);
			list2.add(20 + i);
			list2.add("男");

			list.add(list2);
		}

		return list;

	}

	@Override
	public List<LinkedList<?>> buildArgs(List<Map<String, Object>> datas) {
		List list = new ArrayList<LinkedList<?>>();
		System.out.println("准备组装" + datas.size() + "条数据");
		for (Map map : datas) {
			List list2 = new LinkedList();
			list2.add(map.get("id"));
			list2.add(map.get("name"));
			list2.add(map.get("age"));
			list2.add(map.get("sex"));
			list.add(list2);
		}

		return list;

	}
}
