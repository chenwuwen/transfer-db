package transfer;

public class Page {

	/**
	 * 页码
	 */
	private int pageNo;

	/**
	 * 每页显示数
	 */
	private int pageSize = 2;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
