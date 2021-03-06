package connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionManager {

	private static String username = "testuser";
	private static String password = "testpass";
	private static Connection sessionConnection;
	
	public static Connection getSessionConnection() throws Exception
	{
		if (sessionConnection == null || sessionConnection.isClosed())
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			sessionConnection = DriverManager.getConnection("jdbc:mysql://localhost/", username, password);
		}
		
		return sessionConnection;
	}
	
	public static void closeSessionConnection() throws Exception
	{
		if (sessionConnection != null && !sessionConnection.isClosed())
		{
			sessionConnection.close();
			sessionConnection = null;
		}
	}

	public String getUsername() 
	{
		return username;
	}

	public String getPassword() 
	{
		return password;
	}
}
