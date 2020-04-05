package cn.al.hax.store.utils;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;
import org.apache.commons.dbutils.QueryRunner;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class c3p0 {
	public static Connection getConnection() throws SQLException{
		/*
		 * 1.创建连接池对象,在这个地方的可以记性参数的配置，配置的参数为配置文件的访问
		 * 2.配置文件已经进行配置处理操作
		 * 3.过去连接对象
		 * 4.返回连接对象到连接池
		 */
		ComboPooledDataSource datasource=new ComboPooledDataSource();
		return  datasource.getConnection();//获取连接池对象
		
	}
	public static void fun() {
		/*
		 * 1.创建c3p0连接池对象
		 * 2.配置四大参数
		 * 3.配置连接池参数
		 * 4.创建连接对象
		 */
		//创建c3p0连接池对象
		try {
			ComboPooledDataSource combo=new ComboPooledDataSource();
			//四大配置参数
			combo.setDriverClass("com.mysql.cj.jdbc.Driver");
			combo.setJdbcUrl("jdbc:mysql://localhost:3306/db_demo?serverTimezone=UTC");
			combo.setUser("root");
			combo.setPassword("haxhax540@");
			//对池的配置
			combo.setMaxPoolSize(50);//最大的活动连接
			combo.setMinPoolSize(20);//最小空闲连接
			combo.setMaxIdleTime(1000);//最大等待时间，超过了就会抛出异常处理
			//创建连接对象
			Connection conn=combo.getConnection();
			System.out.println(conn);
			//返回连接池对象
			conn.close();
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

}
