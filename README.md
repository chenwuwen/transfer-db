#这是一个数据库转移数据的脚本代码

- **如何使用** ：App.java是主文件,使用时应该执行该文件的main()方法
- **如何自定义**: 在transfer.business包下创建一个要导入数据的数据表为名称的java类,然后该类去实现Business接口,同时该类创建两个静态变量querySQL insertSQL 并写出具体业务逻辑
- **数据库配置**：在OldDB 和NewDB中配置自己新旧数据库的连接信息

>当你使用该脚本时肯定要实现自己的业务逻辑,该脚本内的User 类,是一个Demo程序,使用的时候,可以将脚本内附带的sql文件,导入到就数据库中，然后运行该脚本,进行测试,同时当运行自己的程序时,需要修改App中的一些代码,如下


```java
			Business exec = new 自定义的类();

			for (int i = 1;; i++) {
//				此处建议加上排序,建议是按照插入时间排序,如果主键是自增的话,也可按照id排序
				String querySQL = 自定义的类.querySQL + " limit ?,? ";
				page.setPageNo(i);
				List<Map<String, Object>> datas = oldDbConnection.executeQuery(querySQL, null, oldDbConnection, page);
//				System.out.println(datas);
//				oldDbConnection.executeUpdate(User.insertSQL, Demo.getArgs(), oldDbConnection);
				newDbConnection.executeUpdate(自定义的类.insertSQL, exec.buildArgs(datas), newDbConnection);
//				休眠1秒,实际应用时注释掉
				TimeUnit.SECONDS.sleep(1);
			}
```

> **注意：**如果用的Oracle数据库,或者两个库一个是Mysql,一个是Oracle,需要注意在build.gradle中添加oracle的依赖,同时在App.java中注册oracle的jdbc驱动,需要注意的是mysql版本不同,其注册驱动名称有变化,这一点,已经声明在代码注释中了


---------


###这是java实现的脚本程序,如果你用Python,可以参考这个脚本程序

> https://github.com/chenwuwen/oracle2mysql