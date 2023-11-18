import java.sql.*;
import java.io.BufferedReader;
import java.io.FileReader;

import org.apache.ibatis.jdbc.ScriptRunner;

public class Main{

	static final String URL = "jdbc:mysql://localhost:3306";
	static final String USER = "root";
	static final String PASS = "password123";
	static final String START_SCRIPT = "sql/startup_script.sql";
	

	public static void main(String[] args) throws Exception{
		// connect to db
		Connection conn = getConnection();
		if(conn == null){
			System.exit(1);
		}
		// run script for creating database and tables
		new ScriptRunner(conn).runScript(new BufferedReader(new FileReader(START_SCRIPT)));
	        // create view
        	java.awt.EventQueue.invokeLater(new Runnable() {
	        public void run() {
        	        new View(conn).setVisible(true);
	        }
	        });
	}

	public static Connection getConnection() throws Exception{
        	try {
			Class.forName("com.mysql.jdbc.Driver");
        		Connection conn = DriverManager.getConnection(URL, USER, PASS);
			return conn;
        	} catch (Exception ex) {
			System.out.println("Failed to connect.");
			ex.printStackTrace();
			return null;
        	}
	}
}
