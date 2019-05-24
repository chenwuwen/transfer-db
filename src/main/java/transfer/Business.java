package transfer;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * 迁移数据的类 要实现该接口
 * @author HLWK-06
 *
 */
public interface Business {

	/**
	 * 组装插入SQL语句中需要的参数
	 * @param datas
	 * @return
	 */
	List<LinkedList<?>> buildArgs(List<Map<String, Object>> datas);
}
