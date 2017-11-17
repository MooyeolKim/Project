import java.sql.Connection;
import java.sql.DriverManager;


public class DBUtil {
	final static String driver = "com.mysql.jdbc.Driver";
	final static String url = "jdbc:mysql://localhost:3306/jdbc";
	public static Connection getConnection() throws Exception{
		Class.forName(driver);
		Connection con = DriverManager.getConnection(url, "root", "1234");
		return con;
	}
}
