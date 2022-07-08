import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlConnection {
	
	/***
	 * This method makes connection with SQL using driver manager
	 * @return : Returns a Statement to represent a SQL statement.
	 */
	Statement makeConnection() {
		Statement statement = null;
		try {
			
			//Connection object to create a statement object
			Connection con ;
			Class.forName("com.mysql.cj.jdbc.Driver");

			con = DriverManager.getConnection(Constant.url, Constant.user, Constant.password);
			statement  = con.createStatement();
		}
		catch( Exception e){
			e.printStackTrace();
		}
		
		return statement;
	}
}