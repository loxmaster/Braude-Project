import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class mysqlConnection {

	public static void main(String[] args) 
	{
		try 
		{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            System.out.println("Driver definition succeed");
        } catch (Exception ex) {
        	/* handle the error*/
        	 System.out.println("Driver definition failed");
        	 }
        
        try 
        {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/test?serverTimezone=IST","root","123");
            System.out.println("SQL connection succeed");
     	} catch (SQLException ex) 
     	    {/* handle any errors*/
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            }
   	}

	public static void createTableCourses(Connection con1){
		Statement stmt;
		try {
			stmt = con1.createStatement();
			stmt.executeUpdate("create table courses(num int, name VARCHAR(40), semestr VARCHAR(10));");
			stmt.executeUpdate("load data local infile \"courses.txt\" into table courses");
	 		
		} catch (SQLException e) {	e.printStackTrace();}
		 		
	}
	
}


