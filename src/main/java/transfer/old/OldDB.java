package transfer.old;

import transfer.DB;

public class OldDB implements DB {

	private String url = "jdbc:mysql://localhost:3306/old?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC";
	private String username = "root";
	private String passwd = "root";

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	@Override
	public String getUrl() {
		// TODO Auto-generated method stub
		return url;
	}

	@Override
	public String getUserName() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public String getPasswd() {
		// TODO Auto-generated method stub
		return passwd;
	}

	public static void main(String[] args) {
		DB db = new OldDB();
		System.out.println(db.getUrl());
	}
}
