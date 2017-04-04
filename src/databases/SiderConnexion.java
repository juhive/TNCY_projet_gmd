package databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;

public class SiderConnexion {
	
	static String DB_SERVER = "jdbc:mysql://neptune.telecomnancy.univ-lorraine.fr:3306/";
	static String database = "gmd";
	static String DRIVER = "com.mysql.jdbc.Driver";
	static String USER_NAME = "gmd-read";
	static String PWD = "esial";
	
	
public static void main(String[] args) {
	
	try {
		Class.forName(DRIVER);
		Connection con = DriverManager.getConnection(DB_SERVER+database,USER_NAME, PWD);
		String myQuery = "SELECT * " +				//requete
						 "FROM meddra " ;
		Statement st = con.createStatement();
		ResultSet res = st.executeQuery(myQuery);
		
		while(res.next()){
			String CUI = res.getString("cui");
			String concept_type = res.getString("concept_type");
			int id = res.getInt("meddra_id");
			String nom= res.getString("label");
			System.out.println("");
			System.out.println(CUI+" ; "+concept_type+" ; "+id+" ; "+nom);
		}
		res.close();
		st.close();
		con.close();
	   }
		catch (ClassNotFoundException e){
			System.err.println("Could not load JDBC driver");
			System.out.println("Esception: " + e);
			e.printStackTrace();
		   
	   }
	    catch (SQLException ex) {
	    	System.err.println("SQLException information");
	    	while(ex!=null){
	    		System.err.println("" + ex.getMessage());
	    		System.err.println("" + ex.getSQLState());
	    		System.err.println("" + ex.getErrorCode());
	    		ex.printStackTrace();
	    		ex = ex.getNextException();
	    	}
		}
}

}

